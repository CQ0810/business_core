package com.tio.app.user.controllers.mobile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/mobile")
public class UserTestController {
    @PostMapping("/login")
    public String login() {
        return "success";
    }
}
