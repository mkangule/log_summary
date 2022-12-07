package com.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.models.EventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogParser {

  private static final Logger logger = LoggerFactory.getLogger(LogParser.class);
  static ObjectMapper mapper = new ObjectMapper();

  public static EventLog parse(String log) {
    try {
      return mapper.readValue(log, EventLog.class);
    } catch (Exception ex) {
      logger.error("Failed to parse log: {} ", log, ex);
    }
    return null;
  }
}
