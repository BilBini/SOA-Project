package com.example.mainapp.controller;

import com.example.mainapp.model.Author;
import com.example.mainapp.model.Book;
import com.example.mainapp.service.AuthorService;
import com.example.mainapp.service.BookService;
import com.example.mainapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final AuthorService authorService;
    private final BookService bookService;
    private final UserService userService;
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Get all data
        List<Author> authors = authorService.getAllAuthors();
        List<Book> allBooks = bookService.getAllBooks();
        
        // Calculate statistics
        long totalUsers = userService.getUserCount();
        long totalAuthors = authors.size();
        long totalBooks = allBooks.size();
        
        // Calculate mock growth percentages (replace with real calculations in production)
        int userGrowth = 15;
        int authorGrowth = 10;
        int bookGrowth = 20;
        
        // Get books by genre
        Map<String, Long> booksByGenre = allBooks.stream()
            .collect(Collectors.groupingBy(
                Book::getGenre,
                Collectors.counting()
            ));
            
        // Get books by author
        Map<Long, List<Book>> booksByAuthor = allBooks.stream()
            .collect(Collectors.groupingBy(Book::getAuthorId));
            
        // Create author books map
        Map<Long, List<Book>> authorBooks = new HashMap<>();
        authors.forEach(author -> {
            List<Book> authorBookList = booksByAuthor.getOrDefault(author.getId(), new ArrayList<>());
            authorBooks.put(author.getId(), authorBookList);
        });
        
        // Add all data to model
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalAuthors", totalAuthors);
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("userGrowth", userGrowth);
        model.addAttribute("authorGrowth", authorGrowth);
        model.addAttribute("bookGrowth", bookGrowth);
        model.addAttribute("booksByGenre", booksByGenre);
        model.addAttribute("authors", authors);
        model.addAttribute("authorBooks", authorBooks);
        
        return "dashboard";
    }
    
    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors";
    }
    
    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books";
    }
}