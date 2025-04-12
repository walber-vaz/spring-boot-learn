package dev.w2k.repository;

import dev.w2k.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserHardCodedRepository {
  private final UserData userData;

  public List<User> findAll() {
    return userData.getUsers();
  }

  public Optional<User> findById(Long id) {
    return userData.getUsers().stream().filter(user -> user.getId().equals(id)).findFirst();
  }

  public List<User> findByFirstName(String firstName) {
    return userData.getUsers().stream().filter(user -> user.getFirstName().equalsIgnoreCase(firstName)).toList();
  }

  public User save(User user) {
    userData.getUsers().add(user);
    return user;
  }

  public void delete(User user) {
    userData.getUsers().remove(user);
  }

  public void update(User user) {
    delete(user);
    save(user);
  }
}