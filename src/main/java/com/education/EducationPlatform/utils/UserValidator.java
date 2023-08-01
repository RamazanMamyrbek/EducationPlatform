package com.education.EducationPlatform.utils;

import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> findUser = userService.findByEmail(user.getEmail());
        if (findUser.isPresent())
            errors.rejectValue("email", "", "User with this email is already signed in!");
    }
}
