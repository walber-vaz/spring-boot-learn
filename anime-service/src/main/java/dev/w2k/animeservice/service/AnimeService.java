package dev.w2k.animeservice.service;

import dev.w2k.animeservice.domain.Anime;
import dev.w2k.animeservice.repository.AnimeHardCodeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AnimeService {

  private final AnimeHardCodeRepository repository;

  public List<Anime> findAll(String name) {
    return name == null ? this.repository.findAll() : this.repository.findByName(name);
  }

  public Anime findByIdThrowNotFound(Long id) {
    return this.repository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
  }

  public Anime save(Anime anime) {
    return this.repository.save(anime);
  }

  public void delete(Long id) {
    var anime = this.findByIdThrowNotFound(id);
    this.repository.delete(anime);
  }

  public void update(Anime animeToUpdate) {
    assertAnimeExists(animeToUpdate.getId());
    this.repository.update(animeToUpdate);
  }

  public void assertAnimeExists(Long id) {
    this.findByIdThrowNotFound(id);
  }
}
