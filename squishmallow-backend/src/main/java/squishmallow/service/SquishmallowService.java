package squishmallow.service;

import squishmallow.model.Squishmallow;
import squishmallow.repository.SquishmallowRepository;
import squishmallow.repository.UserCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SquishmallowService {

    private final SquishmallowRepository squishmallowRepository;
    private final UserCollectionRepository userCollectionRepository;

    @Autowired
    public SquishmallowService(SquishmallowRepository squishmallowRepository,
                               UserCollectionRepository userCollectionRepository) {
        this.squishmallowRepository = squishmallowRepository;
        this.userCollectionRepository = userCollectionRepository;
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

    // Ellenőrzi, hogy a Squishmallow szerepel-e a felhasználói gyűjteményben
    public boolean isSquishmallowInUserCollection(Long squishmallowId) {
        try {
            return userCollectionRepository.existsBySquishmallowId(squishmallowId);
        } catch (Exception e) {
            System.out.println("Hiba történt a felhasználói gyűjtemény ellenőrzésekor: " + e.getMessage());
            return false;
        }
    }

    // Squishmallow frissítése
    public Squishmallow updateSquishmallow(Long squishmallowId, Squishmallow updatedSquishmallow) {
        Optional<Squishmallow> existingSquishmallowOptional = squishmallowRepository.findById(squishmallowId);

        if (existingSquishmallowOptional.isPresent()) {
            Squishmallow existingSquishmallow = existingSquishmallowOptional.get();

            // Frissítjük az adatokat
            existingSquishmallow.setName(updatedSquishmallow.getName());
            existingSquishmallow.setType(updatedSquishmallow.getType());
            existingSquishmallow.setCategory(updatedSquishmallow.getCategory());
            existingSquishmallow.setSize(updatedSquishmallow.getSize());

            // Mentés a frissített rekordról
            return squishmallowRepository.save(existingSquishmallow);
        } else {
            // Ha nem található a Squishmallow a megadott ID-val, hibát jelez
            throw new RuntimeException("Squishmallow with ID " + squishmallowId + " not found.");
        }
    }
}
