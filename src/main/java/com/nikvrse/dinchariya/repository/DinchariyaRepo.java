package com.nikvrse.dinchariya.repository;

import com.nikvrse.dinchariya.model.DinchariyaModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


public interface DinchariyaRepo extends MongoRepository<DinchariyaModel, ObjectId> {

}
