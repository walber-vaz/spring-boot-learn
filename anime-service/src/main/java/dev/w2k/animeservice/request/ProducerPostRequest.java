package dev.w2k.animeservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProducerPostRequest {

  @NotBlank(message = "Name is mandatory")
  @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
  private String name;
}
