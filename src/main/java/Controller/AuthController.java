package Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    // application.yaml에 등록된 OAuth2 클라이언트(google, naver 등) 정보를 담고 있는 저장소
    private final ClientRegistrationRepository clientRegistrationRepository;

    // OAuth2 인증 요청 기본 URL (Spring Security가 이 경로로 OAuth2 로그인을 처리함)
    private static final String AUTHORIZATION_REQUEST_BASE_URL = "oauth2/authorization";

    public AuthController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    // ==================== 메인 페이지 ====================
    // "/" 접속 시 index.html을 반환
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // ==================== 로그인 페이지 ====================
    // 사용자가 /login에 접속하면, 등록된 OAuth2 제공자(google, naver)별 로그인 URL을 만들어서
    // login.html에 전달 → 로그인 버튼을 동적으로 생성
    @GetMapping("/login")
    public String getLoginPage(Model model) {

        // [1] 로그인 URL을 저장할 빈 Map 생성
        // 최종 결과 예시: {"Google": "oauth2/authorization/google", "Naver": "oauth2/authorization/naver"}
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

        // [2] clientRegistrationRepository가 Iterable(반복 가능)한지 타입 검사
        // → application.yaml에 등록된 OAuth2 클라이언트가 여러 개일 수 있으므로
        //   하나씩 꺼내서 반복하려면 Iterable로 캐스팅이 가능한지 먼저 확인해야 함
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);

        // [3] 타입 검사 통과 시, clientRegistrationRepository를 Iterable로 캐스팅
        // → 이제 for-each로 등록된 OAuth2 제공자를 하나씩 꺼낼 수 있음
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGeneric(0))) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        // [4] 등록된 각 OAuth2 제공자를 순회하면서 로그인 URL 생성
        // registration.getClientName()  → "Google", "Naver" (표시용 이름)
        // registration.getRegistrationId() → "google", "naver" (설정 ID)
        // 결합하면: "oauth2/authorization/google" → 이 URL을 누르면 구글 로그인이 시작됨
        if (clientRegistrations != null) {
            clientRegistrations.forEach(registration ->
                    oauth2AuthenticationUrls.put(
                            registration.getClientName(),
                            AUTHORIZATION_REQUEST_BASE_URL + "/" + registration.getRegistrationId()
                    )
            );
        }

        // [5] 완성된 URL Map을 "urls"라는 이름으로 Model에 담아서 뷰(login.html)에 전달
        // login.html에서 이걸 반복하며 "구글로 로그인", "네이버로 로그인" 버튼을 렌더링
        model.addAttribute("urls", oauth2AuthenticationUrls);
        return "login";
    }

    // ==================== 유저 페이지 ====================
    // USER 권한을 가진 사용자만 접근 가능 (SecurityConfig에서 설정)
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    // ==================== 접근 거부 페이지 ====================
    // 권한 없는 페이지에 접근했을 때 SecurityConfig에서 여기로 리다이렉트
    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
