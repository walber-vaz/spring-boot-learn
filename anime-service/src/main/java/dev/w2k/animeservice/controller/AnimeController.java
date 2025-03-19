package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.domain.Anime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {
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
  public List<Anime> listAll() {
    log.info("Get all animes");
    return Anime.getAnimes();
  }

  @GetMapping("/filter")
  public Anime findByName(@RequestParam String name) {
    if (name == null || name.isBlank()) {
      log.warn("Anime not found");
      return null;
    }

    log.info("Searching for anime: {}", name);
    return Anime.getAnimes().stream()
        .filter(anime -> anime.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);
  }

  @GetMapping("{id}")
  public Anime findById(@PathVariable Long id) {
    if (id == null) {
      log.warn("Anime not found");
      return null;
    }

    log.info("Searching for anime with id: {}", id);
    return Anime.getAnimes().stream()
        .filter(anime -> anime.getId().equals(id))
        .findFirst()
        .orElse(null);
  }
}
