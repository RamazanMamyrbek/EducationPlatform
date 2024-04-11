package com.education.EducationPlatform.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Table(name = "courses")
@Entity
@Data
public class Course {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "This field shouldn't be empty")
    @Size(min = 2, max = 30, message = "This field should be between 2 and 30 characters")
    private String name;


    @Column(name = "price")
    @Min(value = 0)
    private int price;

    @Column(name = "link")
    private String link;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private CourseStatus status;

    @ManyToMany(mappedBy = "courses")
    private List<User> users;

    public void addUser(User user){
        if (users == null)
            users = new ArrayList<>();
        users.add(user);
    }
}
