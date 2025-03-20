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
public class Anime {

  @EqualsAndHashCode.Include
  private Long id;
  private String name;
  private LocalDateTime createdAt;
}
