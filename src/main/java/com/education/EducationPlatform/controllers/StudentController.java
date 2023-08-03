package com.education.EducationPlatform.controllers;


import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.services.CourseService;
import com.education.EducationPlatform.services.UserService;
import com.education.EducationPlatform.utils.SecurityContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final CourseService courseService;
    private final UserService userService;
    private final SecurityContextManager securityContextManager;

    @Autowired
    public StudentController(CourseService courseService, UserService userService, SecurityContextManager securityContextManager) {
        this.courseService = courseService;
        this.userService = userService;
        this.securityContextManager = securityContextManager;
    }

    @GetMapping("/courses")
    public String showAllCoursesPage(Model model) {
        securityContextManager.enrichModel(model);
        model.addAttribute("courses",courseService.findAllCourses());
        return "student/course/index";
    }

    @GetMapping("/courses/{id}")
    public String showCoursePage(@PathVariable("id") int id, Model model) {
        securityContextManager.enrichModel(model);
        model.addAttribute("course", courseService.findCourseById(id));
        User user = userService.findUserById(securityContextManager.getUserFromSession().getId());
        model.addAttribute("user", user);
        return "student/course/show";
    }

    @PostMapping("/courses/{id}/enroll")
    public String enrollToCourse(@PathVariable("id") int courseId, Model model) {
        securityContextManager.enrichModel(model);
       int studentId = securityContextManager.getUserFromSession().getId();
       courseService.enrollUser(studentId, courseId);
       return "redirect:/students/courses/" + courseId;
    }

    @PostMapping("/courses/{id}/drop")
    public String dropCourse(@PathVariable("id") int courseId) {
        int studentId = securityContextManager.getUserFromSession().getId();
        courseService.dropUser(studentId, courseId);
        return "redirect:/students/courses/" + courseId;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        securityContextManager.enrichModel(model);
        model.addAttribute("student", userService.findUserById(securityContextManager.getUserFromSession().getId()));
        return "student/profile";
    }

    @GetMapping("/edit")
    public String editProfilePage(Model model) {
        securityContextManager.enrichModel(model);
        model.addAttribute("student", userService.findUserById(securityContextManager.getUserFromSession().getId()));
        return "student/edit";
    }

    @PatchMapping()
    public String editProfile(@ModelAttribute("student") @Valid User user,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "student/edit";
        }
        userService.updateUser(securityContextManager.getUserFromSession().getId(), user);
        return "redirect:/students/profile";
    }

    @DeleteMapping()
    public String deleteProfile() {
        userService.deleteUser(securityContextManager.getUserFromSession().getId());
        return "redirect:/logout";
    }
}
