package com.campusdual.skeletonbackend.controller;

import com.campusdual.skeletonbackend.auth.JWTUtil;
import com.campusdual.skeletonbackend.model.dto.SignupDTO;
import com.campusdual.skeletonbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JWTUtil jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.toLowerCase().startsWith("basic ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Header auth is missing.");
        }

        String base64Credentials = authHeader.substring("Basic ".length());
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);

        final String[] values = credentials.split(":", 2);
        if (values.length != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed auth header");
        }

        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(values[0], values[1])
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = this.jwtUtils.generateJWTToken(userDetails.getUsername());

            return ResponseEntity.ok(token);

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupDTO request) {
        if (this.userService.existsByUsername(request.getLogin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
        }

        this.userService.registerNewUser(request.getLogin(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered.");

    }
}