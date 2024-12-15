package com.tugrulhan.freethoughts.services;

import com.tugrulhan.freethoughts.constants.EmailConstants;
import com.tugrulhan.freethoughts.dtos.response.JwtAuthenticationResponse;
import com.tugrulhan.freethoughts.dtos.reuqest.SignInRequest;
import com.tugrulhan.freethoughts.dtos.reuqest.SignUpRequest;
import com.tugrulhan.freethoughts.enums.Role;
import com.tugrulhan.freethoughts.models.User;
import com.tugrulhan.freethoughts.repositories.UserRepository;
import com.tugrulhan.freethoughts.services.impl.AuthenticationService;
import com.tugrulhan.freethoughts.services.impl.EmailService;
import com.tugrulhan.freethoughts.services.impl.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl  implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var user = userRepository.findByEmail(request.email());
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user = User.builder().firstName(request.firstName()).lastName(request.lastName())
                .email(request.email()).password(passwordEncoder.encode(request.password()))
                .role(Role.ST_USER).build();
        userRepository.save(user);
        emailService.sendEmail(request.email(),EmailConstants.SUBJECT,"MR."+user.getFirstName()+user.getLastName()+ EmailConstants.body);
        var jwt=jwtService.generateToken(user);
        var response=new JwtAuthenticationResponse(jwt);
        return response;

    }
}
