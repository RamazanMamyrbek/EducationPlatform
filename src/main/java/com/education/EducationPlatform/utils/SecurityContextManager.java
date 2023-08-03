package com.education.EducationPlatform.utils;

import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.security.MyUserDetails;
import com.education.EducationPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class SecurityContextManager {

    private final UserService userService;

    @Autowired
    public SecurityContextManager(UserService userService) {
        this.userService = userService;
    }

    public void enrichModel(Model model){
        if (checkAuthentication()){
            model.addAttribute("authentication", userService.findUserById(getUserFromSession().getId()));
        }
    }
    public User getUserFromSession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        return user;
    }
    public boolean checkAuthentication() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser");
    }
}
