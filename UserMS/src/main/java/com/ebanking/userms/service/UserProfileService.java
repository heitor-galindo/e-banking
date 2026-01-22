package com.ebanking.userms.service;

import com.ebanking.userms.entities.UserProfile;
import com.ebanking.userms.repository.UserProfileRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
  private final UserProfileRepository repository;

  public UserProfileService(UserProfileRepository repository) {
    this.repository = repository;
  }

  public UserProfile createUser(UserProfile user) {
    return repository.save(user);
  }

  public List<UserProfile> getAllUsers() {
    return repository.findAll();
  }

  public Optional<UserProfile> getUserById(String id) {
    return repository.findById(id);
  }

  public void deleteUser(String id) {
    repository.deleteById(id);
  }
}
