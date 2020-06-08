package org.linn.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.linn.entity.User;
import org.linn.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/user")
    public List<User> query(@RequestParam(required = false, defaultValue = "tom") String username) {
        //message.out.println(username);
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
        // message.out.println(user);
       /* if (true){
            throw new RuntimeException("111");
        }*/
        return ResponseEntity.ok(user);
    }


    @PostMapping("/upload")
    public void updateLoad(MultipartFile file) throws IOException {
        String folder = "D:" + File.separator + "floder" + File.separator + file.getOriginalFilename();
        try (
                FileOutputStream out = new FileOutputStream(new File(folder));
                FileInputStream in = (FileInputStream) file.getInputStream()
        ) {
            FileChannel inChannel = in.getChannel();
            FileChannel outChannel = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
            inChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/me")
    public Object getCurrentUser(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/mee")
    public Object getCurrentUsers(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    @GetMapping("/hello")
    public String hello(Principal principal) {
        return "hello" + principal.getName();
    }
}
