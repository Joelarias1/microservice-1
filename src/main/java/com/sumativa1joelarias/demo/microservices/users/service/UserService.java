package com.sumativa1joelarias.demo.microservices.users.service;

import com.sumativa1joelarias.demo.microservices.users.dto.UserDTO;
import com.sumativa1joelarias.demo.microservices.users.dto.UserManagementRequest;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserManagementRequest request);
    UserDTO updateUser(Long id, UserManagementRequest request);
    void deleteUser(Long id);
} 