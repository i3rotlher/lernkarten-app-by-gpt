package com.lernkartenapp.service;

import com.lernkartenapp.model.User;
import com.lernkartenapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.createUser(user);

        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("hashedPassword", savedUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Weitere Tests...
}
