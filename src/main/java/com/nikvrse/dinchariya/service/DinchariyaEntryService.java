package com.nikvrse.dinchariya.service;

import com.nikvrse.dinchariya.model.DinchariyaModel;
import com.nikvrse.dinchariya.model.User;
import com.nikvrse.dinchariya.repository.DinchariyaRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DinchariyaEntryService {

    @Autowired
    private DinchariyaRepo dinchariyaRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveDinchariya(String username,DinchariyaModel dinchariya){
        try{
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return;
            }
            dinchariya.setDate(LocalDateTime.now());
            DinchariyaModel saved = dinchariyaRepo.save(dinchariya);
            user.getMyDincharias().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            throw new RuntimeException("Dinchariyas Service Error : "+e);
        }
    }

    @Transactional
    public void saveDinchariya(DinchariyaModel dinchariya){
        dinchariyaRepo.save(dinchariya);
    }

    public List<DinchariyaModel> getAllDinchariyas(){
        return dinchariyaRepo.findAll();
    }

    public Optional<DinchariyaModel> getDinchariyaById(ObjectId id){
        return dinchariyaRepo.findById(id);
    }


    @Transactional
   public boolean deleteById(String username,ObjectId id){
        boolean removed=false;
        User user=userService.getUserByUsername(username);
        removed=user.getMyDincharias().removeIf(x->x.getId().equals(id));
        if(removed){
             userService.saveUser(user);
             dinchariyaRepo.deleteById(id);
        }
        return removed;
   }


}
