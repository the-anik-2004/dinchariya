package com.nikvrse.dinchariya.controller;

import com.nikvrse.dinchariya.api.response.WeatherResponse;
import com.nikvrse.dinchariya.model.User;
import com.nikvrse.dinchariya.service.QuoteService;
import com.nikvrse.dinchariya.service.UserService;
import com.nikvrse.dinchariya.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private QuoteService quoteService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    //----------------Industry Standards------------------
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(){
//        List<User> users= userService.getAllUsers();
//        if(!users.isEmpty()){
//            return ResponseEntity.ok(new ApiResponse<>("Users fetched Successfully",users));
//        }else{
//            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>("No User Found",null));
//        }
//    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<User> getUserById(@PathVariable ObjectId userId){
//        User user=userService.getUserById(userId);
//        if(user!=null){
//            return  ResponseEntity.status(HttpStatus.FOUND).body(user);
//        }else {
//            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
    @GetMapping("/greet/{loc}")
    public ResponseEntity<?> greetings(@PathVariable String loc){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        WeatherResponse response=weatherService.getWeather(loc);
        String greetings="Hi "+username+",\n It's feels like "+response.getCurrent().getFeelslike()+"° C in "+response.getLocation().getName()+", "+response.getLocation().getCountry();
//        String quote="Quote of the day is :"+ quoteService.getQuote().getQuote();
//        return ResponseEntity.status(HttpStatus.OK).body(greetings+"\n "+quote);
        return ResponseEntity.status(HttpStatus.OK).body(greetings);
    }

    @GetMapping("/me")
    public ResponseEntity<?> readUser(){
        try{
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            User user = userService.getUserByUsername(username);
            if(user == null) {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with this Username : @"+username);
            }
            return  ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User userReq){
        try{
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            User prevUser = userService.getUserByUsername(username);
            if(prevUser != null) {
                prevUser.setUsername(userReq.getUsername().isEmpty() && userReq.getUsername().equals(prevUser.getUsername())
                        ?  prevUser.getUsername()
                        :userReq.getUsername());
                prevUser.setPassword(userReq.getPassword().isEmpty() && userReq.getPassword().equals(prevUser.getPassword())
                        ?  prevUser.getPassword()
                        :userReq.getPassword());
                userService.updateUser(prevUser);
                return  ResponseEntity.status(HttpStatus.OK).body(prevUser);
            }else{
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with this Username : @"+userReq.getUsername());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<?> deleteUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        if(userService.getUserByUsername(username)==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not present with username :@+"+username);
        }else{
            userService.deleteUserByUsername(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
