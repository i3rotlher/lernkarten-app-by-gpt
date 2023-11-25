package com.lernkartenapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lernkartenapp.model.User;
import com.lernkartenapp.repository.UserRepository;
import com.lernkartenapp.util.JwtUtil;

import java.util.NoSuchElementException;
import java.util.Optional;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private KarteiboxService karteiboxService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setPassword("pass123");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertNotEquals("pass123", createdUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        Optional<User> userOptional = Optional.of(new User());
        when(userRepository.findByEmail(email)).thenReturn(userOptional);
    
        Optional<User> result = userService.findByEmail(email);
    
        assertTrue(result.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLoginSuccess() {
        String email = "test@example.com";
        String password = "pass123";
        User user = new User();
        user.setId("1");
        user.setPassword(passwordEncoder.encode(password));
    
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user.getId())).thenReturn("token");
    
        String token = userService.login(email, password);
    
        assertNotNull(token);
    }
    
    @Test
    public void testLoginFailure() {
        String email = "test@example.com";
        String password = "wrongpass";
        User user = new User();
        user.setPassword(passwordEncoder.encode("pass123"));
    
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    
        String token = userService.login(email, password);
    
        assertNull(token);
    }

    @Test
    public void testDeleteUser() {
        String userId = "1";
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(karteiboxService).deleteKarteiboxenByUser(userId);
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(karteiboxService, times(1)).deleteKarteiboxenByUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testFindByEmailFound() {
        String email = "test@example.com";
        Optional<User> userOptional = Optional.of(new User());
        when(userRepository.findByEmail(email)).thenReturn(userOptional);

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testFindByEmailNotFound() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByEmail(email);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    // ... (Andere Testmethoden)

    @Test
    public void testDeleteUserNotFound() {
        String userId = "nonexistent";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.deleteUser(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).deleteById(anyString());
    }

    @Test
    public void testLoginInvalidCredentials() {
        String email = "user@example.com";
        String password = "wrongPassword";
        User user = new User();
        user.setPassword(passwordEncoder.encode("correctPassword"));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        String result = userService.login(email, password);

        assertNull(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

}
