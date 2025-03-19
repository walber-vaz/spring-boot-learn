package dev.w2k.animeservice.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {

  /*
   * Create a controller and a method that returns a list of anime names though the following
   * endpoint: http://localhost:8080/v1/animes
   **/
  @GetMapping
  public List<String> listAll() throws InterruptedException {
    /*
     * Simulate a slow request, so we can see the benefits of using a custom thread pool.
     * */
    log.info(Thread.currentThread().getName());
    TimeUnit.SECONDS.sleep(1);
    return List.of("Dragon Ball", "Naruto", "One Piece");
  }
}
