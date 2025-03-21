package dev.w2k.animeservice.repository;

import dev.w2k.animeservice.domain.Producer;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProducerHardCodeRepositoryTest {

  @InjectMocks
  private ProducerHardCodeRepository repository;
  @Mock
  private ProducerData producerData;
  private final List<Producer> producers = new ArrayList<>();

  @BeforeEach
  void init() {
    var ufotable = Producer.builder()
        .id(1L)
        .name("Ufotable")
        .build();
    var bones = Producer.builder()
        .id(2L)
        .name("Bones")
        .build();
    var a1Pictures = Producer.builder()
        .id(3L)
        .name("A-1 Pictures")
        .build();

    producers.addAll(List.of(ufotable, bones, a1Pictures));
  }

  @Test
  @DisplayName("findAll returns all producers when successful")
  void findAll_ReturnsAllProducers_WhenSuccessful() {
    BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    var result = repository.findAll();
    Assertions.assertThat(result).isNotNull().hasSize(producers.size());
  }
}