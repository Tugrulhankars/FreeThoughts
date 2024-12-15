package com.tugrulhan.freethoughts.security;

import com.tugrulhan.freethoughts.exceptions.OAuth2AuthenticationProcessingException;
import com.tugrulhan.freethoughts.models.User;
import com.tugrulhan.freethoughts.models.UserPrincipal;
import com.tugrulhan.freethoughts.repositories.UserRepository;
import com.tugrulhan.freethoughts.security.oauth2.user.OAuth2UserInfo;
import com.tugrulhan.freethoughts.security.oauth2.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }

    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            log.error("Email not found from OAuth2 provider");
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(oAuth2UserInfo.getEmail()));

        User user=new User();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }


        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }
}


