package com.ljj.businessserverback.rest;

import com.ljj.businessserverback.http.HttpResult;
import com.ljj.businessserverback.model.domain.User;
import com.ljj.businessserverback.model.request.LoginUserRequest;
import com.ljj.businessserverback.model.request.RegisterUserRequest;
import com.ljj.businessserverback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/rest/users")
@RestController
public class UserRestApi {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public HttpResult login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user  =userService.loginUser(new LoginUserRequest(username,password));
        Map<String,Object> map = new HashMap<>();
        map.put("success",user != null);
        map.put("user",user);
        return HttpResult.ok(map);
    }

    @GetMapping(value = "/register")
    public HttpResult addUser(@RequestParam("username") String username,@RequestParam("password") String password,Model model) {
        if(userService.checkUserExist(username)){
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("message"," 用户名已经被注册！");
            return HttpResult.ok(map);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success",userService.registerUser(new RegisterUserRequest(username,password)));
        map.put("message","");
        return HttpResult.ok(map);
    }
}
