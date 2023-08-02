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
    private final UserService userService;
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public TeacherController(CourseService courseService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String myProfilePage(Model model){
        model.addAttribute("teacher", userService.findUserById(getUserFromSession().getId()));
        return "teacher/profile";
    }

    @GetMapping("/myProfile")
    public String myProfilePage(){
        //TODO
        return "teacher/myProfile";
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
        return "teacher/course/createCourse";
    }
    @GetMapping("/courses/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        model.addAttribute("user", userService.findUserById(getUserFromSession().getId()));
        model.addAttribute("course", courseService.findCourseById(id));
        return "teacher/course/show";
    }
    @PostMapping("/courses")
    public String createCourse(@ModelAttribute("course") @Valid Course course,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "teacher/course/createCourse";
        courseService.save(course);
        courseService.createCourseByTeacher(getUserFromSession().getId(), course.getId());
        return "redirect:/teachers/myProfile";
    }
    @GetMapping("/editCourse")
    public String editCoursePage(){

    }
}
