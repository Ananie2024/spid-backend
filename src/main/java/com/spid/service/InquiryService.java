package com.spid.service;

import com.spid.dto.request.InquiryRequest;
import com.spid.dto.response.InquiryResponse;
import com.spid.entity.Inquiry;
import com.spid.exception.ResourceNotFoundException;
import com.spid.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class InquiryService {
    @Autowired
    private InquiryRepository inquiryRepository;
    
    @Autowired
    private JavaMailSender mailSender;
    
    public InquiryResponse createInquiry(InquiryRequest request) {
        Inquiry inquiry = new Inquiry();
        inquiry.setName(request.getName());
        inquiry.setEmail(request.getEmail());
        inquiry.setPhone(request.getPhone());
        inquiry.setMessage(request.getMessage());
        inquiry.setService(request.getService());
        inquiry.setIsRead(false);
        
        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        
        // Send confirmation email
        sendConfirmationEmail(inquiry);
        
        return convertToResponse(savedInquiry);
    }
    
    public InquiryResponse markAsRead(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found with id: " + id));
        inquiry.setIsRead(true);
        Inquiry updatedInquiry = inquiryRepository.save(inquiry);
        return convertToResponse(updatedInquiry);
    }
    
    public void deleteInquiry(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found with id: " + id));
        inquiryRepository.delete(inquiry);
    }
    
    public Page<InquiryResponse> getAllInquiries(Pageable pageable) {
        return inquiryRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::convertToResponse);
    }
    
    public Page<InquiryResponse> getUnreadInquiries(Pageable pageable) {
        return inquiryRepository.findByIsReadOrderByCreatedAtDesc(false, pageable)
                .map(this::convertToResponse);
    }
    
    public long getUnreadCount() {
        return inquiryRepository.findByIsReadOrderByCreatedAtDesc(false, Pageable.unpaged()).getTotalElements();
    }
    
    private void sendConfirmationEmail(Inquiry inquiry) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(inquiry.getEmail());
            message.setSubject("Thank you for contacting SPID Construction");
            message.setText("Dear " + inquiry.getName() + ",\n\n" +
                    "Thank you for your inquiry. We have received your message and will get back to you within 24 hours.\n\n" +
                    "Your message: " + inquiry.getMessage() + "\n\n" +
                    "Best regards,\n" +
                    "SPID Construction Team");
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
    
    private InquiryResponse convertToResponse(Inquiry inquiry) {
        InquiryResponse response = new InquiryResponse();
        response.setId(inquiry.getId());
        response.setName(inquiry.getName());
        response.setEmail(inquiry.getEmail());
        response.setPhone(inquiry.getPhone());
        response.setMessage(inquiry.getMessage());
        response.setService(inquiry.getService());
        response.setIsRead(inquiry.getIsRead());
        response.setCreatedAt(inquiry.getCreatedAt());
        return response;
    }
}
