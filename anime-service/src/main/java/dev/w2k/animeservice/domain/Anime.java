package dev.w2k.animeservice.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Anime {
  private Long id;
  private String name;

  public static List<Anime> getAnimes() {
    List<Anime> animes = new ArrayList<>();
    animes.add(new Anime(1L, "Naruto"));
    animes.add(new Anime(2L, "Bleach"));
    animes.add(new Anime(3L, "One Piece"));
    return animes;
  }
}
