package dev.w2k.animeservice.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Anime {
  private Long id;
  private String name;

  @Getter
  private static List<Anime> animes = new ArrayList<>();
  static {
    animes.add(new Anime(1L, "Naruto"));
    animes.add(new Anime(2L, "Bleach"));
    animes.add(new Anime(3L, "One Piece"));
  }
}
