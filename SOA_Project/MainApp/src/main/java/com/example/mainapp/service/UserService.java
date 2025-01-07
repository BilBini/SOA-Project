package com.example.mainapp.service;

import com.example.mainapp.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final String DATA_DIR = "data";
    private final String DATA_FILE = DATA_DIR + "/users.json";
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private Map<String, User> users = new ConcurrentHashMap<>();

    public UserService(ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        createDataDirectory();
        loadData();
        if (users.isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            users.put(admin.getUsername(), admin);
            saveData();
        }
    }

    private void createDataDirectory() {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try {
                Map<String, List<User>> data = objectMapper.readValue(file, new TypeReference<Map<String, List<User>>>() {});
                users.clear();
                data.getOrDefault("users", new ArrayList<>()).forEach(user -> 
                    users.put(user.getUsername(), user)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        try {
            File file = new File(DATA_FILE);
            Map<String, List<User>> data = Map.of("users", new ArrayList<>(users.values()));
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public void registerUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.put(user.getUsername(), user);
        saveData();
    }

    public long getUserCount() {
        return users.size();
    }
}