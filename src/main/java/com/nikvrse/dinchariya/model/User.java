package com.nikvrse.dinchariya.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true) //N.B. - it will not create index automatic index [you have to configure 'application.properties']
    @NonNull
    private String username;

    @NonNull
    private String password;

    private String email;
    private boolean sentimentAnalysis;

    @DBRef
    private List<DinchariyaModel> myDincharias=new ArrayList<>();
    private List<String> Roles;



}
