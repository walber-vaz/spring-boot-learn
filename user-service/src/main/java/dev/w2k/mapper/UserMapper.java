package dev.w2k.mapper;

import dev.w2k.domain.User;
import dev.w2k.dto.request.UserPostRequest;
import dev.w2k.dto.request.UserPutRequest;
import dev.w2k.dto.response.UserGetResponse;
import dev.w2k.dto.response.UserPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

  @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
  User toUser(UserPostRequest postRequest);

  User toUser(UserPutRequest request);

  UserPostResponse toUserPostResponse(User user);

  UserGetResponse toUserGetResponse(User user);

  List<UserGetResponse> toUserGetResponseList(List<User> users);

}