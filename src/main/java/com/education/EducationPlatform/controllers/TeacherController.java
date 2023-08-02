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

    @Autowired
    public TeacherController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/profile")
    public String myProfilePage(){
        //TODO
        return "teacher/myProfile";
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
    @GetMapping("/courses/{course_id}/edit")
    public String editCoursePage(@PathVariable("course_id") Integer course_id, Model model){
        Course course = courseService.findCourseById(course_id);
        model.addAttribute("course", course);
        if (hasCourse(course))
            return "teacher/course/edit";
        return "teacher/course/show";
    }
    @PatchMapping("/courses/{course_id}")
    public String editCourse(@PathVariable("course_id") Integer course_id, @ModelAttribute("course") @Valid Course course,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "teacher/course/edit";
        courseService.updateCourse(course_id, course);
        return "redirect:/teachers/courses/" + course_id;
    }
    @DeleteMapping("/courses/{course_id}")
    public String deleteCourse(@PathVariable("course_id") Integer courseId){
        courseService.deleteCourse(courseId);
        return "redirect:/teachers/profile";
    }
    private boolean hasCourse(Course course){
        User user = getUserFromSession();
        User teacher = userService.findUserById(user.getId());
        return teacher.getCourses().contains(course);
    }
    private User getUserFromSession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        return user;
    }
}
