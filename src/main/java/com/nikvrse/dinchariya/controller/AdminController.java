package com.nikvrse.dinchariya.controller;

import com.nikvrse.dinchariya.model.User;
import com.nikvrse.dinchariya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    public AdminController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllOfUsers(){
        List<User> users= userService.getAllUsers();
        return users.isEmpty()
                ?ResponseEntity.noContent().build()
                :ResponseEntity.ok(users);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createNewAdmin(@RequestBody User admin){
        User user=userService.getUserByUsername(admin.getUsername());
        if(user!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User @" + admin.getUsername() + " already exists.");
        }
        userService.saveAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body("@"+admin.getUsername()+" becomes ADMIN.");
    }

    @PutMapping("/make-admin")
    public ResponseEntity<?> makeAdmin(@RequestBody User prevUser){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username=authentication.getName();

        User user=userService.getUserByUsername(prevUser.getUsername());

        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("User with username @"+prevUser.getUsername()+" is not  present.");
        }
        List<String> roles=new ArrayList<>(user.getRoles());
        if(!roles.contains("ADMIN")){
            roles.add("ADMIN");
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("@"+prevUser.getUsername()+" becomes ADMIN.");
    }

}
