package com.spid.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
public class BusinessService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nameEn;
    
    @Column(nullable = false)
    private String nameFr;
    
    @Column(length = 1000)
    private String descriptionEn;
    
    @Column(length = 1000)
    private String descriptionFr;
    
    private String icon;
    
    private Integer displayOrder = 0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public BusinessService() {}
    
    public BusinessService(String nameEn, String nameFr) {
        this.nameEn = nameEn;
        this.nameFr = nameFr;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getNameEn() { return nameEn; }
    public String getNameFr() { return nameFr; }
    public String getDescriptionEn() { return descriptionEn; }
    public String getDescriptionFr() { return descriptionFr; }
    public String getIcon() { return icon; }
    public Integer getDisplayOrder() { return displayOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }
    public void setNameFr(String nameFr) { this.nameFr = nameFr; }
    public void setDescriptionEn(String descriptionEn) { this.descriptionEn = descriptionEn; }
    public void setDescriptionFr(String descriptionFr) { this.descriptionFr = descriptionFr; }
    public void setIcon(String icon) { this.icon = icon; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}