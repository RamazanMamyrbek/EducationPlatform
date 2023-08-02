package com.education.EducationPlatform.controllers;


import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.security.MyUserDetails;
import com.education.EducationPlatform.services.CourseService;
import com.education.EducationPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public StudentController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/courses")
    public String showAllCoursesPage(Model model) {
        model.addAttribute("courses",courseService.findAllCourses());
        return "student/course/index";
    }

    @GetMapping("/courses/{id}")
    public String showCoursePage(@PathVariable("id") int id, Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        User user = userService.findUserById(getUserFromSession().getId());
        model.addAttribute("user", user);
        return "student/course/show";
    }

    @PostMapping("/courses/{id}/enroll")
    public String enrollToCourse(@PathVariable("id") int courseId) {
       int studentId = getUserFromSession().getId();
       courseService.enrollUser(studentId, courseId);
       return "redirect:/students/courses/" + courseId;
    }

    @PostMapping("/courses/{id}/drop")
    public String dropCourse(@PathVariable("id") int courseId) {
        int studentId = getUserFromSession().getId();
        courseService.dropUser(studentId, courseId);
        return "redirect:/students/courses/" + courseId;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("student", userService.findUserById(getUserFromSession().getId()));
        return "student/profile";
    }

    private User getUserFromSession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        return user;
    }



}
