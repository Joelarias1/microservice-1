package com.sumativa1joelarias.demo.microservices.users.service;

import com.sumativa1joelarias.demo.microservices.users.dto.UserDTO;
import com.sumativa1joelarias.demo.microservices.users.dto.UserManagementRequest;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import com.sumativa1joelarias.demo.microservices.users.exception.ResourceNotFoundException;
import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserManagementRequest request) {
        if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Username o Email ya existen.");
        }
        
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        if (request.getPassword() == null || request.getPassword().isBlank()){
             throw new IllegalArgumentException("La contraseña es requerida para crear un usuario.");
        }
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        
        newUser.setRole(request.getRole() != null ? request.getRole() : UserRole.USER); 
        newUser.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");

        User savedUser = userRepository.save(newUser);
        return convertToDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserManagementRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            if (!user.getId().equals(id)) throw new IllegalArgumentException("Username ya existe.");
        });
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            if (!user.getId().equals(id)) throw new IllegalArgumentException("Email ya existe.");
        });

        existingUser.setUsername(request.getUsername());
        existingUser.setEmail(request.getEmail());
        
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        if (request.getRole() != null) {
             existingUser.setRole(request.getRole());
        }
        if (request.getStatus() != null) {
            existingUser.setStatus(request.getStatus());
        }
        
        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }
} 