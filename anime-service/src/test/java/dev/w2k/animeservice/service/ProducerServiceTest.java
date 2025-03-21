package dev.w2k.animeservice.service;

import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.repository.ProducerHardCodeRepository;
import java.util.ArrayList;
import java.util.Collections;
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

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

  @InjectMocks
  private ProducerService service;
  @Mock
  private ProducerHardCodeRepository repository;
  private List<Producer> producerList;

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

    producerList = new ArrayList<>(List.of(ufotable, bones, a1Pictures));
  }

  @Test
  @DisplayName("findAll returns a list with all producers when argument is null")
  @Order(1)
  void findAll_ReturnsAllProducers_WhenArgumentIsNull() {
    BDDMockito.when(repository.findAll()).thenReturn(producerList);

    var result = service.findAll(null);
    Assertions.assertThat(result).isNotNull().hasSameElementsAs(producerList);
  }

  @Test
  @DisplayName("findAll return a list with the producer that matches the name")
  @Order(2)
  void findByName_ReturnsProducer_WhenNameExists() {
    var producer = producerList.getFirst();
    var expectedProducersFound = Collections.singletonList(producer);

    BDDMockito.when(repository.findByName(producer.getName())).thenReturn(expectedProducersFound);

    var result = service.findAll(producer.getName());
    Assertions.assertThat(result).containsAll(expectedProducersFound);
  }

  @Test
  @DisplayName("findByName returns empty list when name does not exist")
  @Order(3)
  void findByName_ReturnsEmptyList_WhenNameDoesNotExist() {
    var name = "Non-existent producer";
    BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

    var result = service.findAll(name);
    Assertions.assertThat(result).isEmpty();
  }
}