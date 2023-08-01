package com.education.EducationPlatform.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Table(name = "users")
@Entity
@Data
public class User{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    @NotEmpty(message = "This field shouldn't be empty")
    @Size(min = 2, max = 150, message = "This field should be between 2 and 30 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "This field shouldn't be empty")
    @Size(min = 2, max = 30, message = "This field should be between 2 and 30 characters")
    private String lastName;

    @Column(name = "email")
    @NotEmpty(message = "This field shouldn't be empty")
    @Email
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "This field shouldn't be empty")
    @Size(min = 8, max = 150, message = "This field should be between 8 and 30 characters")
    private String password;

    @Column(name = "phone_number")
    @NotEmpty(message = "This field shouldn't be empty")
    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(name = "users_courses",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;
}