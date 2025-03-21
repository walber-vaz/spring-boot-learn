package dev.w2k.animeservice.repository;

import dev.w2k.animeservice.domain.Producer;
import external.dependency.Connection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ProducerHardCodeRepository {

  private final Connection connection;
  private final ProducerData producerData;

  public List<Producer> findAll() {
    log.info(connection);
    return producerData.getProducers();
  }

  public Optional<Producer> findById(Long id) {
    return producerData.getProducers()
        .stream()
        .filter(p -> p.getId().equals(id))
        .findFirst();
  }

  public List<Producer> findByName(String name) {
    return producerData.getProducers()
        .stream()
        .filter(a -> a.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();
  }

  public Producer save(Producer producer) {
    producerData.getProducers().add(producer);

    return producer;
  }

  public void delete(Producer producer) {
    producerData.getProducers().remove(producer);
  }

  public void update(Producer producer) {
    delete(producer);
    save(producer);
  }
}
