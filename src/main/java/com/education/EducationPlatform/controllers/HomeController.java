package com.education.EducationPlatform.controllers;

import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.security.MyUserDetails;
import com.education.EducationPlatform.services.UserService;
import com.education.EducationPlatform.utils.SecurityContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final UserService userService;
    private final SecurityContextManager securityContextManager;

    @Autowired
    public HomeController(UserService userService, SecurityContextManager securityContextManager) {
        this.userService = userService;
        this.securityContextManager = securityContextManager;
    }

    @GetMapping("/welcome")
    public String welcomePage(Model model){
        securityContextManager.enrichModel(model);
        return "welcomePage";
    }
    private User getUserFromSession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        return user;
    }
}
