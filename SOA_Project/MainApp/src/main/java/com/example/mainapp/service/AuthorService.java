package com.example.mainapp.service;

import com.example.mainapp.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final RestTemplate restTemplate;
    
    @Value("${api.authors.url}")
    private String authorsApiUrl;
    
    public List<Author> getAllAuthors() {
        return restTemplate.exchange(
            authorsApiUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Author>>() {}
        ).getBody();
    }
    
    public Author getAuthorById(Long id) {
        return restTemplate.getForObject(authorsApiUrl + "/" + id, Author.class);
    }
    
    public Author createAuthor(Author author) {
        return restTemplate.postForObject(authorsApiUrl, author, Author.class);
    }
    
    public void updateAuthor(Long id, Author author) {
        restTemplate.put(authorsApiUrl + "/" + id, author);
    }
    
    public void deleteAuthor(Long id) {
        restTemplate.delete(authorsApiUrl + "/" + id);
    }
}