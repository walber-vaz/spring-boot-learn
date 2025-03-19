package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.domain.Anime;
import dev.w2k.animeservice.mapper.AnimeMapper;
import dev.w2k.animeservice.request.AnimePostRequest;
import dev.w2k.animeservice.response.AnimeGetResponse;
import java.util.List;
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
    } else {
      var filteredAnimes = animes.stream()
          .filter(a -> a.getName().toLowerCase().contains(name.toLowerCase()))
          .toList();
      var mappedFilteredAnimes = MAPPER.toAnimeGetResponseList(filteredAnimes);

      return ResponseEntity.ok(mappedFilteredAnimes);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
    log.info("Searching for anime with id: {}", id);
    var anime = Anime.getAnimes().stream()
        .filter(a -> a.getId().equals(id))
        .findFirst()
        .orElse(null);

    if (anime == null) {
      return ResponseEntity.notFound().build();
    } else {
      var response = MAPPER.toAnimeGetResponse(anime);
      return ResponseEntity.ok(response);
    }
  }

  @PostMapping
  public ResponseEntity<AnimeGetResponse> create(@RequestBody AnimePostRequest animePostRequest) {
    log.info("Creating anime: {}", animePostRequest);
    var anime = MAPPER.toAnime(animePostRequest);
    var response = MAPPER.toAnimeGetResponse(anime);

    Anime.getAnimes().add(anime);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
