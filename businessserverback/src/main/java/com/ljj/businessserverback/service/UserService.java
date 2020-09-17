package com.ljj.businessserverback.service;

import com.ljj.businessserverback.model.domain.User;
import com.ljj.businessserverback.model.request.LoginUserRequest;
import com.ljj.businessserverback.model.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean registerUser(RegisterUserRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirst(true);
        user.setTimestamp(System.currentTimeMillis());
        try{
            mongoTemplate.save(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(LoginUserRequest request){
        User user = findByUsername(request.getUsername());
        if(null == user) {
            return null;
        }else if(!user.passwordMatch(request.getPassword())){
            return null;
        }
        return user;
    }


    public boolean checkUserExist(String username){
        return null != findByUsername(username);
    }

    public User findByUsername(String username){
        Query query = new Query(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(query, User.class);
        if(user == null){
            return null;
        }
        return user;
    }

}
