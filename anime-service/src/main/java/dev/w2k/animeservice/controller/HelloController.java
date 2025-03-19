package dev.w2k.animeservice.controller;

import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/greetings")
@Slf4j
public class HelloController {

  @GetMapping
  public String hello() {
    return "Hello, World!";
  }

  @PostMapping
  public Long save(@RequestBody String name) {
    log.info("Saving name: {}", name);
    return ThreadLocalRandom.current().nextLong(1, 1000);
  }
}
