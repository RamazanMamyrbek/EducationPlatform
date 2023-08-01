package com.education.EducationPlatform.controllers;

import com.education.EducationPlatform.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user){
        return "/auth/registration";
    }
}
