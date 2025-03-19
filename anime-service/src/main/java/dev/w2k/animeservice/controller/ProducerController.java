package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.domain.Producer;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
  /*
  *
  * @GetMapping
  * public List<String> listAll() throws InterruptedException {
  *   Simulate a slow request, so we can see the benefits of using a custom thread pool.
  *
  *   log.info(Thread.currentThread().getName());
  *   TimeUnit.SECONDS.sleep(1);
  *   return List.of("Dragon Ball", "Naruto", "One Piece");
  * }
  * */

  @GetMapping
  public List<Producer> listAll(@RequestParam(required = false, defaultValue = "") String name) {
    log.info("Searching for producer with name: {}", name);
    List<Producer> producers = Producer.getProducers();
    if (name == null) {
      return producers;
    }

    return producers.stream()
        .filter(producer -> producer.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();
  }

  @GetMapping("{id}")
  public Producer findById(@PathVariable Long id) {
    log.info("Searching for producer with id: {}", id);
    return Producer.getProducers().stream()
        .filter(producer -> producer.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  /*
  * @PostMapping
  * podemos passar headers especificos para o endpoint
  * Ex: @PostMapping(headers = "x-api-key=123")
  * Com isso só será possível acessar o endpoint se o header x-api-key for igual a 123
  * Quando colocar headers ele vai ficar obrigatorio
  * */
  @PostMapping
  public ResponseEntity<Producer> create(@RequestBody Producer producer) {
    log.info("Creating producer: {}", producer);
    producer.setId(ThreadLocalRandom.current().nextLong(1, 1000));
    Producer.getProducers().add(producer);

    return ResponseEntity.status(HttpStatus.CREATED).body(producer);
  }
}
