package dev.w2k.animeservice.repository;

import dev.w2k.animeservice.domain.Anime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AnimeData {

  private final List<Anime> animes = new ArrayList<>();

  {
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
