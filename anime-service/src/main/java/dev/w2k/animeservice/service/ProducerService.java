package dev.w2k.animeservice.service;


import dev.w2k.animeservice.domain.Producer;
import dev.w2k.animeservice.repository.ProducerHardCodeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProducerService {

  private final ProducerHardCodeRepository repository;

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
    assertProducerExists(producerToUpdate.getId());
    repository.update(producerToUpdate);
  }

  public void assertProducerExists(Long id) {
    this.findByIdOrThrowNotFound(id);
  }
}
