package dev.w2k.animeservice.repository;

import dev.w2k.animeservice.domain.Anime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AnimeHardCodeRepository {

  private static final List<Anime> ANIMES = new ArrayList<>();

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

    ANIMES.addAll(List.of(naruto, dragonBall, onePiece));
  }

  public List<Anime> findAll() {
    return ANIMES;
  }

  public Optional<Anime> findById(Long id) {
    return ANIMES
        .stream()
        .filter(a -> a.getId().equals(id))
        .findFirst();
  }

  public List<Anime> findByName(String name) {
    return ANIMES
        .stream()
        .filter(a -> a.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();
  }

  public Anime save(Anime anime) {
    ANIMES.add(anime);

    return anime;
  }

  public void delete(Anime anime) {
    ANIMES.remove(anime);
  }

  public void update(Anime anime) {
    delete(anime);
    save(anime);
  }
}
