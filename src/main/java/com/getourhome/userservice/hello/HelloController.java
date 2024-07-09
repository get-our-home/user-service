package com.getourhome.userservice.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/users/hello")
    public String hello(){
        return "Hello, World!";
    }
}