package dev.w2k.animeservice.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Producer {

  private Long id;
  /*
   * @JsonProperty("full_name")
   * Quando queremos que o nome do atributo seja diferente do nome do campo no JSON
   * */
  private String name;
  private LocalDateTime createdAt;

  @Getter
  private static List<Producer> producers = new ArrayList<>();

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

    producers.addAll(List.of(madhouse, sunrise, toeiAnimation));
  }
}
