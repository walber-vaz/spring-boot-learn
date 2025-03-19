package dev.w2k.animeservice.mapper;

import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.request.ProducerPostRequest;
import dev.w2k.animeservice.response.ProducerGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProducerMapper {
  ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

  @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1, 1000))")
  Producer toProducer(ProducerPostRequest producerRequest);

  ProducerGetResponse toProducerGetResponse(Producer producer);
}
