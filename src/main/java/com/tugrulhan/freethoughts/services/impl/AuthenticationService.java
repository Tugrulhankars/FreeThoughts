package com.tugrulhan.freethoughts.services.impl;

import com.tugrulhan.freethoughts.dtos.response.JwtAuthenticationResponse;
import com.tugrulhan.freethoughts.dtos.reuqest.SignInRequest;
import com.tugrulhan.freethoughts.dtos.reuqest.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signIn(SignInRequest request);
    JwtAuthenticationResponse signUp(SignUpRequest request);
}
