package com.education.EducationPlatform.controllers;

import com.education.EducationPlatform.models.Course;
import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.services.CourseService;
import com.education.EducationPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    private final CourseService courseService;

    @Autowired
    public AdminController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }


    //User control
    @GetMapping("/users")
    public String showAllUsersPage(Model model) {
        model.addAttribute("users",userService.findAllUsers());
        return "user/index";
    }

    @GetMapping("/users/{id}")
    public String showUserPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "user/show";
    }

    @GetMapping("/users/new")
    public String addUserPage(@ModelAttribute("user")User user) {
        return "user/new";
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "user/new";
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUserPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "user/edit";
    }

    @PatchMapping("/users/{id}")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "user/edit";
        userService.updateUser(id, user);
        return "redirect:/admin/users/" + id;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    //Course control
    @GetMapping("/courses")
    public String showAllCoursesPage(Model model) {
        model.addAttribute("courses",courseService.findAllCourses());
        return "course/index";
    }

    @GetMapping("/courses/{id}")
    public String showCoursePage(@PathVariable("id") int id, Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        return "course/show";
    }

    @GetMapping("/courses/new")
    public String addCoursePage(@ModelAttribute("course") Course course) {
        return "course/new";
    }

    @PostMapping("/courses")
    public String addCourse(@ModelAttribute("course") @Valid Course course,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "course/new";
        courseService.addCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/{id}/edit")
    public String editCoursePage(@PathVariable("id") int id, Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        return "course/edit";
    }

    @PatchMapping("/courses/{id}")
    public String editCourse(@ModelAttribute("course") @Valid Course course,
                           BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "course/edit";
        courseService.updateCourse(id, course);
        return "redirect:/admin/courses/" + id;
    }

    @DeleteMapping("/courses/{id}")
    public String deleteCourse(@PathVariable("id") int id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses";
    }
}
