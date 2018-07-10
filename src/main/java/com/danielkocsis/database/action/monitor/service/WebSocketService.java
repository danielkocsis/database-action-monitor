package com.danielkocsis.database.action.monitor.service;

import com.danielkocsis.database.action.monitor.dto.BroadcastMessage;
import com.danielkocsis.database.action.monitor.dto.factory.BroadcastMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

  private final SimpMessagingTemplate webSocket;

  @Value("${websocket.broadcast.destination}")
  private String broadcastDestination;

  public void broadcastMessage(final long timestamp, final long id) {
    final BroadcastMessage broadcastMessage =
        BroadcastMessageFactory.getBroadcastMessage(timestamp, id);

    webSocket.convertAndSend(broadcastDestination, broadcastMessage);
  }

}
