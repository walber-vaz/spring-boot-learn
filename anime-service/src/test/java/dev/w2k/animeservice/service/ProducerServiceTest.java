package dev.w2k.animeservice.service;

import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.repository.ProducerHardCodeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

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
    var ufotable = Producer.builder().id(1L).name("Ufotable").build();
    var bones = Producer.builder().id(2L).name("Bones").build();
    var a1Pictures = Producer.builder().id(3L).name("A-1 Pictures").build();

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

  @Test
  @DisplayName("findById returns a list with all producers when argument is null")
  @Order(4)
  void findById_ReturnsAllProducers_WhenArgumentIsNull() {
    var expectedProducersFound = producerList.getFirst();
    BDDMockito.when(repository.findById(expectedProducersFound.getId()))
        .thenReturn(Optional.of(expectedProducersFound));

    var result = service.findByIdOrThrowNotFound(expectedProducersFound.getId());
    Assertions.assertThat(result).isEqualTo(expectedProducersFound);
  }

  @Test
  @DisplayName("findById throws exception ResponseStatusException when id does not exist")
  @Order(5)
  void findById_ThrowsResponseStatusException_WhenIdDoesNotExist() {
    var expectedProducersFound = producerList.getFirst();
    BDDMockito.when(repository.findById(expectedProducersFound.getId()))
        .thenReturn(Optional.empty());

    Assertions.assertThatException()
        .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProducersFound.getId()))
        .isInstanceOf(ResponseStatusException.class);
  }

  @Test
  @Order(6)
  @DisplayName("save create producer when successful")
  void save_CreateProducer_WhenSuccessful() {
    var producerToSave = Producer.builder().id(99L).name("Test Producer")
        .createdAt(LocalDateTime.now()).build();

    BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);

    var saveProducer = service.save(producerToSave);

    Assertions.assertThat(saveProducer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
  }

  @Test
  @Order(7)
  @DisplayName("delete remove producer when successful")
  void delete_RemoveProducer_WhenSuccessful() {
    var expectedProducersToDelete = producerList.getFirst();
    BDDMockito.when(repository.findById(expectedProducersToDelete.getId()))
        .thenReturn(Optional.of(expectedProducersToDelete));
    BDDMockito.doNothing().when(repository).delete(expectedProducersToDelete);

    Assertions.assertThatNoException()
        .isThrownBy(() -> service.delete(expectedProducersToDelete.getId()));
  }

  @Test
  @DisplayName("delete throws exception ResponseStatusException when id does not found")
  @Order(8)
  void delete_ThrowsResponseStatusException_WhenProducerIsDoesNotFound() {
    var producerToDelete = producerList.getFirst();
    BDDMockito.when(repository.findById(producerToDelete.getId()))
        .thenReturn(Optional.empty());

    Assertions.assertThatException()
        .isThrownBy(() -> service.delete(producerToDelete.getId()))
        .isInstanceOf(ResponseStatusException.class);
  }

  @Test
  @Order(9)
  @DisplayName("update save producer when successful")
  void update_UpdateProducer_WhenSuccessful() {
    var producerToUpdate = producerList.getFirst();
    producerToUpdate.setName("Updated Name");

    BDDMockito.when(repository.findById(producerToUpdate.getId()))
        .thenReturn(Optional.of(producerToUpdate));
    BDDMockito.doNothing().when(repository).update(producerToUpdate);

    Assertions.assertThatNoException()
        .isThrownBy(() -> service.update(producerToUpdate));
  }

  @Test
  @Order(10)
  @DisplayName("update throws exception ResponseStatusException when id does not found")
  void update_ThrowsResponseStatusException_WhenProducerIsDoesNotFound() {
    var producerToUpdate = producerList.getFirst();
    
    BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

    Assertions.assertThatException()
        .isThrownBy(() -> service.update(producerToUpdate))
        .isInstanceOf(ResponseStatusException.class);
  }
}