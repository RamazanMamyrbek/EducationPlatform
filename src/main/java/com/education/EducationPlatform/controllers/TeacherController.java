package com.education.EducationPlatform.controllers;

import com.education.EducationPlatform.models.Course;
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
@RequestMapping("/teachers")
public class TeacherController {
    private final UserService userService;
    private final CourseService courseService;
    private final SecurityContextManager securityContextManager;

    @Autowired
    public TeacherController(UserService userService, CourseService courseService, SecurityContextManager securityContextManager) {
        this.userService = userService;
        this.courseService = courseService;
        this.securityContextManager = securityContextManager;
    }

    @GetMapping("/profile")
    public String profilePage(Model model){
        securityContextManager.enrichModel(model);
        model.addAttribute("teacher", userService.findUserById(securityContextManager.getUserFromSession().getId()));
        return "teacher/profile";
    }
    @GetMapping("/edit")
    public String editProfilePage(Model model) {
        securityContextManager.enrichModel(model);
        model.addAttribute("teacher", userService.findUserById(securityContextManager.getUserFromSession().getId()));
        return "teacher/edit";
    }
    @PatchMapping()
    public String editProfile(@ModelAttribute("teacher") @Valid User user,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "teacher/edit";
        }
        userService.updateUser(securityContextManager.getUserFromSession().getId(), user);
        return "redirect:/teachers/profile";
    }
    @DeleteMapping()
    public String deleteProfile() {
        userService.deleteUser(securityContextManager.getUserFromSession().getId());
        return "redirect:/logout";
    }
    @GetMapping("/courses")
    public String coursesIndexPage(Model model){
        model.addAttribute("courses", courseService.findAllCourses());
        securityContextManager.enrichModel(model);
        return "teacher/course/index";
    }
    @GetMapping("/courses/new")
    public String createCoursePage(@ModelAttribute("course") Course course, Model model){
        securityContextManager.enrichModel(model);
        return "teacher/course/createCourse";
    }
    @GetMapping("/courses/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        securityContextManager.enrichModel(model);
        model.addAttribute("user", userService.findUserById(securityContextManager.getUserFromSession().getId()));
        model.addAttribute("course", courseService.findCourseById(id));
        return "teacher/course/show";
    }
    @PostMapping("/courses")
    public String createCourse(@ModelAttribute("course") @Valid Course course,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "teacher/course/createCourse";
        courseService.save(course);
        courseService.createCourseByTeacher(securityContextManager.getUserFromSession().getId(), course.getId());
        return "redirect:/teachers/profile";
    }
    @GetMapping("/courses/{course_id}/edit")
    public String editCoursePage(@PathVariable("course_id") Integer course_id, Model model){
        securityContextManager.enrichModel(model);
        Course course = courseService.findCourseById(course_id);
        model.addAttribute("course", course);
        if (hasCourse(course))
            return "teacher/course/edit";
        return "teacher/course/show";
    }
    @PatchMapping("/courses/{course_id}")
    public String editCourse(@PathVariable("course_id") Integer course_id,
                             @ModelAttribute("course") @Valid Course course,
                             BindingResult bindingResult) {
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
        User user = securityContextManager.getUserFromSession();
        User teacher = userService.findUserById(user.getId());
        return teacher.getCourses().contains(course);
    }
}