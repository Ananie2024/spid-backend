package com.spid.service;

import com.spid.dto.request.ServiceRequest;
import com.spid.dto.response.ServiceResponse;
import com.spid.entity.BusinessService;
import com.spid.exception.ResourceNotFoundException;
import com.spid.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;
    
    public ServiceResponse createService(ServiceRequest request) {
        BusinessService businessService = new BusinessService();
        businessService.setNameEn(request.getNameEn());
        businessService.setNameFr(request.getNameFr());
        businessService.setDescriptionEn(request.getDescriptionEn());
        businessService.setDescriptionFr(request.getDescriptionFr());
        businessService.setIcon(request.getIcon());
        if (request.getDisplayOrder() != null) {
            businessService.setDisplayOrder(request.getDisplayOrder());
        }
        
        BusinessService saved = serviceRepository.save(businessService);
        return convertToResponse(saved);
    }
    
    public ServiceResponse updateService(Long id, ServiceRequest request) {
        BusinessService businessService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        
        businessService.setNameEn(request.getNameEn());
        businessService.setNameFr(request.getNameFr());
        businessService.setDescriptionEn(request.getDescriptionEn());
        businessService.setDescriptionFr(request.getDescriptionFr());
        businessService.setIcon(request.getIcon());
        if (request.getDisplayOrder() != null) {
            businessService.setDisplayOrder(request.getDisplayOrder());
        }
        
        BusinessService updated = serviceRepository.save(businessService);
        return convertToResponse(updated);
    }
    
    public void deleteService(Long id) {
        BusinessService businessService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        serviceRepository.delete(businessService);
    }
    
    public ServiceResponse getServiceById(Long id) {
        BusinessService businessService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        return convertToResponse(businessService);
    }
    
    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private ServiceResponse convertToResponse(BusinessService businessService) {
        ServiceResponse response = new ServiceResponse();
        response.setId(businessService.getId());
        response.setNameEn(businessService.getNameEn());
        response.setNameFr(businessService.getNameFr());
        response.setDescriptionEn(businessService.getDescriptionEn());
        response.setDescriptionFr(businessService.getDescriptionFr());
        response.setIcon(businessService.getIcon());
        response.setDisplayOrder(businessService.getDisplayOrder());
        return response;
    }
}