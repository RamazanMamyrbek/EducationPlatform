package com.education.EducationPlatform.controllers;

import com.education.EducationPlatform.models.Course;
import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.security.MyUserDetails;
import com.education.EducationPlatform.services.CourseService;
import com.education.EducationPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public TeacherController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String myProfilePage(Model model){
        model.addAttribute("teacher", userService.findUserById(getUserFromSession().getId()));
        return "teacher/profile";
    }

    @GetMapping("/edit")
    public String editProfilePage(Model model) {
        model.addAttribute("teacher", userService.findUserById(getUserFromSession().getId()));
        return "teacher/edit";
    }

    @PatchMapping()
    public String editProfile(@ModelAttribute("teacher") @Valid User user,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "teacher/edit";
        }
        userService.updateUser(getUserFromSession().getId(), user);
        return "redirect:/teachers/profile";
    }

    @DeleteMapping()
    public String deleteProfile() {
        userService.deleteUser(getUserFromSession().getId());
        return "redirect:/logout";
    }


    @GetMapping("/courses/new")
    public String createCoursePage(@ModelAttribute("course") Course course){
        return "teacher/createCourse";
    }
    @PostMapping("/courses/new")
    public String createCourse(@ModelAttribute("course") @Valid Course course,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "teacher/createCourse";
        courseService.save(course);
        return "redirect:/teachers/myProfile";
    }
    @GetMapping("/editCourse")
    public String editCoursePage(){
        return "";
    }

    private User getUserFromSession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        return user;
    }
}
