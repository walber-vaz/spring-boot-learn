package dev.w2k.animeservice.config;

import external.dependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

  @Bean
  public Connection connection() {
    return new Connection("localhost", "w2k", "password");
  }
}
