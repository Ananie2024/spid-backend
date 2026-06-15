package com.spid.controller;

import com.spid.dto.request.InquiryRequest;
import com.spid.dto.response.InquiryResponse;
import com.spid.service.InquiryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inquiries")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;
    
    // Public endpoint - anyone can submit inquiry
    @PostMapping("/public/submit")
    public ResponseEntity<InquiryResponse> submitInquiry(@Valid @RequestBody InquiryRequest request) {
        return new ResponseEntity<>(inquiryService.createInquiry(request), HttpStatus.CREATED);
    }
    
    // Admin endpoints
    @GetMapping("/admin/all")
    public ResponseEntity<Page<InquiryResponse>> getAllInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(inquiryService.getAllInquiries(pageable));
    }
    
    @GetMapping("/admin/unread")
    public ResponseEntity<Page<InquiryResponse>> getUnreadInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(inquiryService.getUnreadInquiries(pageable));
    }
    
    @GetMapping("/admin/count/unread")
    public ResponseEntity<Long> getUnreadCount() {
        return ResponseEntity.ok(inquiryService.getUnreadCount());
    }
    
    @PutMapping("/admin/read/{id}")
    public ResponseEntity<InquiryResponse> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(inquiryService.markAsRead(id));
    }
    
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteInquiry(@PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok().body("Inquiry deleted successfully");
    }
}
