package com.danielkocsis.database.action.monitor.dto.factory;

import com.danielkocsis.database.action.monitor.dto.BroadcastMessage;

public class BroadcastMessageFactory {

  private static final String PAYLOAD_PATTERN = "Timestamp = %d :: A row with ID %d was inserted";

  public static BroadcastMessage getBroadcastMessage(final long timestamp, final long id) {
    return new BroadcastMessage(String.format(PAYLOAD_PATTERN, timestamp, id));
  }

}
