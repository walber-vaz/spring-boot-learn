package dev.w2k.animeservice.repository;

import dev.w2k.animeservice.domain.Producer;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
  @Order(1)
  @DisplayName("findAll returns all producers when successful")
  void findAll_ReturnsAllProducers_WhenSuccessful() {
    BDDMockito.when(producerData.getProducers()).thenReturn(producers);

    var result = repository.findAll();
    Assertions.assertThat(result).isNotNull().hasSameElementsAs(producers);
  }

  @Test
  @Order(2)
  @DisplayName("findById returns producer when successful")
  void findById_ReturnsProducer_WhenSuccessful() {
    BDDMockito.when(producerData.getProducers()).thenReturn(producers);

    var expectedProduce = producers.getFirst();
    var result = repository.findById(expectedProduce.getId());
    Assertions.assertThat(result).isPresent().contains(expectedProduce);
  }

  @Test
  @Order(3)
  @DisplayName("findByName returns empty list when producer not found")
  void findByName_ReturnsAllProducers_WhenIsEmpty() {
    BDDMockito.when(producerData.getProducers()).thenReturn(producers);

    var result = repository.findByName("not found");
    Assertions.assertThat(result).isNotNull().isEmpty();
  }

  @Test
  @Order(4)
  @DisplayName("findByName returns producer when successful")
  void findByName_ReturnsProducer_WhenSuccessful() {
    BDDMockito.when(producerData.getProducers()).thenReturn(producers);

    var expectedProduce = producers.getFirst();
    var result = repository.findByName(expectedProduce.getName());
    Assertions.assertThat(result).contains(expectedProduce);
  }
}