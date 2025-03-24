package dev.w2k.animeservice.controller;

import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.mapper.ProducerMapperImpl;
import dev.w2k.animeservice.repository.ProducerData;
import dev.w2k.animeservice.repository.ProducerHardCodeRepository;
import dev.w2k.animeservice.service.ProducerService;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({ProducerMapperImpl.class, ProducerData.class, ProducerHardCodeRepository.class,
    ProducerService.class})
class ProducerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private ProducerData producerData;
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
  @DisplayName("GET /producers should return 200 OK")
  @Order(1)
  void findAll_ReturnsAllProducers() throws Exception {
    BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1L));
  }
}