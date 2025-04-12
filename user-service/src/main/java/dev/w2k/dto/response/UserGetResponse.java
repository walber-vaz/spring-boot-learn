package dev.w2k.dto.response;

public record UserGetResponse(
    Long id,
    String firstName,
    String lastName,
    String email
) {

}
