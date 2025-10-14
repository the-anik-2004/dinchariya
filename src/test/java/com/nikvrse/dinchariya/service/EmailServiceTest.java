package com.nikvrse.dinchariya.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendMail(
                "icitizen81@gmail.com",
                "Mail checking",
                "Toh kaise hain aap log,,,??📩"
        );
    }
}
