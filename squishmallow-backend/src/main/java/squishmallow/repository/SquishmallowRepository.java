package squishmallow.repository;

import squishmallow.model.Squishmallow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface SquishmallowRepository extends JpaRepository<Squishmallow, Long> {
    Optional<Squishmallow> findByNameAndTypeAndCategoryAndSize(String name, String type, String category, String size);
}
