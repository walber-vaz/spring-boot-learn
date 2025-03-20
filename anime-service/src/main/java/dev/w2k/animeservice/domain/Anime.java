package dev.w2k.animeservice.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
public class Anime {

  private Long id;
  private String name;
  private LocalDateTime createdAt;

  @Getter
  private static List<Anime> animes = new ArrayList<>();

  static {
    var naruto = Anime.builder()
        .id(1L)
        .name("Naruto")
        .createdAt(LocalDateTime.now())
        .build();
    var dragonBall = Anime.builder()
        .id(2L)
        .name("Dragon Ball")
        .createdAt(LocalDateTime.now())
        .build();
    var onePiece = Anime.builder()
        .id(3L)
        .name("One Piece")
        .createdAt(LocalDateTime.now())
        .build();

    animes.addAll(List.of(naruto, dragonBall, onePiece));
  }
}
