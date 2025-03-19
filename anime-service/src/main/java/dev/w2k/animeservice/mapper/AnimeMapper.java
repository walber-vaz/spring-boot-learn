package dev.w2k.animeservice.mapper;

import dev.w2k.animeservice.domain.Anime;
import dev.w2k.animeservice.request.AnimePostRequest;
import dev.w2k.animeservice.response.AnimeGetResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnimeMapper {
  AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

  @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1, 1000))")
  Anime toAnime(AnimePostRequest animePostRequest);

  AnimeGetResponse toAnimeGetResponse(Anime anime);

  List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> animes);
}
