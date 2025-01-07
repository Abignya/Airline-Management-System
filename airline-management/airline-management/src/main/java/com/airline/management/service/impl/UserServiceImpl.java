package com.airline.management.service.impl;

import com.airline.management.dto.UserRegistrationDTO;
import com.airline.management.model.Role;
import com.airline.management.model.User;
import com.airline.management.repository.RoleRepository;
import com.airline.management.repository.UserRepository;
import com.airline.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        // Check if username already exists
        if (userRepository.findByUsername(userRegistrationDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create and save the user
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        // Assign role
        Role role = roleRepository.findByName(userRegistrationDTO.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);

        userRepository.save(user);
    }

}
