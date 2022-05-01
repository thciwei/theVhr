package com.thciwei.proj.controller.config;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "尚未登录，请登录!";
    }
}
