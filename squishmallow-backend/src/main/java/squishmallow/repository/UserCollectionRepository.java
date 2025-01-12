package squishmallow.repository;

import squishmallow.model.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
    boolean existsBySquishmallowId(Long squishmallow_id);
    List<UserCollection> findByUserUsername(String username);
    List<UserCollection> findByUserId(Long user_id);
}

