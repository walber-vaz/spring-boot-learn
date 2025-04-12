package dev.w2k.exceptions;

public record DefaultErrorMessage(
    String message,
    String path,
    String method,
    int status
) {

}
