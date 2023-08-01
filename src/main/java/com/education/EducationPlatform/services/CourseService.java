package com.education.EducationPlatform.services;

import com.education.EducationPlatform.models.Course;
import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.repositories.CourseRepository;
import com.education.EducationPlatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public void enrollUser(int userId, int courseId) {
        Course course = courseRepository.findById(courseId).get();
        User user = userRepository.findById(userId).get();
        user.getCourses().add(course);
        course.getUsers().add(user);
    }

    @Transactional
    public void dropUser(int userId, int courseId) {
        Course course = courseRepository.findById(courseId).get();
        User user = userRepository.findById(userId).get();
        user.getCourses().remove(course);
        course.getUsers().remove(user);
    }
}
