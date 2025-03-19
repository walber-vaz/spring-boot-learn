package dev.w2k.animeservice.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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
    animes.add(new Anime(ThreadLocalRandom.current().nextLong(1, 1000), "Naruto"));
    animes.add(new Anime(ThreadLocalRandom.current().nextLong(1, 1000), "Dragon Ball"));
    animes.add(new Anime(ThreadLocalRandom.current().nextLong(1, 1000), "One Piece"));
  }
}
