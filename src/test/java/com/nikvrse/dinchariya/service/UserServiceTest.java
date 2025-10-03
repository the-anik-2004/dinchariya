package com.nikvrse.dinchariya.service;

import com.nikvrse.dinchariya.model.User;
import com.nikvrse.dinchariya.repository.UserRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepo userRepo;

//    @Disabled
    @Test
    void testFindByUsername(){
        User user=userRepo.findByUsername("anikpal");
        assertFalse(user.getMyDincharias().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings =    {
            "anikpal",
//            "debanjan07",
            "rohan"
    })
    void testUserExistance(String username){
       User user= userRepo.findByUsername(username);
       assertNotNull(user,"failed for user @"+username);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
//            "3,3,9"
    })
    void  addTest(int a,int b,int expected){
        assertEquals(expected,a+b,"Failed for the addition a="+a+" & b="+b+" where the exepected Value="+expected);
    }
}
