package com.spid.controller;

import com.spid.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/uploads")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UploadController {
    @Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        String fileUrl = fileStorageService.storeFile(file, "temp/");
        Map<String, String> response = new HashMap<>();
        response.put("url", fileUrl);
        response.put("message", "File uploaded successfully");
        return ResponseEntity.ok(response);
    }
}
