package dev.w2k.exception;

public record DefaultErrorMessage(
    String message,
    String path,
    String method,
    int status
) {

}
