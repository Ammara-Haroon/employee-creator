package com.projects.backend.login;


import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class LoginController {
private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest);
		Authentication authenticationRequest =
			UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
			System.out.println(authenticationRequest.toString());
			Authentication authenticationResponse =
			this.authenticationManager.authenticate(authenticationRequest);
			System.out.println(authenticationResponse.toString());
			AuthenticationResponse res = new AuthenticationResponse(authenticationResponse.isAuthenticated(), authenticationResponse.getAuthorities(), authenticationResponse.getName());
			System.out.println(res.toString());
			
			return new ResponseEntity<>(res.toString(),HttpStatus.OK);
	}

	public record LoginRequest(String username, String password) {
	}

	
}
