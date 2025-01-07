package com.example.authorsapi.service;

import com.example.authorsapi.model.Author;
import com.example.authorsapi.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }
    
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
    
    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found"));
            
        author.setName(authorDetails.getName());
        author.setEmail(authorDetails.getEmail());
        author.setBio(authorDetails.getBio());
        
        return authorRepository.save(author);
    }
    
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}