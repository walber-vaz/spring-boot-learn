package dev.w2k.animeservice.service;

import dev.w2k.animeservice.domain.Anime;
import dev.w2k.animeservice.repository.AnimeHardCodeRepository;
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
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

  @InjectMocks
  private AnimeService service;
  @Mock
  private AnimeHardCodeRepository repository;
  private List<Anime> animeList;

  @BeforeEach
  void init() {
    var cavaleirosDoZodiaco = Anime.builder().id(1L).name("Cavaleiros do Zodiaco")
        .createdAt(LocalDateTime.now()).build();
    var gintama = Anime.builder().id(2L).name("Gintama").createdAt(LocalDateTime.now()).build();
    var onePunchMan = Anime.builder().id(3L).name("One Punch").createdAt(LocalDateTime.now())
        .build();

    animeList = new ArrayList<>(List.of(cavaleirosDoZodiaco, gintama, onePunchMan));
  }

  @Test
  @DisplayName("findAll returns a list with all animes when argument is null")
  @Order(1)
  void findAll_ReturnsAllAnimes_WhenArgumentIsNull() {
    BDDMockito.when(repository.findAll()).thenReturn(animeList);

    var result = service.findAll(null);

    Assertions.assertThat(result).isNotNull().hasSameElementsAs(animeList);
  }

  @Test
  @Order(2)
  @DisplayName("findByName return a list with the anime that matches the name")
  void findByName_ReturnsAnime_WhenNameExists() {
    var anime = animeList.getFirst();
    var expectedAnimesFound = Collections.singletonList(anime);

    BDDMockito.when(repository.findByName(anime.getName())).thenReturn(expectedAnimesFound);

    var result = service.findAll(anime.getName());

    Assertions.assertThat(result).containsAll(expectedAnimesFound);
  }

  @Test
  @Order(3)
  @DisplayName("findByName returns empty list when name does not exist")
  void findByName_ReturnsEmptyList_WhenNameDoesNotExist() {
    var name = "Non-existent anime";
    BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

    var result = service.findAll(name);

    Assertions.assertThat(result).isEmpty();
  }

  @Test
  @Order(4)
  @DisplayName("findById returns a list with all animes when argument is null")
  void findById_ReturnsAllAnimes_WhenArgumentIsNull() {
    var expectedAnimesFound = animeList.getFirst();
    BDDMockito.when(repository.findById(expectedAnimesFound.getId()))
        .thenReturn(Optional.of(expectedAnimesFound));

    var result = service.findByIdThrowNotFound(expectedAnimesFound.getId());

    Assertions.assertThat(result).isEqualTo(expectedAnimesFound);
  }

  @Test
  @DisplayName("findById throws exception ResponseStatusException when id does not exist")
  @Order(5)
  void findById_ThrowsResponseStatusException_WhenIdDoesNotExist() {
    var expectedAnimesFound = animeList.getFirst();
    BDDMockito.when(repository.findById(expectedAnimesFound.getId())).thenReturn(Optional.empty());

    Assertions.assertThatException()
        .isThrownBy(() -> service.findByIdThrowNotFound(expectedAnimesFound.getId()))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  @Order(6)
  @DisplayName("save create anime when successful")
  void save_CreateAnime_WhenSuccessful() {
    var animeToSave = Anime.builder().id(99L).name("Test Anime").createdAt(LocalDateTime.now())
        .build();

    BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);

    var saveAnime = service.save(animeToSave);

    Assertions.assertThat(saveAnime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
  }

  @Test
  @Order(7)
  @DisplayName("delete remove anime when successful")
  void delete_RemoveAnime_WhenSuccessful() {
    var expectedAnimesToDelete = animeList.getFirst();
    BDDMockito.when(repository.findById(expectedAnimesToDelete.getId()))
        .thenReturn(Optional.of(expectedAnimesToDelete));
    BDDMockito.doNothing().when(repository).delete(expectedAnimesToDelete);

    Assertions.assertThatNoException()
        .isThrownBy(() -> service.delete(expectedAnimesToDelete.getId()));
  }

  @Test
  @Order(8)
  @DisplayName("delete throws exception ResponseStatusException when id does not found")
  void delete_ThrowsResponseStatusException_WhenAnimeIsDoesNotFound() {
    var animeToDelete = animeList.getFirst();
    BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.empty());

    Assertions.assertThatException().isThrownBy(() -> service.delete(animeToDelete.getId()))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  @Order(9)
  @DisplayName("update save anime when successful")
  void update_UpdateAnime_WhenSuccessful() {
    var animeToUpdate = animeList.getFirst();
    animeToUpdate.setName("Updated Name");

    BDDMockito.when(repository.findById(animeToUpdate.getId()))
        .thenReturn(Optional.of(animeToUpdate));
    BDDMockito.doNothing().when(repository).update(animeToUpdate);

    Assertions.assertThatNoException().isThrownBy(() -> service.update(animeToUpdate));
  }

  @Test
  @Order(10)
  @DisplayName("update throws exception ResponseStatusException when id does not found")
  void update_ThrowsResponseStatusException_WhenAnimeIsDoesNotFound() {
    var animeToUpdate = animeList.getFirst();

    BDDMockito.when(repository.findById(99L)).thenReturn(Optional.empty());

    Assertions.assertThatException().isThrownBy(() -> service.update(animeToUpdate))
        .isInstanceOf(RuntimeException.class);
  }
}