package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import service.OAuth2Service;

// @Configuration: 이 클래스가 Spring 설정 클래스임을 선언 (Bean 등록 가능)
// @EnableWebSecurity: Spring Security 필터 체인을 활성화
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // OAuth2 로그인 시 사용자 정보를 처리하는 서비스 (생성자 주입)
    private final OAuth2Service oAuth2Service;

    public SecurityConfig(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }

    // SecurityFilterChain: 모든 HTTP 요청이 이 필터 체인을 거쳐서 인증/인가 처리됨
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 보호 비활성화 (REST API나 OAuth2 사용 시 보통 끔)
            .csrf(csrf -> csrf.disable())

            // URL별 접근 권한 설정
            .authorizeHttpRequests(auth -> auth
                // 정적 리소스, 메인 페이지 → 누구나 접근 가능
                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                // 로그인 페이지 → 누구나 접근 가능
                .requestMatchers("/login").permitAll()
                // /Users/** → "USER" 권한 가진 사용자만
                .requestMatchers("/Users/**").hasAuthority("USER")
                // /Admin/** → "ADMIN" 권한 가진 사용자만
                .requestMatchers("/Admin/**").hasAuthority("ADMIN")
                // 그 외 모든 요청 → 로그인 필수
                .anyRequest().authenticated()
            )

            // 권한 없는 페이지 접근 시 → /accessDenied로 이동
            .exceptionHandling(ex -> ex.accessDeniedPage("/accessDenied"))

            // 로그아웃 설정: /logout 요청 시 로그아웃 처리
            .logout(logout -> logout.logoutUrl("/logout").permitAll())

            // OAuth2 로그인 설정
            .oauth2Login(oauth2 -> oauth2
                // 커스텀 로그인 페이지 경로
                .loginPage("/login")
                // 로그인 성공 후 사용자 정보를 OAuth2Service에서 처리
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2Service))
            );

        // 위 설정을 적용한 SecurityFilterChain 객체 반환
        return http.build();
    }
}
