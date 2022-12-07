package com.test.service;

import com.test.models.Event;
import com.test.models.EventLog;
import com.test.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class EventService {

  private static final Logger logger = LoggerFactory.getLogger(EventService.class);
  private final HashMap<String, EventLog> eventMap;
  private final EventRepository eventRepository;

  public EventService() {
    this.eventMap = new HashMap<>();
    this.eventRepository = new EventRepository();
  }

  public void processEvent(EventLog eventLog) {
    String id = eventLog.getId();

    if (!this.eventMap.containsKey(id)) {
      this.eventMap.put(id, eventLog);
      return;
    }

    long duration = Math.abs(this.eventMap.remove(id).getTimestamp() - eventLog.getTimestamp());

    Event event = Event.builder().id(eventLog.getId()).duration(duration).type(eventLog.getType()).host(eventLog.getHost())
        .alert(duration > 4).build();

    this.eventRepository.save(event);
    logger.debug("Saved Event: {}", event);
  }
}
