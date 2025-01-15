package squishmallow.repository;

import squishmallow.model.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
    boolean existsBySquishmallowId(Long squishmallow_id);
    boolean existsByUserId(Long squishmallow_id);
    List<UserCollection> findByUserUsername(String username);
    List<UserCollection> findByUserId(Long user_id);
    void deleteByUserIdAndId(Long userId, Long collectionId);
    Optional<UserCollection> findByUserIdAndId(Long userId, Long collectionId);

}

