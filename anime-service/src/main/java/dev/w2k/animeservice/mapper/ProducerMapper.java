package dev.w2k.animeservice.mapper;

import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.request.ProducerPostRequest;
import dev.w2k.animeservice.request.ProducerPutRequest;
import dev.w2k.animeservice.response.ProducerGetResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProducerMapper {

  ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

  @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1, 1000))")
  Producer toProducer(ProducerPostRequest producerRequest);

  @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
  Producer toProducer(ProducerPutRequest request);

  ProducerGetResponse toProducerGetResponse(Producer producer);

  List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producers);

}
