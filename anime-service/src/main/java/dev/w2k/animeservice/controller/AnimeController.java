package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.domain.Anime;
import dev.w2k.animeservice.mapper.AnimeMapper;
import dev.w2k.animeservice.request.AnimePostRequest;
import dev.w2k.animeservice.request.AnimePutRequest;
import dev.w2k.animeservice.response.AnimeGetResponse;
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
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {

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
    var animes = Anime.getAnimes();
    var mappedAnimes = MAPPER.toAnimeGetResponseList(animes);

    if (name == null) {
      return ResponseEntity.ok(mappedAnimes);
    }

    var response = mappedAnimes.stream()
        .filter(a -> a.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();

    return ResponseEntity.ok(response);
  }

  @GetMapping("{id}")
  public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
    log.info("Searching for anime with id: {}", id);
    var animeGetResponse = Anime.getAnimes().stream()
        .filter(anime -> anime.getId().equals(id))
        .findFirst()
        .map(MAPPER::toAnimeGetResponse)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

    return animeGetResponse != null
        ? ResponseEntity.ok(animeGetResponse)
        : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<AnimeGetResponse> create(@RequestBody AnimePostRequest animePostRequest) {
    log.info("Creating anime: {}", animePostRequest);
    var anime = MAPPER.toAnime(animePostRequest);

    Anime.getAnimes().add(anime);

    var response = MAPPER.toAnimeGetResponse(anime);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    log.info("Deleting anime with id: {}", id);
    var anime = Anime.getAnimes().stream()
        .filter(a -> a.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

    Anime.getAnimes().remove(anime);

    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
    log.info("Updating anime with: {}", request);
    var anime = Anime.getAnimes().stream()
        .filter(a -> a.getId().equals(request.getId()))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

    var animeUpdate = MAPPER.toAnime(request);
    Anime.getAnimes().remove(anime);
    Anime.getAnimes().add(animeUpdate);

    return ResponseEntity.ok().build();
  }
}