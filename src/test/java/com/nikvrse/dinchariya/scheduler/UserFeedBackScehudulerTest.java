package com.nikvrse.dinchariya.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserFeedBackScehudulerTest {

    @Autowired
    private UserFeedbackScheduler userFeedbackScheduler;
    @Test
    public  void fetchUsersAndSendSAMailTest(){
        userFeedbackScheduler.fetchUsersAndSendSAMail();
    }
}
