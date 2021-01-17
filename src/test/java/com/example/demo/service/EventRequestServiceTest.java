package com.example.demo.service;

import com.example.demo.domain.EventRequest;
import com.example.demo.dto.EventRequestDto;
import com.example.demo.dto.InputEventRequestDto;
import com.example.demo.repository.EventRequestRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventRequestServiceTest {
  @Mock
  private EventRequestRepository eventRequestRepository;

  @InjectMocks
  private EventRequestService eventRequestService;
  private static final PodamFactory PODAM = new PodamFactoryImpl();

  @Test
  public void shouldSaveEventRequest() {
    InputEventRequestDto requestDto = PODAM.manufacturePojo(InputEventRequestDto.class);
    EventRequestDto eventRequestDto = eventRequestService.saveEventRequest(requestDto);
    Assertions.assertThat(eventRequestDto)
        .isEqualToIgnoringGivenFields(requestDto, "id", "createdDate", "lastModifiedDate");
    ArgumentCaptor<EventRequest> argumentCaptor = ArgumentCaptor.forClass(EventRequest.class);
    verify(eventRequestRepository).save(argumentCaptor.capture());
    Assertions.assertThat(argumentCaptor.getValue()).isEqualToIgnoringGivenFields(requestDto,
        "id", "createdDate", "lastModifiedDate");
  }

}
