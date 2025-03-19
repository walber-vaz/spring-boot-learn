package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.domain.Anime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
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
  public List<Anime> listAll(@RequestParam(required = false, defaultValue = "") String name) {
    log.info("Searching for anime with name: {}", name);
    List<Anime> animes = Anime.getAnimes();
    if (name == null) {
      return animes;
    }

    return animes.stream()
        .filter(anime -> anime.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();
  }

  @GetMapping("{id}")
  public Anime findById(@PathVariable Long id) {
    log.info("Searching for anime with id: {}", id);
    return Anime.getAnimes().stream()
        .filter(anime -> anime.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @PostMapping
  public Anime create(@RequestBody Anime anime) {
    log.info("Creating anime: {}", anime);
    anime.setId(ThreadLocalRandom.current().nextLong(1, 1000));
    Anime.getAnimes().add(anime);

    return anime;
  }
}
