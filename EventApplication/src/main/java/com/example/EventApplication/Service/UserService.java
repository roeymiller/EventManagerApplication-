package com.example.EventApplication.Service;

import com.example.EventApplication.model.User;
import com.example.EventApplication.model.request.UserBuilder;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
//    String authenticateUser(String username, String password);
    List<User> getAllUsers();

    Optional<User> getUserById(long id);

    User addUser(UserBuilder userBuilder);

    User updateUser(UserBuilder userBuilder, long id);

    void deleteById(long id);

    List<User> getUsersById(Set<Long> ids);
}
