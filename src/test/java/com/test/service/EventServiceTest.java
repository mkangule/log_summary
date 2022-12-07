package com.test.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.test.models.Event;
import com.test.models.EventLog;
import com.test.models.EventState;
import com.test.repository.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.FieldSetter;

import java.util.HashMap;

public class EventServiceTest {

  EventService eventService = new EventService();

  @Mock
  EventRepository eventRepository;
  String id = "abcd";

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    FieldSetter.setField(eventService, eventService.getClass().getDeclaredField("eventRepository"), eventRepository);
  }

  @Test
  public void shouldSaveEventWithAlertTrue() throws NoSuchFieldException {
    HashMap<String, EventLog> eventMap = new HashMap<>();
    eventMap.put(id, getEventLog(id, EventState.STARTED, 3));
    FieldSetter.setField(eventService, eventService.getClass().getDeclaredField("eventMap"), eventMap);

    eventService.processEvent(getEventLog(id, EventState.FINISHED, 10));

    verify(eventRepository).save(getEvent(id, 3, 10));
  }

  @Test
  public void shouldSaveEventWithAlertFalse() throws NoSuchFieldException {
    HashMap<String, EventLog> eventMap = new HashMap<>();
    eventMap.put(id, getEventLog(id, EventState.STARTED, 3));
    FieldSetter.setField(eventService, eventService.getClass().getDeclaredField("eventMap"), eventMap);

    eventService.processEvent(getEventLog(id, EventState.FINISHED, 5));

    verify(eventRepository).save(getEvent(id, 3, 5));
  }

  @Test
  public void shouldNotSaveEvent() throws NoSuchFieldException {
    FieldSetter.setField(eventService, eventService.getClass().getDeclaredField("eventMap"), new HashMap<>());

    eventService.processEvent(getEventLog(id, EventState.FINISHED, 5));

    verify(eventRepository, never()).save(getEvent(id, 3, 5));
  }

  private EventLog getEventLog(String id, EventState state, long time) {
    EventLog eventLog = new EventLog();
    eventLog.setId(id);
    eventLog.setState(state);
    eventLog.setTimestamp(time);
    return eventLog;
  }

  private Event getEvent(String id, long time1, long time2) {
    long duration = Math.abs(time1 - time2);
    Event event = new Event();
    event.setId(id);
    event.setDuration(duration);
    event.setAlert(duration > 4);
    return event;
  }

}
