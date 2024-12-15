package com.tugrulhan.freethoughts.configs;

import com.tugrulhan.freethoughts.security.CustomOAuth2UserService;
import com.tugrulhan.freethoughts.security.JwtAuthenticationFilter;
import com.tugrulhan.freethoughts.security.oauth2.user.*;
import com.tugrulhan.freethoughts.services.impl.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class SecurityConfig {
    private final static String OAUTH2_BASE_URI = "/oauth2/authorize";
    private final static String OAUTH2_REDIRECTION_ENDPOINT = "/oauth2/callback";
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final HttpCookieOauth2AuthorizationRequestReqpository httpCookieOauth2AuthorizationRequestReqpository;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService, CustomOAuth2UserService customOAuth2UserService, OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler, HttpCookieOauth2AuthorizationRequestReqpository httpCookieOauth2AuthorizationRequestReqpository, ClientRegistrationRepository clientRegistrationRepository) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.httpCookieOauth2AuthorizationRequestReqpository = httpCookieOauth2AuthorizationRequestReqpository;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }
    @Bean
    public HttpCookieOauth2AuthorizationRequestReqpository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOauth2AuthorizationRequestReqpository();
    }
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/token/refresh/**","/api/v1/auth/signIn","/login","/home","/api/v1/articles/**", "/api/v1/auth/signUp","/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/", "/error").permitAll()
                        .requestMatchers("/auth/**", "/oauth2/**").permitAll()
                        .anyRequest()
                        .authenticated()
        );

        http
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
                                .baseUri(OAUTH2_BASE_URI)
                                .authorizationRequestRepository(httpCookieOauth2AuthorizationRequestReqpository)
                                .authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository, OAUTH2_BASE_URI))
                        )
                        .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig.baseUri(OAUTH2_REDIRECTION_ENDPOINT))
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))
                        .tokenEndpoint(tokenEndpointConfig -> tokenEndpointConfig.accessTokenResponseClient(authorizationCodeTokenResponseClient()))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> authorizationCodeTokenResponseClient() {
        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(new CustomAccessTokenResponseConverter());

        RestTemplate restTemplate = new RestTemplate(Arrays.asList(new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

        DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        tokenResponseClient.setRestOperations(restTemplate);

        return tokenResponseClient;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
