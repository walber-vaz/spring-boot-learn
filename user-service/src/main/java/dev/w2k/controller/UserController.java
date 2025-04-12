package dev.w2k.controller;

import dev.w2k.dto.request.UserPostRequest;
import dev.w2k.dto.request.UserPutRequest;
import dev.w2k.dto.response.UserGetResponse;
import dev.w2k.dto.response.UserPostResponse;
import dev.w2k.mapper.UserMapper;
import dev.w2k.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

  private final UserService service;
  private final UserMapper mapper;

  @GetMapping
  public ResponseEntity<List<UserGetResponse>> findAll(
      @RequestParam(required = false) String firstName) {
    log.debug("Request received to list all users, param first name '{}'", firstName);

    var users = service.findAll(firstName);

    var userGetResponses = mapper.toUserGetResponseList(users);

    return ResponseEntity.ok(userGetResponses);
  }

  @GetMapping("{id}")
  public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
    log.debug("Request to find user by id: {}", id);

    var user = service.findByIdOrThrowNotFound(id);

    var userGetResponse = mapper.toUserGetResponse(user);

    return ResponseEntity.ok(userGetResponse);
  }

  @PostMapping
  public ResponseEntity<UserPostResponse> save(@RequestBody @Valid UserPostRequest request) {
    log.debug("Request to save user : {}", request);

    var user = mapper.toUser(request);

    var userSaved = service.save(user);

    var userPostResponse = mapper.toUserPostResponse(userSaved);

    return ResponseEntity.status(HttpStatus.CREATED).body(userPostResponse);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    log.debug("Request to delete user by id: {}", id);

    service.delete(id);

    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> update(@RequestBody @Valid UserPutRequest request) {
    log.debug("Request to update user {}", request);

    var userToUpdate = mapper.toUser(request);

    service.update(userToUpdate);

    return ResponseEntity.noContent().build();
  }
}
