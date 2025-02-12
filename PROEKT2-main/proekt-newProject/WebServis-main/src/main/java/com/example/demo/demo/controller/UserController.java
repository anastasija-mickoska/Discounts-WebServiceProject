package com.example.demo.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.demo.model.User;
import com.example.demo.demo.service.UserService;
@RestController
@RequestMapping("/api/auth")
public class UserController {
    
    @Autowired
    private UserService userService;

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value="/register", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String registerNewUser(@RequestHeader(value="username") String username,
                                  @RequestHeader(value="password") String password,
                                  HttpServletRequest httpServletRequest) throws Exception{
        Specification <com.example.demo.demo.model.User> spec;
        List<com.example.demo.demo.model.User> userList = null;
        User user = new User();
        boolean flag = false;

        try {
            if(!userService.checkIfExists(String.valueOf(username)))
            {
                user.setUsername(username);
                user.setPassword(password);
                userService.saveUser(user);
                flag = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if(flag){
            return "Корисникот е успешно регистриран";
        } else {
            return "Корисникот веќе постои";
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value="/unregister", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String unregister(@RequestHeader(value="username") String username,
                             @RequestHeader(value="password") String password,
                                  HttpServletRequest httpServletRequest) throws Exception{
        List<com.example.demo.demo.model.User> userList = null;
        User user = new User();
        String message = "";

        try {
            

            if(!userService.checkIfExists(username)){
                message = "Корисникот не постои";
            } else {
                User user1 = userService.getUserByUserName(username);
                if(user1.getPassword().equals(password)){
                    userService.deleteUser(user1);
                    message = "Корисникот е успешно избришан";
                } else {
                    message = "Лозинката за бришење на корисникот не се совпаѓа";
                }
            }


        } catch (Exception e){
            message = "Настана грешка, обидете се повторно!";
            e.printStackTrace();
        }

        return message;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value="/login", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String login(@RequestHeader(value="username") String username,
                        @RequestHeader(value="password") String password,
                             HttpServletRequest httpServletRequest) throws Exception{
        Specification <com.example.demo.demo.model.User> spec;
        List<com.example.demo.demo.model.User> userList = null;
        User user = new User();
        user = userService.getUserByUserName(username);
        String message = "";

        try {

            if(!userService.checkIfExists(username)){
                message = "Корисникот не постои или е погрешно корисничкото име";
            } else {
                if(user.getPassword().equals(password)){
                    message = "Корисникот е успешно најавен";
                } else {
                    message = "Лозинката не е точна";
                }
            }


        } catch (Exception e){
            message = "Настана грешка, обидете се повторно!";
            e.printStackTrace();
        }
        return message;
    }

}
