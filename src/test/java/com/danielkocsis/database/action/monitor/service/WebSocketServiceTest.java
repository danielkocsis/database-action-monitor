package com.danielkocsis.database.action.monitor.service;

import static org.mockito.Mockito.verify;

import com.danielkocsis.database.action.monitor.dto.BroadcastMessage;
import com.danielkocsis.database.action.monitor.dto.factory.BroadcastMessageFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class WebSocketServiceTest {

  @Mock
  private SimpMessagingTemplate simpMessagingTemplate;
  @InjectMocks
  private WebSocketService webSocketService;

  @Test
  public void testHappyPath() {

    //given

    final long timestamp = 1L;
    final long id = 1L;
    final BroadcastMessage broadcastMessage = BroadcastMessageFactory.getBroadcastMessage(
        id, timestamp);

    ReflectionTestUtils.setField(webSocketService, "broadcastDestination", "test");

    // when

    webSocketService.broadcastMessage(timestamp, id);

    //then

    verify(simpMessagingTemplate).convertAndSend("test", broadcastMessage);
  }

}
