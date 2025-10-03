//package com.nikvrse.dinchariya.service;
//
//import com.nikvrse.dinchariya.repository.UserRepo;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class UserServiceImplTest {
//
//    @InjectMocks
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Mock
//    private UserRepo userRepo;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
////
//    @Test
//    void loadUserByUsernameTest(){
//        when(userRepo.findByUsername(ArgumentMatchers.anyString())).thenReturn((com.nikvrse.dinchariya.model.User) User.builder().username("anikpal").password("efaec435546dcfew").roles(String.valueOf(new ArrayList<>())).build());
//        UserDetails user =userDetailsService.loadUserByUsername("anikpal");
//        Assertions.assertNotNull(user);
//    }
//}
