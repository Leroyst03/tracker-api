package app.tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.tracker.dto.AuthResponse;
import app.tracker.dto.UserRequest;
import app.tracker.exception.HttpException;
import app.tracker.security.JwtUtil;
import app.tracker.service.UserService;


@RestController
@RequestMapping("/auth")
public class Auth {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest user) {
       try {
            Long response = userService.verifyPassword(user);
            if(response != 0){
                String token = jwtUtil.generateToken(response);
        
                return ResponseEntity.ok(new AuthResponse(token));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña o el email son incorrectos");
        }
        catch(HttpException err) {
            return ResponseEntity.status(err.getStatus()).body(err.getMessage());
        }
    }
    
    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado!");
        }
        catch(HttpException err) {
            return ResponseEntity.status(err.getStatus()).body(err.getMessage());
        }

    }
}
