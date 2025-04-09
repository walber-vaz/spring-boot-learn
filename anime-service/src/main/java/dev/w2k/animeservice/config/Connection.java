package dev.w2k.animeservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class Connection {
  private String host;
  private String username;
  private String password;
}
