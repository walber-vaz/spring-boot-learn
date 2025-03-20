package dev.w2k.animeservice.repository;

import dev.w2k.animeservice.domain.Producer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerHardCoderRepository {

  private static final List<Producer> PRODUCERS = new ArrayList<>();

  static {
    var madhouse = Producer.builder()
        .id(1L)
        .name("Madhouse")
        .createdAt(LocalDateTime.now())
        .build();
    var sunrise = Producer.builder()
        .id(2L)
        .name("Sunrise")
        .createdAt(LocalDateTime.now())
        .build();
    var toeiAnimation = Producer.builder()
        .id(3L)
        .name("Toei Animation")
        .createdAt(LocalDateTime.now())
        .build();

    PRODUCERS.addAll(List.of(madhouse, sunrise, toeiAnimation));
  }

  public List<Producer> findAll() {
    return PRODUCERS;
  }

  public Optional<Producer> findById(Long id) {
    return PRODUCERS
        .stream()
        .filter(p -> p.getId().equals(id))
        .findFirst();
  }

  public List<Producer> findByName(String name) {
    return PRODUCERS
        .stream()
        .filter(a -> a.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();
  }

  public Producer save(Producer producer) {
    PRODUCERS.add(producer);

    return producer;
  }

  public void delete(Producer producer) {
    PRODUCERS.remove(producer);
  }

  public void update(Producer producer) {
    delete(producer);
    save(producer);
  }
}
