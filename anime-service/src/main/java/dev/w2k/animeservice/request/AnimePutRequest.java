package dev.w2k.animeservice.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnimePutRequest {
  private Long id;
  private String name;
}
