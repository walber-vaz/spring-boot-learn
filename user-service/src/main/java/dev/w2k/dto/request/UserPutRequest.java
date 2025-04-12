package dev.w2k.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserPutRequest(
    @NotNull(message = "Id is required")
    Long id,
    @NotBlank(message = "Firstname is required")
    String firstname,
    @NotBlank(message = "Lastname is required")
    String lastname,
    @NotBlank(message = "Email is required")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email is not valid")
    String email
) {

}
