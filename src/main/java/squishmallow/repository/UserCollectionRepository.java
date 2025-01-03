package squishmallow.repository;

import squishmallow.model.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
}
