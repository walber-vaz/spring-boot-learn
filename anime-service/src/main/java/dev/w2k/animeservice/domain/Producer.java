package dev.w2k.animeservice.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Producer {
  private Long id;
  /*
  * @JsonProperty("full_name")
  * Quando queremos que o nome do atributo seja diferente do nome do campo no JSON
  * */
  private String name;

  @Getter
  private static List<Producer> producers = new ArrayList<>();
  static {
    var madhouse = new Producer(ThreadLocalRandom.current().nextLong(1, 1000), "Madhouse");
    var sunrise = new Producer(ThreadLocalRandom.current().nextLong(1, 1000), "Sunrise");
    var toeiAnimation = new Producer(ThreadLocalRandom.current().nextLong(1, 1000), "Toei Animation");

    producers.addAll(List.of(madhouse, sunrise, toeiAnimation));
  }
}
