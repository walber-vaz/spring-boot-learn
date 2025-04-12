package dev.w2k.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<DefaultErrorMessage> handleNotFoundException(
      NotFoundException exception, HttpServletRequest request) {
    log.error("Not found exception: {}", exception.getReason());

    var errorMessage = new DefaultErrorMessage(
        exception.getReason(),
        request.getRequestURI(),
        request.getMethod(),
        HttpStatus.NOT_FOUND.value()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
  }
}
