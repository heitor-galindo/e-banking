package com.ebanking.userms.repository;

import com.ebanking.userms.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface User repository. */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Find by reservation id is not null list.
   *
   * @return  the list
   */
  List<User> findByAccountIDIsNotNull();
}
