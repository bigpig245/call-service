package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.exception.CallServiceException;
import com.example.demo.service.EventRequestService;
import com.example.demo.util.Constants;
import com.example.demo.util.ErrorMessage;
import com.example.demo.validator.EventRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/v1/events")
public class EventRequestController {
  private static final Logger LOG = LoggerFactory.getLogger(EventRequestController.class);
  @Autowired
  private EventRequestValidator eventRequestValidator;
  @Autowired
  private EventRequestService eventRequestService;

  @PostMapping(value = "/import")
  public ResponseEntity<ImportResponseDto> uploadTrack(
      @AuthenticationPrincipal OfficerDto officerDto,
      @RequestParam("file") MultipartFile file)
      throws Exception {
    return ResponseEntity.ok(eventRequestService.importFile(file, officerDto));
  }

  @PostMapping
  public ResponseEntity<EventRequestDto> saveEventRequest(
      @AuthenticationPrincipal OfficerDto officerDto,
      @Valid @RequestBody InputEventRequestDto requestDto) {
    List<ErrorMessage> messages = eventRequestValidator.validateInputRequest(requestDto, officerDto);
    if (!messages.isEmpty()) {
      throw new CallServiceException(messages.get(0));
    }
    return ResponseEntity.ok(eventRequestService.saveEventRequest(requestDto));
  }

  @GetMapping
  public ResponseEntity<PageDataDto<EventRequestDto>> searchEventRequest(
      @AuthenticationPrincipal OfficerDto officerDto,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "limit", defaultValue = "1000") int limit,
      @RequestParam(value = "sort_by", defaultValue = "lastModifiedDate") String sortBy,
      @RequestParam(value = "sort_order", defaultValue = "desc") String order,
      @RequestParam(value = "responder", defaultValue = "desc") String responder,
      @RequestParam(value = "event_date_from", required = false)
      @DateTimeFormat(pattern = Constants.DEFAULT_DATE_FORMAT) LocalDate eventDateFrom,
      @RequestParam(value = "event_date_to", required = false)
      @DateTimeFormat(pattern = Constants.DEFAULT_DATE_FORMAT) LocalDate eventDateTo) {
    LOG.debug("Start searching...");
    EventRequestSearchDto requestDto = new EventRequestSearchDto();
    requestDto.setAgencyId(officerDto.getAgencyId());
    requestDto.setEventDateFrom(eventDateFrom);
    requestDto.setEventDateTo(eventDateTo);
    requestDto.setResponder(responder);
    requestDto.setPage(page);
    requestDto.setLimit(limit);
    requestDto.setSortOrder(order);
    requestDto.setSortBy(sortBy);
    eventRequestValidator.validateSearchEventRequestDto(requestDto, officerDto);
    return ResponseEntity.ok(eventRequestService.searchEventRequests(requestDto));
  }

}
