package com.example.authorsapi.repository;

import com.example.authorsapi.model.Author;
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

@Repository
public class AuthorRepository {
    private final String DATA_FILE = "data/authors.json";
    private final ObjectMapper objectMapper;
    private Map<Long, Author> authors = new ConcurrentHashMap<>();
    private long currentId = 1;

    public AuthorRepository(ObjectMapper objectMapper) {
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
                Map<String, List<Author>> data = objectMapper.readValue(file, new TypeReference<Map<String, List<Author>>>() {});
                authors.clear();
                data.getOrDefault("authors", new ArrayList<>()).forEach(author -> {
                    if (author.getId() == null) {
                        author.setId(currentId++);
                    } else {
                        currentId = Math.max(currentId, author.getId() + 1);
                    }
                    authors.put(author.getId(), author);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            Map<String, List<Author>> data = Map.of("authors", new ArrayList<>(authors.values()));
            objectMapper.writeValue(new File(DATA_FILE), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Author> findAll() {
        return new ArrayList<>(authors.values());
    }

    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(authors.get(id));
    }

    public Author save(Author author) {
        if (author.getId() == null) {
            author.setId(currentId++);
        }
        authors.put(author.getId(), author);
        saveData();
        return author;
    }

    public void deleteById(Long id) {
        authors.remove(id);
        saveData();
    }
}