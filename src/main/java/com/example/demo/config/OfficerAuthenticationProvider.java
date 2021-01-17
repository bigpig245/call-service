package com.example.demo.config;

import com.example.demo.dto.OfficerDto;
import com.example.demo.service.OfficerService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class OfficerAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private OfficerService officerService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    OfficerDto officerDto = officerService.findByAccessToken(authentication.getPrincipal().toString());
    if(officerDto == null) {
      throw new AuthenticationCredentialsNotFoundException("Not Authorized");
    }

    MDC.put("dispatcher", String.valueOf(officerDto.getOfficerNo()));
    MDC.put("agency", String.valueOf(officerDto.getAgencyId()));
    return new UsernamePasswordAuthenticationToken(officerDto, null, Collections.emptyList());
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}