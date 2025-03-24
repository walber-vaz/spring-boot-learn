package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.mapper.ProducerMapperImpl;
import dev.w2k.animeservice.repository.ProducerData;
import dev.w2k.animeservice.repository.ProducerHardCodeRepository;
import dev.w2k.animeservice.service.ProducerService;
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
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Pode usar @ComponentScan para importar todos os beans do pacote
@Import({ProducerMapperImpl.class, ProducerData.class, ProducerHardCodeRepository.class,
    ProducerService.class})
class ProducerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private ProducerData producerData;
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
  @DisplayName("GET /producers should return 200 OK")
  @Order(1)
  void findAll_ReturnsAllProducers() throws Exception {
    BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

    var response = readResourceFile("producer/get-producer-null-name-200.json");
    mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  private String readResourceFile(String fileName) throws IOException {
    var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
    return new String(Files.readAllBytes(file.toPath()));
  }
}