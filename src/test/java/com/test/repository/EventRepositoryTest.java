package com.test.repository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.test.models.Event;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventRepositoryTest {

  EventRepository eventRepository = new EventRepository();

  @Mock
  Connection connection;
  @Mock
  PreparedStatement preparedStatement;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldSave() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(1);

    eventRepository.save(getEvent());

    verify(connection.prepareStatement(Mockito.anyString()), calls(1));
  }

  private Event getEvent() {
    Event event = new Event();
    event.setId("123");
    event.setDuration(5);
    event.setAlert(true);
    return event;
  }
}
