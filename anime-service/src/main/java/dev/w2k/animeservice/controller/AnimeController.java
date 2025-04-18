package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.mapper.AnimeMapper;
import dev.w2k.animeservice.request.AnimePostRequest;
import dev.w2k.animeservice.request.AnimePutRequest;
import dev.w2k.animeservice.response.AnimeGetResponse;
import dev.w2k.animeservice.service.AnimeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("v1/animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {

  private final AnimeService service;
  private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;
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
  public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false,
      defaultValue = "") String name) {
    log.info("Searching for anime with name: {}", name);
    var animes = this.service.findAll(name);

    var response = MAPPER.toAnimeGetResponseList(animes);

    return ResponseEntity.ok(response);
  }

  @GetMapping("{id}")
  public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
    log.info("Searching for anime with id: {}", id);
    var anime = this.service.findByIdThrowNotFound(id);

    var response = MAPPER.toAnimeGetResponse(anime);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<AnimeGetResponse> create(@RequestBody AnimePostRequest animePostRequest) {
    log.info("Creating anime: {}", animePostRequest);
    var anime = MAPPER.toAnime(animePostRequest);

    this.service.save(anime);

    var response = MAPPER.toAnimeGetResponse(anime);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    log.info("Deleting anime with id: {}", id);
    var anime = this.service.findByIdThrowNotFound(id);

    this.service.delete(anime.getId());

    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
    log.info("Updating anime with: {}", request);
    var anime = MAPPER.toAnime(request);

    this.service.update(anime);

    return ResponseEntity.ok().build();
  }
}