package dev.w2k.animeservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {
  @Value("${database.url}")
  private String url;
  @Value("${database.username}")
  private String username;
  @Value("${database.password}")
  private String password;

  @Bean
  public Connection connection() {
    return new Connection(url, username, password);
  }
}
