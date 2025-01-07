package com.example.mainapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;

@RestController
@RequestMapping("/api/system")
public class SystemController {
    
    @PostMapping("/ensure-data-directory")
    public ResponseEntity<Void> ensureDataDirectory() {
        File dataDir = new File("../data");
        if (!dataDir.exists()) {
            if (dataDir.mkdirs()) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}