package com.example.desplieguecheapshopbackend.service;

import com.example.desplieguecheapshopbackend.domain.model.User;
import com.example.desplieguecheapshopbackend.domain.repository.UserRepository;
import com.example.desplieguecheapshopbackend.domain.service.UserService;
import com.example.desplieguecheapshopbackend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return userRepository.save(
                user.setFirstname(userRequest.getFirstname())
                        .setLastname(userRequest.getLastname())
                        .setPostalCode(userRequest.getPostalCode())
                        .setDateOfBirth(userRequest.getDateOfBirth())
                        .setAddress(userRequest.getAddress())
                        .setPhoneNumber(userRequest.getPhoneNumber()));
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}