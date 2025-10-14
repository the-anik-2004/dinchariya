package com.nikvrse.dinchariya.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T getRedisData(String key,Class<T> entityClass){
        try{
            Object obj = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return obj!=null? mapper.readValue(obj.toString(), entityClass):null;
        } catch (JsonProcessingException e) {
            log.error("EXCEPTION IN REDIS : {}", String.valueOf(e));
            return null;
        }
    }

    public void setRedisData(String key,Object o,Long ttl){
        try{
            ObjectMapper mapper=new ObjectMapper();
            String jsonValue=mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            log.error("EXCEPTION IN REDIS : {}", String.valueOf(e));
        }
    }
}
