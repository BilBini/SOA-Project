package com.example.mainapp.model;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;
    private Long authorId;
    private String isbn;
    private Integer publicationYear;
    private String description;
    private String genre;
}