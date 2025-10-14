package com.nikvrse.dinchariya.controller;

import com.nikvrse.dinchariya.model.User;
import com.nikvrse.dinchariya.service.UserDetailsServiceImpl;
import com.nikvrse.dinchariya.service.UserService;
import com.nikvrse.dinchariya.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    private static final Logger logger=  LoggerFactory.getLogger(PublicController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Dinchariya | status : OK";
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user){
        try{
            boolean isCreated=userService.saveNewUser(user);
            return isCreated ? ResponseEntity.status(HttpStatus.CREATED).body("User is created"): ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("User already exists with the username : @"+user.getUsername());
        }catch (Exception e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
           authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
            );
           UserDetails userDetails=userDetailsService.loadUserByUsername(user.getUsername());
           String jwtToken= jwtUtil.generateToken(userDetails.getUsername());
           return new ResponseEntity<>(jwtToken,HttpStatus.OK);
        }catch (Exception e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

