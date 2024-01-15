package com.example.EventApplication.Service;

import com.example.EventApplication.Exception.AuthenticationException;
import com.example.EventApplication.Exception.NotFoundException;
import com.example.EventApplication.model.User;
import com.example.EventApplication.model.request.UserBuilder;
import com.example.EventApplication.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    @Override
//    public String authenticateUser(String username, String password) {
//        User user = userRepository.findByUserName(username);
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            String authToken = generateAuthToken(user.getId(), user.getUserName());
//            return authToken;
//        } else {
//            throw new AuthenticationException("Invalid username or password");
//        }
//    }
//
//    private String generateAuthToken(Long userId, String username) {
//        long expirationTime = 86400000; // 24 hours in milliseconds
//        byte[] secretKeyBytes = new byte[32];
//        SecureRandom secureRandom = new SecureRandom();
//        secureRandom.nextBytes(secretKeyBytes);
//        String secretKey = Base64.getEncoder().encodeToString(secretKeyBytes);
//
//        Date now = new Date();
//        Date expiration = new Date(now.getTime() + expirationTime);
//
//        return Jwts.builder()
//                .setSubject(userId.toString())
//                .setIssuedAt(now)
//                .setExpiration(expiration)
//                .compact();
//    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User addUser(UserBuilder userBuilder) {
        var newUser = modelMapper.map(userBuilder, User.class);
        newUser.setCreationDate(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(UserBuilder userBuilder, long id) {
        var optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            var updatedUser = optionalUser.get();
            updatedUser.setEmail(userBuilder.getEmail());
            updatedUser.setUserName(userBuilder.getUserName());
            updatedUser.setPhone(userBuilder.getPhone());
            updatedUser.setPassword(userBuilder.getPassword());
            return userRepository.save(updatedUser);
        } else {
            throw new NotFoundException("User with ID " + id + " not found");
        }
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersById(Set<Long> ids) {
        return userRepository.findAllById(ids);
    }
}
