package com.spid.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class ProjectRequest {
    @NotBlank
    private String titleEn;
    
    @NotBlank
    private String titleFr;
    
    private String descriptionEn;
    private String descriptionFr;
    private String locationEn;
    private String locationFr;
    private Integer year;
    private String category;
    private MultipartFile coverImage;
    
    // Getters and Setters
    public String getTitleEn() { return titleEn; }
    public void setTitleEn(String titleEn) { this.titleEn = titleEn; }
    public String getTitleFr() { return titleFr; }
    public void setTitleFr(String titleFr) { this.titleFr = titleFr; }
    public String getDescriptionEn() { return descriptionEn; }
    public void setDescriptionEn(String descriptionEn) { this.descriptionEn = descriptionEn; }
    public String getDescriptionFr() { return descriptionFr; }
    public void setDescriptionFr(String descriptionFr) { this.descriptionFr = descriptionFr; }
    public String getLocationEn() { return locationEn; }
    public void setLocationEn(String locationEn) { this.locationEn = locationEn; }
    public String getLocationFr() { return locationFr; }
    public void setLocationFr(String locationFr) { this.locationFr = locationFr; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public MultipartFile getCoverImage() { return coverImage; }
    public void setCoverImage(MultipartFile coverImage) { this.coverImage = coverImage; }
}
