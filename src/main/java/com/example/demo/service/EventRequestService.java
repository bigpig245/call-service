package com.example.demo.service;

import com.example.demo.domain.EventRequest;
import com.example.demo.domain.QEventRequest;
import com.example.demo.dto.*;
import com.example.demo.repository.EventRequestRepository;
import com.example.demo.util.DateTimeUtils;
import com.example.demo.util.ErrorMessage;
import com.example.demo.util.ExpressionBuilderUtils;
import com.example.demo.validator.EventRequestValidator;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.demo.util.Constants.DEFAULT_PAGE;
import static com.example.demo.util.Constants.MAX_PAGE;

@Service
public class EventRequestService {
  public static final Logger LOG = LoggerFactory.getLogger(EventRequestService.class);
  @Autowired
  private EventRequestRepository eventRequestRepository;
  @Autowired
  private EventRequestValidator eventRequestValidator;

  public ImportResponseDto importFile(MultipartFile file, OfficerDto officerDto) throws IOException {
    LOG.info("Import file is starting...");
    ImportResponseDto responseDto = new ImportResponseDto();
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))) {
      CSVParser csvParser = new CSVParser(fileReader,
          CSVFormat.DEFAULT
              .withFirstRecordAsHeader()
              .withIgnoreHeaderCase()
              .withTrim());

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
      int i = 0;
      for (CSVRecord csvRecord : csvRecords) {
        i++;
        InputEventRequestDto inputEventRequestDto = new InputEventRequestDto();
        inputEventRequestDto.setAgencyId(csvRecord.get("agency_id"));
        inputEventRequestDto.setEventNumber(csvRecord.get("event_number"));
        inputEventRequestDto.setEventTypeCode(csvRecord.get("event_type_code"));
        inputEventRequestDto.setEventTime(DateTimeUtils.parseDate(csvRecord.get("event_time")));
        inputEventRequestDto.setDispatchTime(DateTimeUtils.parseDate(csvRecord.get("dispatch_time")));
        inputEventRequestDto.setResponder(csvRecord.get("responder"));

        List<ErrorMessage> messages = eventRequestValidator.validateInputRequest(inputEventRequestDto, officerDto);
        if (!messages.isEmpty()) {
          LOG.error("Error while importing line {}", i);
          for (ErrorMessage message : messages) {
            responseDto.addErrorMessage(i,
                String.format(message.getMessage(), StringUtils.join(message.getParams(), ",")));
          }
          responseDto.setFailedItems(responseDto.getFailedItems() + 1);
          continue;
        }

        try {
          saveEventRequest(inputEventRequestDto);
          responseDto.setSuccessItems(responseDto.getSuccessItems() + 1);
        } catch (Exception ex) {
          LOG.error("Error while importing line {}... ", i, ex);
          responseDto.addErrorMessage(i, ex.getMessage());
          responseDto.setFailedItems(responseDto.getFailedItems() + 1);
        }
      }
    }
    LOG.info("Import file completed, success items = {}, failed items = {}",
        responseDto.getSuccessItems(), responseDto.getFailedItems());
    return responseDto;
  }

  @Transactional
  public EventRequestDto saveEventRequest(InputEventRequestDto requestDto) {
    EventRequest eventRequest = new EventRequest();
    eventRequest.setId(UUID.randomUUID().toString());
    eventRequest.setAgencyId(requestDto.getAgencyId());
    eventRequest.setEventNumber(requestDto.getEventNumber());
    eventRequest.setEventType(requestDto.getEventType());
    eventRequest.setEventTime(requestDto.getEventTime());
    eventRequest.setDispatchTime(requestDto.getDispatchTime());
    eventRequest.setDispatcher(requestDto.getDispatcher());
    eventRequest.setResponder(requestDto.getResponder());
    eventRequestRepository.save(eventRequest);
    return new EventRequestDto(eventRequest);
  }

  public PageDataDto<EventRequestDto> searchEventRequests(EventRequestSearchDto searchDto) {
    Sort.Direction direction =
        Sort.Direction.fromOptionalString(searchDto.getSortBy()).orElse(Sort.Direction.DESC);
    PageRequest pageable = PageRequest.of(
        searchDto.getPage() < 0 ? DEFAULT_PAGE : searchDto.getPage() - 1,
        searchDto.getLimit() > MAX_PAGE ? MAX_PAGE : searchDto.getLimit(),
        Sort.by(direction, searchDto.getSortBy(), "id"));
    Predicate predicate = buildPredicate(searchDto);
    Page<EventRequest> eventRequestPage = eventRequestRepository.findAll(predicate, pageable);

    List<EventRequestDto> eventRequestDtos = eventRequestPage.getContent().stream().map(EventRequestDto::new)
        .collect(Collectors.toList());
    PaginationDto paginationDto = new PaginationDto(eventRequestPage.getTotalPages(),
        eventRequestPage.getTotalElements(), eventRequestPage.getNumberOfElements());
    return new PageDataDto<>(eventRequestDtos, paginationDto);
  }

  private Predicate buildPredicate(EventRequestSearchDto searchDto) {
    QEventRequest qEventRequest = QEventRequest.eventRequest;
    BooleanExpression dispatcherExpression = ExpressionBuilderUtils.eqExpression(
        qEventRequest.agencyId, searchDto.getAgencyId());
    BooleanExpression eventTimeBetweenExpression = ExpressionBuilderUtils.betweenExpression(
        qEventRequest.eventTime, searchDto.getEventDateFrom(), searchDto.getEventDateTo());
    BooleanExpression responderExpression = ExpressionBuilderUtils.eqExpression(
        qEventRequest.responder, searchDto.getResponder());
    return ExpressionUtils.allOf(dispatcherExpression,
        eventTimeBetweenExpression,
        responderExpression);
  }

}
