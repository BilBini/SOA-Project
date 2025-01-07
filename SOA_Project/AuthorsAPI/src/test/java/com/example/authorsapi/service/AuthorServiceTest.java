package com.example.authorsapi.service;

import com.example.authorsapi.model.Author;
import com.example.authorsapi.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    
    @InjectMocks
    private AuthorService authorService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void getAllAuthors_ShouldReturnListOfAuthors() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("John Doe");
        
        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Jane Smith");
        
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));
        
        List<Author> authors = authorService.getAllAuthors();
        
        assertEquals(2, authors.size());
        verify(authorRepository, times(1)).findAll();
    }
    
    @Test
    void getAuthorById_WhenAuthorExists_ShouldReturnAuthor() {
        Author author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        
        Optional<Author> result = authorService.getAuthorById(1L);
        
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }
    
    @Test
    void createAuthor_ShouldReturnSavedAuthor() {
        Author author = new Author();
        author.setName("John Doe");
        
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        
        Author savedAuthor = authorService.createAuthor(author);
        
        assertNotNull(savedAuthor);
        assertEquals("John Doe", savedAuthor.getName());
    }
}