package com.test.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventLog {
  private String id;
  private EventState state;
  private long timestamp;
  private String type;
  private String host;
}
