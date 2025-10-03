package com.nikvrse.dinchariya.repository;

import com.nikvrse.dinchariya.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    //User must have email and their SetimantAnalysis must be true...
    public List<User> getUsersForSA(){
        Query query=new Query();

        query.addCriteria((Criteria.where("email").regex("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")));
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

//        -------Alternative way----------
//        Criteria criteria=new Criteria();
//        query.addCriteria(
//                criteria.andOperator(
//                        Criteria.where("email").exists(true),
//                        Criteria.where("email").ne(null).ne(""),
//                        Criteria.where("sentimentAnalysis").is(true)
//                )
//        );
        List<User> users=mongoTemplate.find(query,User.class);
        return users;
    }
}
