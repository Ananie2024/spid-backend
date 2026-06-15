package com.spid.controller;

import com.spid.dto.request.ProjectRequest;
import com.spid.dto.response.ProjectResponse;
import com.spid.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    
    // Admin endpoints
    @PostMapping("/admin/create")
    public ResponseEntity<ProjectResponse> createProject(@Valid @ModelAttribute ProjectRequest request) throws Exception {
        return new ResponseEntity<>(projectService.createProject(request), HttpStatus.CREATED);
    }
    
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @ModelAttribute ProjectRequest request) throws Exception {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }
    
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) throws Exception {
        projectService.deleteProject(id);
        return ResponseEntity.ok().body("Project deleted successfully");
    }
    
    @PostMapping("/admin/{projectId}/images")
    public ResponseEntity<?> addProjectImage(@PathVariable Long projectId, @RequestParam("image") MultipartFile image) throws Exception {
        projectService.addProjectImage(projectId, image);
        return ResponseEntity.ok().body("Image added successfully");
    }
    
    @DeleteMapping("/admin/images/{imageId}")
    public ResponseEntity<?> deleteProjectImage(@PathVariable Long imageId) throws Exception {
        projectService.deleteProjectImage(imageId);
        return ResponseEntity.ok().body("Image deleted successfully");
    }
    
    // Public endpoints
    @GetMapping("/public/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }
    
    @GetMapping("/public/all")
    public ResponseEntity<Page<ProjectResponse>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(projectService.getAllProjects(pageable));
    }
}
