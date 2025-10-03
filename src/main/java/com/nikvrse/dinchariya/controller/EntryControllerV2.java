package com.nikvrse.dinchariya.controller;

import com.nikvrse.dinchariya.model.DinchariyaModel;
import com.nikvrse.dinchariya.model.User;
import com.nikvrse.dinchariya.service.DinchariyaEntryService;
import com.nikvrse.dinchariya.service.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dinchariyas")
public class EntryControllerV2 {
    private final DinchariyaEntryService dcService;
    private final UserService userService;

    public EntryControllerV2(DinchariyaEntryService dces,UserService userService) {
        this.dcService = dces;
        this.userService=userService;
    }

    @PostMapping
    public ResponseEntity<?> createDinchariya(@RequestBody DinchariyaModel dcm) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        try {
            User user=userService.getUserByUsername(username);
            if(user==null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not exists with the username : @" + username);
            }
            dcService.saveDinchariya(username,dcm);
            return new ResponseEntity<>(dcm, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDinchariyaOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.getUserByUsername(username);
        if(user!=null){
            List<DinchariyaModel> dinchariyas = user.getMyDincharias();
            if (!dinchariyas.isEmpty()) {
                return new ResponseEntity<>(dinchariyas, HttpStatus.OK);
            } else {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No dinchariyas found for the username : @"+username);
            }
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user exists with the username : @"+username);
        }
    }


    @GetMapping("/id/{dcId}")
    public ResponseEntity<DinchariyaModel> getDCById(@PathVariable ObjectId dcId) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.getUserByUsername(username);
        List<DinchariyaModel> collect=user.getMyDincharias().stream().filter(x->x.getId().equals(dcId)).toList();
        if(!collect.isEmpty()){
            Optional<DinchariyaModel> dcEntry = dcService.getDinchariyaById(dcId);
            if(dcEntry.isPresent()){
                return ResponseEntity.status(HttpStatus.FOUND).body(dcEntry.get());
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("/id/{dcId}")
    public ResponseEntity<String> deleteDC(@PathVariable ObjectId dcId) {
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            Optional<DinchariyaModel> data = dcService.getDinchariyaById(dcId);
            if (data.isPresent()) {
                dcService.deleteById(username,dcId);
                return new ResponseEntity<>("Dinchariya deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Dinchariya Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("/id/{dcId}")
    public ResponseEntity<?> updateDC(
            @PathVariable ObjectId dcId,
            @RequestBody DinchariyaModel newDcm) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        User user=userService.getUserByUsername(username);
        if(user.getMyDincharias().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Dinchariya is present for the User @:"+username);
        }
        List<DinchariyaModel> dinchariyas=user.getMyDincharias().stream().filter(x->x.getId().equals(dcId)).toList();


        if(dinchariyas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Dinchariya found with ID: " + dcId);
        }
        DinchariyaModel existingData=dinchariyas.get(0);

        try{
            if (newDcm != null) {
                if (newDcm.getTitle() != null && !newDcm.getTitle().isEmpty()) {
                    existingData.setTitle(newDcm.getTitle());
                }
                if (newDcm.getContent() != null && !newDcm.getContent().isEmpty()) {
                    existingData.setContent(newDcm.getContent());
                }
            }
            dcService.saveDinchariya(existingData);
            return new ResponseEntity<>(existingData, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }
}