package com.lernkartenapp.service;

import com.lernkartenapp.model.User;
import com.lernkartenapp.repository.UserRepository;
import com.lernkartenapp.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil; // Injiziere JwtUtil

    @Autowired
    private KarteiboxService karteiboxService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    } 

    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return jwtUtil.generateToken( user.get().getId()); // Verwende jwtUtil statt statischer Methode
        }
        return null;
    }

    public void deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            karteiboxService.deleteKarteiboxenByUser(id); // Lösche zuerst alle Karteiboxen und Karteikarten des Benutzers
            userRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Benutzer mit der ID " + id + " nicht gefunden");
        }
    }

}
