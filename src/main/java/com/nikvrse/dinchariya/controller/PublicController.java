package com.nikvrse.dinchariya.controller;

import com.nikvrse.dinchariya.model.User;
import com.nikvrse.dinchariya.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    private static final Logger logger=  LoggerFactory.getLogger(PublicController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Dinchariya | status : OK";
    }

//    @PostMapping("/create-user")
//    public ResponseEntity<String> createUser(@RequestBody User user){
//        try{
//            User prevUser = userService.getUserByUsername(user.getUsername());
//            if(prevUser==null){
//                userService.saveNewUser(user);
//                return ResponseEntity.status(HttpStatus.CREATED).body("User is Saved");
//            }else{
//                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("User already exists with the username : @"+user.getUsername());
//            }
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
//}

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user){
        try{
            boolean isCreated=userService.saveNewUser(user);
            return isCreated ? ResponseEntity.status(HttpStatus.CREATED).body("User is created"): ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("User already exists with the username : @"+user.getUsername());
        }catch (Exception e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

