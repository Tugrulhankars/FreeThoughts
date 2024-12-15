package com.tugrulhan.freethoughts.controllers;

import com.tugrulhan.freethoughts.dtos.response.JwtAuthenticationResponse;
import com.tugrulhan.freethoughts.dtos.reuqest.SignInRequest;
import com.tugrulhan.freethoughts.dtos.reuqest.SignUpRequest;
import com.tugrulhan.freethoughts.services.impl.AuthenticationService;
import com.tugrulhan.freethoughts.services.impl.JwtService;
import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signIn")
    @Operation(summary = "Sign In", description = "Sign In")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/signUp")
    @Operation(summary = "Sign Up", description = "Sign Up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody  SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }



}
