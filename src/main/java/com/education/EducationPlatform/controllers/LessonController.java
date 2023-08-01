package com.education.EducationPlatform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/lesson")
@Controller
public class LessonController {
    @GetMapping()
    public String lobby(){
        return "lesson/lobby";
    }
    @GetMapping("/room")
    public String room(@RequestParam(value = "room", required = false) Integer roomId){
        return "lesson/room";
    }
}
