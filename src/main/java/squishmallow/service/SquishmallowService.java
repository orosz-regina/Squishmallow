package squishmallow.service;

import squishmallow.model.Squishmallow;
import squishmallow.repository.SquishmallowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SquishmallowService {

    private final SquishmallowRepository squishmallowRepository;

    @Autowired
    public SquishmallowService(SquishmallowRepository squishmallowRepository) {
        this.squishmallowRepository = squishmallowRepository;
    }

    // Squishmallow keresése ID alapján
    public Optional<Squishmallow> findById(Long id) {
        return squishmallowRepository.findById(id);
    }

    // Squishmallow hozzáadása
    public Squishmallow addSquishmallow(Squishmallow squishmallow) {
        return squishmallowRepository.save(squishmallow);
    }

    // Squishmallow törlése ID alapján
    public void deleteSquishmallow(Long squishmallowId) {
        squishmallowRepository.deleteById(squishmallowId);
    }

    // Minden Squishmallow lekérdezése
    public Iterable<Squishmallow> getAllSquishmallows() {
        return squishmallowRepository.findAll();
    }
}
