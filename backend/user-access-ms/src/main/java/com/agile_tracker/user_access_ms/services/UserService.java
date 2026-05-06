package com.agile_tracker.user_access_ms.services;

import com.agile_tracker.user_access_ms.dtos.users.UserResponse;
import com.agile_tracker.user_access_ms.dtos.users.UserUpdateRequest;
import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Integer id);

    UserResponse updateUser(Integer id, UserUpdateRequest request);

    void deleteUser(Integer id);
}
