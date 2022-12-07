package com.test;


import com.test.service.EventService;
import com.test.util.LogParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class Application {
  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    if (args.length == 0) {
      logger.error("Log file missing");
      throw new IllegalArgumentException("argument not provided");
    }

    String fileName = args[0];
    EventService eventService = new EventService();

    try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
      logger.info("Started processing of file : {}", fileName);

      lines.map(LogParser::parse).filter(Objects::nonNull).forEach(eventService::processEvent);

      logger.info("Finished processing of file : {}", fileName);
    } catch (IOException ex) {
      logger.error("Error while processing of file : {}", fileName, ex);
    }
  }
}
