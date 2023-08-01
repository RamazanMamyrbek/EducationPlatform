package com.education.EducationPlatform.services;

import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(int id, User userToBeUpdated) {
        userToBeUpdated.setId(id);
        userRepository.save(userToBeUpdated);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
