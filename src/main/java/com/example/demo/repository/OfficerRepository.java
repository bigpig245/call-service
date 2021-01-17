package com.example.demo.repository;

import com.example.demo.domain.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, String> {
  Officer findByAccessToken(String accessToken);
  Officer findByOfficerNo(String officerNo);
}
