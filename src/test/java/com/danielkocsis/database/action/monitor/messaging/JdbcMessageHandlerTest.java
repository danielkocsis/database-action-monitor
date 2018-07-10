package com.danielkocsis.database.action.monitor.messaging;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.danielkocsis.database.action.monitor.service.WebSocketService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * An example unit test contains mocking and uses Mockito.
 */
@RunWith(MockitoJUnitRunner.class)
public class JdbcMessageHandlerTest {

  private static final long ID = 0L;
  private static final long TIMESTAMP = 1L;

  @Mock
  private WebSocketService webSocketService;

  @InjectMocks
  private JdbcMessageHandler jdbcMessageHandler;

  @Mock
  private Message<List<Map<String, Object>>> message;

  @Mock
  private MessageHeaders messageHeaders;

  @Before
  public void setup() {
    when(message.getHeaders()).thenReturn(messageHeaders);
    when(messageHeaders.getTimestamp()).thenReturn(TIMESTAMP);

    Map<String, Object> rows = new HashMap<>();
    rows.put("ID", ID);

    List<Map<String, Object>> payload = new ArrayList<>();
    payload.add(rows);

    when(message.getPayload()).thenReturn(payload);
  }

  @Test
  public void testHappyPath() {

    // when

    jdbcMessageHandler.handleJdbcMessage(message);

    // then

    verify(webSocketService).broadcastMessage(TIMESTAMP, ID);
  }

  @Test
  public void testWhenRowsAreEmpty() {

    // given

    when(message.getPayload()).thenReturn(Collections.emptyList());

    // when

    jdbcMessageHandler.handleJdbcMessage(message);

    // then

    verify(webSocketService, never()).broadcastMessage(anyLong(), anyLong());
  }

  @Test
  public void testWhenIDisMissingEmpty() {

    // given

    Map<String, Object> rows = new HashMap<>();
    rows.put("foo", ID);

    List<Map<String, Object>> payload = new ArrayList<>();
    payload.add(rows);

    when(message.getPayload()).thenReturn(payload);

    // when

    jdbcMessageHandler.handleJdbcMessage(message);

    // then

    verify(webSocketService, never()).broadcastMessage(anyLong(), anyLong());
  }

}
