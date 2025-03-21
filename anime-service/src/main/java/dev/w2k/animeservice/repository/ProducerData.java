package dev.w2k.animeservice.repository;

import dev.w2k.animeservice.domain.Producer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ProducerData {
  private final List<Producer> producers = new ArrayList<>();

  {
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

    producers.addAll(List.of(madhouse, sunrise, toeiAnimation));
  }

}
