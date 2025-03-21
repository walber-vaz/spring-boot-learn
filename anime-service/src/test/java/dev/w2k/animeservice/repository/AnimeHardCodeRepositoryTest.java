package dev.w2k.animeservice.repository;


import dev.w2k.animeservice.domain.Anime;
import java.time.LocalDateTime;
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
class AnimeHardCodeRepositoryTest {

  @InjectMocks
  private AnimeHardCodeRepository repository;
  @Mock
  private AnimeData animeData;
  private final List<Anime> animeList = new ArrayList<>();

  @BeforeEach
  void init() {
    var cavaleirosDoZodiaco = Anime.builder()
        .id(1L)
        .name("Cavaleiros do Zodiaco")
        .createdAt(LocalDateTime.now())
        .build();
    var gintama = Anime.builder()
        .id(2L)
        .name("Gintama")
        .createdAt(LocalDateTime.now())
        .build();
    var onePunchMan = Anime.builder()
        .id(3L)
        .name("One Punch")
        .createdAt(LocalDateTime.now())
        .build();

    animeList.addAll(List.of(cavaleirosDoZodiaco, gintama, onePunchMan));
  }

  @Test
  @Order(1)
  @DisplayName("findAll returns all animes when successful")
  void findAll_ReturnsAllAnimes_WhenSuccessful() {
    BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

    var result = repository.findAll();
    Assertions.assertThat(result).isNotNull().hasSameElementsAs(animeList);
  }

  @Test
  @Order(2)
  @DisplayName("findById returns anime when successful")
  void findById_ReturnsAnime_WhenSuccessful() {
    BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

    var expectedAnime = animeList.getFirst();
    var result = repository.findById(expectedAnime.getId());
    Assertions.assertThat(result).isPresent().contains(expectedAnime);
  }

  @Test
  @Order(3)
  @DisplayName("findByName returns empty list when anime not found")
  void findByName_ReturnsAllAnimes_WhenIsEmpty() {
    BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

    var result = repository.findByName("not found");
    Assertions.assertThat(result).isNotNull().isEmpty();
  }

  @Test
  @Order(4)
  @DisplayName("findByName returns animes when successful")
  void findByName_ReturnsAnime_WhenSuccessful() {
    BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

    var expectedAnime = animeList.getFirst();
    var result = repository.findByName(expectedAnime.getName());
    Assertions.assertThat(result).contains(expectedAnime);
  }

  @Test
  @Order(5)
  @DisplayName("save create anime when successful")
  void save_CreateAnime_WhenSuccessful() {
    BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

    var animeToSave = Anime.builder()
        .id(99L)
        .name("Test Anime")
        .createdAt(LocalDateTime.now())
        .build();
    var animeSaved = repository.save(animeToSave);

    Assertions.assertThat(animeSaved).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();

    var optionalAnime = repository.findById(animeToSave.getId());

    Assertions.assertThat(optionalAnime).isPresent().contains(animeToSave);
  }

  @Test
  @Order(6)
  @DisplayName("delete remove anime when successful")
  void delete_RemoveAnime_WhenSuccessful() {
    BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

    var animeToDelete = animeList.getFirst();
    repository.delete(animeToDelete);

    var animeListToCheck = repository.findAll();

    Assertions.assertThat(animeListToCheck).isNotEmpty().doesNotContain(animeToDelete);
  }

  @Test
  @Order(7)
  @DisplayName("update save anime when successful")
  void update_UpdateAnime_WhenSuccessful() {
    BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

    var animeToUpdate = this.animeList.getFirst();
    animeToUpdate.setName("Updated Name");

    repository.update(animeToUpdate);

    Assertions.assertThat(this.animeList).contains(animeToUpdate);

    var animeCheckName = repository.findById(animeToUpdate.getId());

    Assertions.assertThat(animeCheckName).isPresent();
    Assertions.assertThat(animeCheckName.get().getName()).isEqualTo(animeToUpdate.getName());
  }
}