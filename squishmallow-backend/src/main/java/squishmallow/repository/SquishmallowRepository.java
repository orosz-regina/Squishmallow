package squishmallow.repository;

import squishmallow.model.Squishmallow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquishmallowRepository extends JpaRepository<Squishmallow, Long> {
}
