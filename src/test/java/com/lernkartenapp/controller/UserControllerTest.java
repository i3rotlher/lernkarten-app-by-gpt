package com.lernkartenapp.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lernkartenapp.model.User;
import com.lernkartenapp.service.UserService;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setId("1");

        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/register")
                .contentType(APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\",\"password\":\"pass123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser("1");

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginUser() throws Exception {
        String jwt = "test.jwt.token";
        when(userService.login("test@example.com", "pass123")).thenReturn(jwt);

        mockMvc.perform(post("/users/login")
                .contentType(APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\",\"password\":\"pass123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(jwt));
    }

    @Test
    public void testLoginUserUnauthorized() throws Exception {
        when(userService.login("wrong@example.com", "wrongpass")).thenReturn(null);

        mockMvc.perform(post("/users/login")
                .contentType(APPLICATION_JSON)
                .content("{\"email\":\"wrong@example.com\",\"password\":\"wrongpass\"}"))
                .andExpect(status().isUnauthorized());
    }
}
