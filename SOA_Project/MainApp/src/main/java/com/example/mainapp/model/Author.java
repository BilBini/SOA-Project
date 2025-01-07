package com.example.mainapp.model;

import lombok.Data;

@Data
public class Author {
    private Long id;
    private String name;
    private String email;
    private String bio;
}