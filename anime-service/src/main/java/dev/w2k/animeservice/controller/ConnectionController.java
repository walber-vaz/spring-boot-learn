package dev.w2k.animeservice.controller;

import external.dependency.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/connections")
@Slf4j
@RequiredArgsConstructor
public class ConnectionController {
  private final Connection connection;

  @GetMapping
  public ResponseEntity<Connection> getConnection() {
    log.info("Fetching connection details");
    return ResponseEntity.ok(connection);
  }
}
