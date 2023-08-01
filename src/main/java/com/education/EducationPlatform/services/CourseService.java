package com.education.EducationPlatform.services;

import com.education.EducationPlatform.models.Course;
import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAllCourses(){
        return courseRepository.findAll();
    }

    public Course findCourseById(int id) {
        return courseRepository.findById(id).get();
    }

    @Transactional
    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    @Transactional
    public void updateCourse(int id, Course courseToBeUpdated) {
        courseToBeUpdated.setId(id);
        courseRepository.save(courseToBeUpdated);
    }

    @Transactional
    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }
}
