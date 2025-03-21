package dev.w2k.animeservice.repository;

import dev.w2k.animeservice.domain.Anime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnimeHardCodeRepository {

  private final AnimeData animeData;

  public List<Anime> findAll() {
    return animeData.getAnimes();
  }

  public Optional<Anime> findById(Long id) {
    return animeData.getAnimes()
        .stream()
        .filter(a -> a.getId().equals(id))
        .findFirst();
  }

  public List<Anime> findByName(String name) {
    return animeData.getAnimes()
        .stream()
        .filter(a -> a.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();
  }

  public Anime save(Anime anime) {
    animeData.getAnimes().add(anime);

    return anime;
  }

  public void delete(Anime anime) {
    animeData.getAnimes().remove(anime);
  }

  public void update(Anime anime) {
    delete(anime);
    save(anime);
  }
}
