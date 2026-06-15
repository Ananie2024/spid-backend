package com.spid.repository;

import com.spid.entity.BusinessService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<BusinessService, Long> {
    List<BusinessService> findAllByOrderByDisplayOrderAsc();
}