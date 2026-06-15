package com.spid.repository;

import com.spid.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Page<Inquiry> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Inquiry> findByIsReadOrderByCreatedAtDesc(Boolean isRead, Pageable pageable);
}
