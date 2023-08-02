package com.education.EducationPlatform.controllers;

import com.education.EducationPlatform.models.Course;
import com.education.EducationPlatform.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final CourseService courseService;

    @Autowired
    public TeacherController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/myProfile")
    public String myProfilePage(){
        //TODO
        return "teacher/myProfile";
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

    }
}
