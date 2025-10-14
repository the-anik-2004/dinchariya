package com.nikvrse.dinchariya.scheduler;

import com.nikvrse.dinchariya.enums.Sentiment;
import com.nikvrse.dinchariya.model.DinchariyaModel;
import com.nikvrse.dinchariya.model.User;
import com.nikvrse.dinchariya.repository.UserRepoImpl;
import com.nikvrse.dinchariya.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserFeedbackScheduler {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepoImpl userRepo;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSAMail(){
        List<User> users=userRepo.getUsersForSA();
        for (User user:users){
            List<DinchariyaModel> dincharias=user.getMyDincharias();
            List<Sentiment> sentiments=dincharias.stream().filter(x->x.getDate()
                    .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))

            ).map(x->x.getSentiment()).toList() ;

            Map<Sentiment,Integer> sentimentCounts=new HashMap<>();

            for(Sentiment sentiment:sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }

            Sentiment mostFrequentSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> entry:sentimentCounts.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount=entry.getValue();
                    mostFrequentSentiment=entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
               emailService.sendMail(
                       user.getEmail(),
                       "Sentiment for last 7 days ",
                       mostFrequentSentiment.toString()
                       );
            }

        }
    }
}
