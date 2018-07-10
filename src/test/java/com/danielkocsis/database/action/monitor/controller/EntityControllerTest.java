package com.danielkocsis.database.action.monitor.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.danielkocsis.database.action.monitor.model.DatabaseEntity;
import com.danielkocsis.database.action.monitor.repository.DatabaseEntityRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * An example component test that contains mocked beans as well.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
public class EntityControllerTest {

  private static final String BASE_PATH = "/api/";

  @LocalServerPort
  private int port;
  private String url;

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private DatabaseEntityRepository databaseEntityRepository;

  @Before
  public void setup() {
    url = "http://localhost:" + port + BASE_PATH;
  }

  @Test
  public void insertEntityTest() {

    // given

    DatabaseEntity databaseEntity = new DatabaseEntity("Foo");

    // when

    ResponseEntity<DatabaseEntity> responseEntity =
        restTemplate.postForEntity(url, databaseEntity, DatabaseEntity.class);

    // then

    assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
    verify(databaseEntityRepository).save(databaseEntity);
  }

  @Test
  public void listEntitiesTest() {

    // given

    List<DatabaseEntity> databaseEntities = new ArrayList<>();
    databaseEntities.add(new DatabaseEntity("test"));

    when(databaseEntityRepository.findAll()).thenReturn(databaseEntities);

    // when

    ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);

    // then

    assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

    List result = responseEntity.getBody();

    assertThat(result.size(), equalTo(1));
    verify(databaseEntityRepository).findAll();
  }

}
