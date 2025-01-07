package com.example.mainapp.service;

import com.example.mainapp.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final RestTemplate restTemplate;
    
    @Value("${api.books.url}")
    private String booksApiUrl;
    
    public List<Book> getAllBooks() {
        return restTemplate.exchange(
            booksApiUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Book>>() {}
        ).getBody();
    }
    
    public List<Book> getBooksByAuthorId(Long authorId) {
        return restTemplate.exchange(
            booksApiUrl + "/author/" + authorId,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Book>>() {}
        ).getBody();
    }
    
    public Book getBookById(Long id) {
        return restTemplate.getForObject(booksApiUrl + "/" + id, Book.class);
    }
    
    public Book createBook(Book book) {
        return restTemplate.postForObject(booksApiUrl, book, Book.class);
    }
    
    public void updateBook(Long id, Book book) {
        restTemplate.put(booksApiUrl + "/" + id, book);
    }
    
    public void deleteBook(Long id) {
        restTemplate.delete(booksApiUrl + "/" + id);
    }
}