package com.spid.service;

import com.spid.dto.request.ProjectRequest;
import com.spid.dto.response.ProjectResponse;
import com.spid.entity.Project;
import com.spid.entity.ProjectImage;
import com.spid.exception.ResourceNotFoundException;
import com.spid.repository.ProjectRepository;
import com.spid.repository.ProjectImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectImageRepository projectImageRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) throws IOException {
        Project project = new Project();
        updateProjectEntity(project, request);
        
        if (request.getCoverImage() != null && !request.getCoverImage().isEmpty()) {
            String coverUrl = fileStorageService.storeFile(request.getCoverImage(), "projects/");
            project.setCoverImage(coverUrl);
        }
        
        Project savedProject = projectRepository.save(project);
        return convertToResponse(savedProject);
    }
    
    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request) throws IOException {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        
        if (request.getCoverImage() != null && !request.getCoverImage().isEmpty()) {
            if (project.getCoverImage() != null) {
                fileStorageService.deleteFile(project.getCoverImage());
            }
            String coverUrl = fileStorageService.storeFile(request.getCoverImage(), "projects/");
            project.setCoverImage(coverUrl);
        }
        
        updateProjectEntity(project, request);
        Project updatedProject = projectRepository.save(project);
        return convertToResponse(updatedProject);
    }
    
    @Transactional
    public void deleteProject(Long id) throws IOException {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        
        if (project.getCoverImage() != null) {
            fileStorageService.deleteFile(project.getCoverImage());
        }
        
        for (ProjectImage image : project.getImages()) {
            fileStorageService.deleteFile(image.getImageUrl());
        }
        
        projectRepository.delete(project);
    }
    
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return convertToResponse(project);
    }
    
    public Page<ProjectResponse> getAllProjects(Pageable pageable) {
        return projectRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::convertToResponse);
    }
    
    @Transactional
    public void addProjectImage(Long projectId, MultipartFile image) throws IOException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
        
        String imageUrl = fileStorageService.storeFile(image, "projects/");
        ProjectImage projectImage = new ProjectImage(imageUrl, project);
        projectImage.setDisplayOrder(project.getImages().size());
        projectImageRepository.save(projectImage);
    }
    
    @Transactional
    public void deleteProjectImage(Long imageId) throws IOException {
        ProjectImage image = projectImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageId));
        
        fileStorageService.deleteFile(image.getImageUrl());
        projectImageRepository.delete(image);
    }
    
    private void updateProjectEntity(Project project, ProjectRequest request) {
        project.setTitleEn(request.getTitleEn());
        project.setTitleFr(request.getTitleFr());
        project.setDescriptionEn(request.getDescriptionEn());
        project.setDescriptionFr(request.getDescriptionFr());
        project.setLocationEn(request.getLocationEn());
        project.setLocationFr(request.getLocationFr());
        project.setYear(request.getYear());
        project.setCategory(request.getCategory());
    }
    
    private ProjectResponse convertToResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setTitleEn(project.getTitleEn());
        response.setTitleFr(project.getTitleFr());
        response.setDescriptionEn(project.getDescriptionEn());
        response.setDescriptionFr(project.getDescriptionFr());
        response.setLocationEn(project.getLocationEn());
        response.setLocationFr(project.getLocationFr());
        response.setYear(project.getYear());
        response.setCategory(project.getCategory());
        response.setCoverImage(project.getCoverImage());
        response.setCreatedAt(project.getCreatedAt());
        
        List<String> imageUrls = project.getImages().stream()
                .sorted((a, b) -> a.getDisplayOrder().compareTo(b.getDisplayOrder()))
                .map(ProjectImage::getImageUrl)
                .collect(Collectors.toList());
        response.setImageUrls(imageUrls);
        
        return response;
    }
}
