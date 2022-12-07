package com.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.test.models.EventLog;
import com.test.models.EventState;
import org.junit.Test;

public class LogParserTest {

  @Test
  public void shouldParseLog() {

    String log = "{\"id\":\"scsmbstgrb\", \"state\":\"STARTED\", \"timestamp\":1491377495213}";

    EventLog eventLog = LogParser.parse(log);

    assertNotNull(eventLog);
    assertEquals("scsmbstgrb", eventLog.getId());
    assertEquals(EventState.STARTED, eventLog.getState());
    assertEquals(1491377495213L, eventLog.getTimestamp());
  }

  @Test
  public void shouldReturnNullForInvalidLog() {

    String log = "{\"id12121\":\"scsmbstgrb\", \"staterwrew\":\"STARTED\", \"timestampqwq\":1491377495213}";

    EventLog eventLog = LogParser.parse(log);

    assertNull(eventLog);
  }
}
