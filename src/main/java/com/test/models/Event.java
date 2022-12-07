package com.test.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"duration", "type", "host", "alert"})
@ToString
public class Event {
  private String id;
  private long duration;
  private String type;
  private String host;
  private boolean alert;

}
