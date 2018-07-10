package com.danielkocsis.database.action.monitor.dto.factory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.danielkocsis.database.action.monitor.dto.BroadcastMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * A really simple test class for testing something doesn't need any mocking.
 */
@RunWith(SpringRunner.class)
public class BroadcastMessageFactoryTest {

  @Test
  public void testHappyPath() {

    // when

    BroadcastMessage broadcastMessage = BroadcastMessageFactory.getBroadcastMessage(1, 1);

    // then

    assertThat(broadcastMessage, notNullValue());

    String payload = broadcastMessage.getPayload();

    assertThat(payload, notNullValue());
    assertThat(payload, equalTo("Timestamp = 1 :: A row with ID 1 was inserted"));
  }
}
