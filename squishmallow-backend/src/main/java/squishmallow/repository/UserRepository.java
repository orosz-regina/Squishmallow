package squishmallow.repository;

import squishmallow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {  // Itt String típusú az ID, amit te használsz
    Optional<User> findByEmail(String email);  // Email alapján keresés
    Optional<User> findByUsername(String username);  // Username alapján keresés
    void deleteByUsername(String username);  // Username alapján törlés
}
