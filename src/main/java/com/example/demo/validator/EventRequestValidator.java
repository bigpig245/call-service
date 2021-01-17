package com.example.demo.validator;

import com.example.demo.dto.EventRequestSearchDto;
import com.example.demo.dto.InputEventRequestDto;
import com.example.demo.dto.OfficerDto;
import com.example.demo.enumeration.EventType;
import com.example.demo.exception.CallServiceException;
import com.example.demo.service.OfficerService;
import com.example.demo.util.ErrorMessage;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.util.ErrorMessage.DISPATCHER_DOES_NOT_BELONG_TO_AGENCY;

@Component
public class EventRequestValidator {
  @Autowired
  private OfficerService officerService;

  public List<ErrorMessage> validateInputRequest(InputEventRequestDto requestDto, OfficerDto dispatcher) {
    Set<ErrorMessage> errorMessages = new HashSet<>();
    errorMessages.add(validateRequireFields(requestDto));
    errorMessages.add(validateEventType(requestDto));
    if (!StringUtils.isEmpty(requestDto.getAgencyId())
        && !dispatcher.getAgencyId().equals(requestDto.getAgencyId())) {
      errorMessages.add(DISPATCHER_DOES_NOT_BELONG_TO_AGENCY);
    }
    errorMessages.add(validateResponder(requestDto.getResponder(), dispatcher));

    requestDto.setDispatcher(dispatcher.getOfficerNo());
    return errorMessages.stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  public void validateSearchEventRequestDto(EventRequestSearchDto requestDto, OfficerDto dispatcher) {

    ErrorMessage errorMessage = validateResponder(requestDto.getResponder(), dispatcher);
    if (errorMessage != null) {
      throw new CallServiceException(errorMessage);
    }

    // update current agency to request
    requestDto.setAgencyId(dispatcher.getAgencyId());
  }

  private ErrorMessage validateResponder(String responderNo, OfficerDto dispatcher) {
    if (!StringUtils.isEmpty(responderNo)) {
      OfficerDto responder = officerService.findByOfficerNo(responderNo);
      if (responder == null) {
        return ErrorMessage.RESPONDER_NOT_FOUND;
      }

      // Dispatcher and responder should belong to only one agency
      if (!dispatcher.getAgencyId().equals(responder.getAgencyId())) {
        return ErrorMessage.RESPONDER_AND_DISPATCHER_NOT_MATCH;
      }
    }
    return null;
  }

  private ErrorMessage validateEventType(InputEventRequestDto requestDto) {
    if (!StringUtils.isEmpty(requestDto.getEventTypeCode())) {
      EventType eventType = EventType.fromName(requestDto.getEventTypeCode());
      requestDto.setEventType(eventType);
      if (eventType == null) {
        return ErrorMessage.INVALID_EVENT_TYPE.setParams(Sets.newHashSet(requestDto.getEventTypeCode()));
      }
    }
    return null;
  }

  public ErrorMessage validateRequireFields(InputEventRequestDto requestDto) {
    Set<String> errorMessages = new HashSet<>();
    errorMessages.add(validateRequireFields(requestDto.getAgencyId(), "agency_id"));
    errorMessages.add(validateRequireFields(requestDto.getEventNumber(), "event_number"));
    errorMessages.add(validateRequireFields(requestDto.getEventTypeCode(), "event_type_code"));
    errorMessages.add(validateRequireFields(requestDto.getEventTime(), "event_time"));
    errorMessages = errorMessages
        .stream().filter(Objects::nonNull).collect(Collectors.toSet());
    if (errorMessages.isEmpty()) {
      return null;
    }
    return ErrorMessage.REQUIRED_FIELD.setParams(errorMessages);

  }

  public String validateRequireFields(Object fieldValue, String fieldName) {
    if (fieldValue == null || fieldValue.toString().trim().length() == 0) {
      return fieldName;
    }
    return null;

  }

}
