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
    // Ellenőrzi, hogy a Squishmallow szerepel-e a felhasználói gyűjteményben
    public boolean isSquishmallowInUserCollection(Long squishmallowId) {
        try {
            // A repository helyes hívása
            System.out.println("Benne van-e: "+userCollectionRepository.existsBySquishmallowId(squishmallowId));
            return userCollectionRepository.existsBySquishmallowId(squishmallowId);

        } catch (Exception e) {
            // Hibakezelés logja
            System.out.println("Hiba történt a felhasználói gyűjtemény ellenőrzésekor: " + e.getMessage());
            return false;
        }
    }
}
