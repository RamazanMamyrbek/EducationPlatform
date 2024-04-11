package com.education.EducationPlatform.controllers;

import com.education.EducationPlatform.models.Course;
import com.education.EducationPlatform.models.CourseStatus;
import com.education.EducationPlatform.models.Role;
import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.services.CourseService;
import com.education.EducationPlatform.services.UserService;
import com.education.EducationPlatform.utils.SecurityContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/lesson")
@Controller
public class LessonController {
    private final SecurityContextManager securityContextManager;
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public LessonController(SecurityContextManager securityContextManager, CourseService courseService, UserService userService) {
        this.securityContextManager = securityContextManager;
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping()
    public String lobby(){
        return "lesson/lobby";
    }
    @GetMapping("/room")
    public String room(@RequestParam(value = "room", required = false) Integer roomId,
                       Model model){
        Course course = courseService.findCourseById(roomId);
        if (!hasCourse(course) || course.getStatus() == CourseStatus.INACTIVE)
            if (getRole() == Role.ROLE_STUDENT)
                return "redirect:/students/profile";
            else
                return "redirect:/teachers/profile";
        model.addAttribute("course", courseService.findCourseById(roomId));
        model.addAttribute("user", securityContextManager.getUserFromSession());
        return "lesson/room";
    }
    private Role getRole(){
        User user = securityContextManager.getUserFromSession();
        return user.getRole();
    }
    private boolean hasCourse(Course course){
        User user = securityContextManager.getUserFromSession();
        User teacher = userService.findUserById(user.getId());
        return teacher.getCourses().contains(course);
    }
}
