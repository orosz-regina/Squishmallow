package squishmallow.controller;

import squishmallow.model.Squishmallow;
import squishmallow.service.SquishmallowService;
import squishmallow.repository.SquishmallowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/squishmallows")
public class SquishmallowController {

    private final SquishmallowService squishmallowService;
    private final SquishmallowRepository squishmallowRepository;

    @Autowired
    public SquishmallowController(SquishmallowService squishmallowService, SquishmallowRepository squishmallowRepository) {
        this.squishmallowService = squishmallowService;
        this.squishmallowRepository = squishmallowRepository;
    }

    // Squishmallow hozzáadása
    @PostMapping
    public ResponseEntity<?> createSquishmallow(@RequestBody Squishmallow squishmallow) {
        try {
            Optional<Squishmallow> existingSquishmallow = squishmallowRepository.findByNameAndTypeAndCategoryAndSize(
                    squishmallow.getName(),
                    squishmallow.getType(),
                    squishmallow.getCategory(),
                    squishmallow.getSize()
            );

            if (existingSquishmallow.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A Squishmallow már létezik a táblában!");
            }

            Squishmallow savedSquishmallow = squishmallowService.addSquishmallow(squishmallow);
            return new ResponseEntity<>(savedSquishmallow, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Hiba történt a Squishmallow hozzáadása közben: " + e.getMessage());
        }
    }

    // Squishmallow keresése ID alapján
    @GetMapping("/{id}")
    public ResponseEntity<Squishmallow> getSquishmallowById(@PathVariable Long id) {
        Optional<Squishmallow> squishmallow = squishmallowService.findById(id);
        return squishmallow.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Minden Squishmallow lekérdezése
    @GetMapping
    public ResponseEntity<Iterable<Squishmallow>> getAllSquishmallows() {
        return ResponseEntity.ok(squishmallowService.getAllSquishmallows());
    }

    // Squishmallow törlése ID alapján
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSquishmallow(@PathVariable Long id) {
        squishmallowService.deleteSquishmallow(id);
        return ResponseEntity.noContent().build();
    }

    // Squishmallow frissítése ID alapján
    @PutMapping("/{id}")
    public ResponseEntity<Squishmallow> updateSquishmallow(@PathVariable Long id, @RequestBody Squishmallow squishmallow) {
        try {
            Squishmallow updatedSquishmallow = squishmallowService.updateSquishmallow(id, squishmallow);
            return ResponseEntity.ok(updatedSquishmallow);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Ha nem találjuk a frissítendő Squishmallow-t
        }
    }

    // Ellenőrizze, hogy a Squishmallow szerepel-e a felhasználói gyűjteményben
    @GetMapping("/check-usercollection/{id}")
    public ResponseEntity<Boolean> checkIfSquishmallowInUserCollection(@PathVariable Long id) {
        boolean exists = squishmallowService.isSquishmallowInUserCollection(id);
        return ResponseEntity.ok(exists);
    }
}
