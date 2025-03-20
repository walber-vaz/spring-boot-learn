package dev.w2k.animeservice.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProducerPutRequest {

  private Long id;
  private String name;
}
