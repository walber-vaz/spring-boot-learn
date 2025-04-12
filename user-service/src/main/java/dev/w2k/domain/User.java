package dev.w2k.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class User {

  @EqualsAndHashCode.Include
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
}