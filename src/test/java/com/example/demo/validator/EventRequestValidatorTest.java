package com.example.demo.validator;

import com.example.demo.dto.InputEventRequestDto;
import com.example.demo.dto.OfficerDto;
import com.example.demo.service.OfficerService;
import com.example.demo.util.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EventRequestValidatorTest {
  @Mock
  private OfficerService officerService;

  @InjectMocks
  private EventRequestValidator eventRequestValidator;

  private static final PodamFactory PODAM = new PodamFactoryImpl();

  @Test
  public void validateImportRequest_RequiredFields() {
    InputEventRequestDto requestDto = new InputEventRequestDto();
    OfficerDto dispatcher = PODAM.manufacturePojo(OfficerDto.class);

    List<ErrorMessage> errorMessages = eventRequestValidator.validateInputRequest(requestDto, dispatcher);

    Assertions.assertThat(errorMessages)
        .hasSize(1)
        .containsExactly(
            ErrorMessage.REQUIRED_FIELD);
    Assertions.assertThat(ErrorMessage.REQUIRED_FIELD.getParams())
        .hasSize(4)
        .contains("agency_id", "event_number", "event_type_code", "event_time");
  }

  @Test
  public void validateImportRequest_InvalidEventType_NotBelongToAgency_ResponderNotFound() {
    InputEventRequestDto requestDto = PODAM.manufacturePojo(InputEventRequestDto.class);
    OfficerDto dispatcher = PODAM.manufacturePojo(OfficerDto.class);

    List<ErrorMessage> errorMessages = eventRequestValidator.validateInputRequest(requestDto, dispatcher);

    Assertions.assertThat(errorMessages)
        .hasSize(3)
        .contains(
            ErrorMessage.INVALID_EVENT_TYPE,
            ErrorMessage.DISPATCHER_DOES_NOT_BELONG_TO_AGENCY,
            ErrorMessage.RESPONDER_NOT_FOUND);
  }

  @Test
  public void validateImportRequest_Dispatcher_Responder_NotBelongToSameAgency() {
    OfficerDto dispatcher = PODAM.manufacturePojo(OfficerDto.class);
    OfficerDto responder = PODAM.manufacturePojo(OfficerDto.class);
    InputEventRequestDto requestDto = PODAM.manufacturePojo(InputEventRequestDto.class);
    requestDto.setAgencyId(dispatcher.getAgencyId());
    requestDto.setEventTypeCode("CAR");
    given(officerService.findByOfficerNo(requestDto.getResponder()))
        .willReturn(responder);

    List<ErrorMessage> errorMessages = eventRequestValidator.validateInputRequest(requestDto, dispatcher);

    Assertions.assertThat(errorMessages)
        .hasSize(1)
        .containsExactly(
            ErrorMessage.RESPONDER_AND_DISPATCHER_NOT_MATCH);
  }

  @Test
  public void validateImportRequest_OK() {
    OfficerDto dispatcher = PODAM.manufacturePojo(OfficerDto.class);
    OfficerDto responder = PODAM.manufacturePojo(OfficerDto.class);
    responder.setAgencyId(dispatcher.getAgencyId());
    InputEventRequestDto requestDto = PODAM.manufacturePojo(InputEventRequestDto.class);
    requestDto.setAgencyId(dispatcher.getAgencyId());
    requestDto.setEventTypeCode("CAR");
    given(officerService.findByOfficerNo(requestDto.getResponder()))
        .willReturn(responder);

    List<ErrorMessage> errorMessages = eventRequestValidator.validateInputRequest(requestDto, dispatcher);

    Assertions.assertThat(errorMessages).isEmpty();
  }

}
