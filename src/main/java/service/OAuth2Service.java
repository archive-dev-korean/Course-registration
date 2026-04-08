package service;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import Repository.UserRepository;
import entity.Users;
import dto.Role;

@Service
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        String email;
        Map<String, Object> response = oAuth2User.getAttributes();

        if (registrationId.equals("naver")) {
            Map<String, Object> hash = (Map<String, Object>) response.get("response");
            email = (String) hash.get("email");
        } else if (registrationId.equals("google")) {
            email = (String) response.get("email");
        } else {
            throw new IllegalArgumentException("Unsupported registrationId: " + registrationId);
        }

        Users user;
        Optional<Users> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new Users();
            user.setEmail(email);
            user.setRole(Role.USER);
            userRepository.save(user);
        }

        httpSession.setAttribute("user", user);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())),
                oAuth2User.getAttributes(),
                userNameAttributeName
        );
    }
}