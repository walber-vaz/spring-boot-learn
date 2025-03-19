package dev.w2k.animeservice.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimeGetResponse {
  private String name;
  private LocalDateTime createdAt;
}
