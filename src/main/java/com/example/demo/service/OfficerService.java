package com.example.demo.service;

import com.example.demo.domain.Officer;
import com.example.demo.dto.OfficerDto;
import com.example.demo.exception.CallServiceException;
import com.example.demo.repository.OfficerRepository;
import com.example.demo.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class OfficerService {
  @Autowired
  private OfficerRepository officerRepository;

  public OfficerDto findByAccessToken(String accessToken) {
    Officer officer = officerRepository.findByAccessToken(accessToken);
    return ofNullable(officer)
        .map(OfficerDto::new)
        .orElse(null);
  }

  public OfficerDto findByOfficerNo(String officerNo) {
    return ofNullable(officerRepository.findByOfficerNo(officerNo))
        .map(OfficerDto::new).orElse(null);
  }
}
