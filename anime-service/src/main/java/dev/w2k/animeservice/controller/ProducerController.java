package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.mapper.ProducerMapper;
import dev.w2k.animeservice.request.ProducerPostRequest;
import dev.w2k.animeservice.request.ProducerPutRequest;
import dev.w2k.animeservice.response.ProducerGetResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {

  private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;
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
  public ResponseEntity<List<ProducerGetResponse>> listAll(@RequestParam(required = false,
      defaultValue = "") String name) {
    log.info("Searching for producer with name: {}", name);
    var producers = Producer.getProducers();
    var mapperProducers = MAPPER.toProducerGetResponseList(producers);

    if (name == null) {
      return ResponseEntity.ok(mapperProducers);
    }

    var response = mapperProducers.stream()
        .filter(a -> a.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();

    return ResponseEntity.ok(response);
  }

  @GetMapping("{id}")
  public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {
    log.info("Searching for producer with id: {}", id);
    var producer = Producer.getProducers().stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

    var response = MAPPER.toProducerGetResponse(producer);

    return ResponseEntity.ok(response);
  }

  /*
   * @PostMapping
   * podemos passar headers especificos para o endpoint
   * Ex: @PostMapping(headers = "x-api-key=123")
   * Com isso só será possível acessar o endpoint se o header x-api-key for igual a 123
   * Quando colocar headers ele vai ficar obrigatorio
   * */
  @PostMapping
  public ResponseEntity<ProducerGetResponse> create(
      @RequestBody ProducerPostRequest producerPostRequest) {
    log.info("Creating producer: {}", producerPostRequest);
    var mapperProducer = MAPPER.toProducer(producerPostRequest);
    var response = MAPPER.toProducerGetResponse(mapperProducer);

    Producer.getProducers().add(mapperProducer);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    log.info("Deleting producer with id: {}", id);
    var producer = Producer.getProducers().stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

    Producer.getProducers().remove(producer);

    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
    log.info("Updating producer with: {}", request);
    var producer = Producer.getProducers().stream()
        .filter(p -> p.getId().equals(request.getId()))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

    var mapperProducer = MAPPER.toProducer(request);
    Producer.getProducers().remove(producer);
    Producer.getProducers().add(mapperProducer);

    return ResponseEntity.ok().build();
  }
}
