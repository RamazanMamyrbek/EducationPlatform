package com.education.EducationPlatform.services;

import com.education.EducationPlatform.models.User;
import com.education.EducationPlatform.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(int id, User userToBeUpdated) {
        userToBeUpdated.setId(id);
        userToBeUpdated.setPassword(passwordEncoder.encode(userToBeUpdated.getPassword()));
        userRepository.save(userToBeUpdated);
    }
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
