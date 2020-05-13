package org.linn.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.linn.entity.User;
import org.linn.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/user")
    public List<User> query(@RequestParam(required = false, defaultValue = "tom") String username) {
        System.out.println(username);
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    //限制id
    @GetMapping("/user/{id:\\d+}")
    @JsonView({User.UserSimpView.class})
    public User queryById(@PathVariable Integer id) {
        return new User();
    }

    @GetMapping("/get/{id:\\d+}")
    public Object getUser(@PathVariable Integer id) {
        throw new CustomException(500, "ID没有找到");
    }

    @PostMapping("/user")
    public ResponseEntity<User> getUser(@Validated @RequestBody User user) {
        System.out.println(user);
       /* if (true){
            throw new RuntimeException("111");
        }*/
        return ResponseEntity.ok(user);
    }
}
