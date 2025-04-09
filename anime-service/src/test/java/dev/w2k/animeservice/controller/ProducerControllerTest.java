package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.repository.ProducerData;
import dev.w2k.animeservice.repository.ProducerHardCodeRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Pode usar @ComponentScan para importar todos os beans do pacote
@ComponentScan(basePackages = "dev.w2k.animeservice")
//@ActiveProfiles("test")
class ProducerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private ProducerData producerData;
  @MockitoSpyBean
  private ProducerHardCodeRepository repository;
  private List<Producer> producerList;
  @Autowired
  private ResourceLoader resourceLoader;

  @BeforeEach
  void init() {
    var datetime = "2025-03-24T16:05:43.4535607";
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
    var dateTimeLocal = LocalDateTime.parse(datetime, formatter);

    var ufotable = Producer.builder()
        .id(1L)
        .name("Ufotable")
        .createdAt(dateTimeLocal)
        .build();
    var bones = Producer.builder()
        .id(2L)
        .name("Bones")
        .createdAt(dateTimeLocal)
        .build();
    var a1Pictures = Producer.builder()
        .id(3L)
        .name("A-1 Pictures")
        .createdAt(dateTimeLocal)
        .build();

    producerList = new ArrayList<>(List.of(ufotable, bones, a1Pictures));
  }

  @Test
  @DisplayName("GET /v1/producers should return 200 OK")
  @Order(1)
  void listAll_ReturnsAllProducers() throws Exception {
    BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

    var response = readResourceFile("producer/get-producer-null-name-200.json");
    mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("GET /v1/producers?name=Ufotable should return 200 OK")
  @Order(2)
  void listAll_ReturnsAllProducersByName() throws Exception {
    BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
    var name = "Ufotable";

    var response = readResourceFile("producer/get-producer-get-name-200.json");
    mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers").param("name", name))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("GET /v1/producers?name=x returns empty list when name is not found")
  @Order(3)
  void listAll_ReturnsEmptyListWhenNameIsNotFound() throws Exception {
    BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
    var name = "x";

    var response = readResourceFile("producer/get-producer-x-200.json");
    mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers").param("name", name))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("GET /v1/producers/1 returns a list with all producers when argument is null")
  @Order(4)
  void findById_ReturnsAllProducers_WhenArgumentIsNull() throws Exception {
    BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
    var response = readResourceFile("producer/get-producer-by-id-200.json");
    var id = 1L;

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/{id}", id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("GET /v1/producers/99 findById throws exception ResponseStatusException when id does not exist")
  @Order(5)
  void findById_ThrowsResponseStatusException_WhenIdDoesNotExist() throws Exception {
    BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
    var id = 99L;

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/{id}", id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.status().reason("Producer not found"));
  }

  @Test
  @Order(6)
  @DisplayName("POST /v1/producers create a producer")
  void save_CreateProducer_WhenSuccessful() throws Exception {
    var request = readResourceFile("producer/post-request-producer-200.json");
    var response = readResourceFile("producer/post-response-producer-201.json");
    var producerToSave = Producer.builder().id(99L).name("Aniplex").createdAt(LocalDateTime.now())
        .build();

    BDDMockito.when(repository.save(ArgumentMatchers.any()))
        .thenReturn(producerToSave);

    mockMvc.perform(MockMvcRequestBuilders
            .post("/v1/producers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request)
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  private String readResourceFile(String filename) throws IOException {
    var file = resourceLoader.getResource("classpath:%s".formatted(filename)).getFile();
    return new String(Files.readAllBytes(file.toPath()));
  }
}