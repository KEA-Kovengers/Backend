package com.newcord.userservice.auth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping("/user/first")
    public String getFirst() {
        return "first";
    }
}
