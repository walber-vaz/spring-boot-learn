package dev.w2k.animeservice.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {

  @EqualsAndHashCode.Include
  private Long id;
  /*
   * @JsonProperty("full_name")
   * Quando queremos que o nome do atributo seja diferente do nome do campo no JSON
   * */
  private String name;
  private LocalDateTime createdAt;
}
