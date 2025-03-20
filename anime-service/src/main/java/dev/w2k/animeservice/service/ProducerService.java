package dev.w2k.animeservice.service;


import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.repository.ProducerHardCoderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class ProducerService {

  private final ProducerHardCoderRepository repository;

  public List<Producer> findAll(String name) {
    return name == null ? repository.findAll() : repository.findByName(name);
  }

  public Producer findByIdOrThrowNotFound(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
  }

  public Producer save(Producer producer) {
    return repository.save(producer);
  }

  public void delete(Long id) {
    var producer = this.findByIdOrThrowNotFound(id);
    repository.delete(producer);
  }

  public void update(Producer producerToUpdate) {
    var producer = this.findByIdOrThrowNotFound(producerToUpdate.getId());
    producerToUpdate.setCreatedAt(producer.getCreatedAt());
    repository.update(producer);
  }
}
