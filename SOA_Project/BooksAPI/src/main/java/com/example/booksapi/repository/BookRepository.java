package com.example.booksapi.repository;

import com.example.booksapi.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private final String DATA_FILE = "data/books.json";
    private final ObjectMapper objectMapper;
    private Map<Long, Book> books = new ConcurrentHashMap<>();
    private long currentId = 1;

    public BookRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        createDataDirectory();
        loadData();
    }

    private void createDataDirectory() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void loadData() {
        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                Map<String, List<Book>> data = objectMapper.readValue(file, new TypeReference<Map<String, List<Book>>>() {});
                books.clear();
                data.getOrDefault("books", new ArrayList<>()).forEach(book -> {
                    if (book.getId() == null) {
                        book.setId(currentId++);
                    } else {
                        currentId = Math.max(currentId, book.getId() + 1);
                    }
                    books.put(book.getId(), book);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            Map<String, List<Book>> data = Map.of("books", new ArrayList<>(books.values()));
            objectMapper.writeValue(new File(DATA_FILE), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public List<Book> findByAuthorId(Long authorId) {
        return books.values().stream()
                .filter(book -> book.getAuthorId().equals(authorId))
                .collect(Collectors.toList());
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(currentId++);
        }
        books.put(book.getId(), book);
        saveData();
        return book;
    }

    public void deleteById(Long id) {
        books.remove(id);
        saveData();
    }
}