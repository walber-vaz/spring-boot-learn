package dev.w2k.animeservice.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/animes")
public class AnimeController {

  /*
   * Create a controller and a method that returns a list of anime names though the following
   * endpoint: http://localhost:8080/v1/animes
   **/
  @GetMapping
  public List<String> listAll() {
    return List.of("Dragon Ball", "Naruto", "One Piece");
  }
}
