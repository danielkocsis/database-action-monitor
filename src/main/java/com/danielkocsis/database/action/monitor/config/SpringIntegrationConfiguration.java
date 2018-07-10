package com.danielkocsis.database.action.monitor.config;

import com.danielkocsis.database.action.monitor.messaging.JdbcMessageHandler;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;

/**
 * This class holds the configuration for Spring Integration responsible for polling the database
 * periodically.
 *
 * The configuration is defined with the helps of Spring Integration DSL.
 */
@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class SpringIntegrationConfiguration {

  private final DataSource dataSource;
  private final JdbcMessageHandler jdbcMessageHandler;

  @Value("${database.polling.frequency}")
  private long pollingFrequency;

  @Bean
  public DirectChannel fromDBChannel() {
    return new DirectChannel();
  }

  /**
   * This factory method created a <code>JdbcPollingChannelAdapter</code> bean configured to run a
   * SELECT statement to fetch every new record from the DatabaseEntity table and updates the
   * records state to prevent the processing of the same records multiple times.
   *
   * @return <code>MessageSource</code> bean
   */
  @Bean
  public MessageSource<Object> jdbcMessageSource() {
    JdbcPollingChannelAdapter jdbcPollingChannelAdapter = new JdbcPollingChannelAdapter(
        dataSource, "SELECT id FROM DatabaseEntity WHERE isNew = true");

    jdbcPollingChannelAdapter.setUpdateSql(
        "UPDATE DatabaseEntity SET isNew=false WHERE id in (:id)");
    jdbcPollingChannelAdapter.setUpdatePerRow(true);

    return jdbcPollingChannelAdapter;
  }

  /**
   * This factory method creates a configured <code>IntegrationFlow</code> which describes the
   * following process:
   *
   * 1, Call {@link #jdbcMessageSource()} bean to read and update all the new records from the
   * monitored table 2, Send a message with the ID's of the processed rows to the message bust 3,
   * Registers {@link com.danielkocsis.database.action.monitor.messaging.JdbcMessageHandler} to
   * process the messages
   *
   * @return <code>IntegrationFlow</code> bean
   */
  @Bean
  public IntegrationFlow integrationFlow() {
    return IntegrationFlows
        .from(
            jdbcMessageSource(),
            c -> c.poller(Pollers.fixedRate(pollingFrequency, TimeUnit.SECONDS)))
        .channel(fromDBChannel())
        .handle(jdbcMessageHandler)
        .get();
  }

}
