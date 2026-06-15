package com.spid.util;

import com.spid.entity.Admin;
import com.spid.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSetupRunner implements CommandLineRunner {
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${admin.username}")
    private String adminUsername;
    
    @Value("${admin.password}")
    private String adminPassword;
    
    @Value("${admin.email}")
    private String adminEmail;
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin if not exists
        if (!adminRepository.existsByUsername(adminUsername)) {
            Admin admin = new Admin();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEmail(adminEmail);
            admin.setRole("ROLE_ADMIN");
            adminRepository.save(admin);
            System.out.println("Default admin account created - Username: " + adminUsername + ", Password: " + adminPassword);
            System.out.println("IMPORTANT: Please change the default password after first login!");
        } else {
            System.out.println("Admin account already exists");
        }
    }
}
