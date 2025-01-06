package squishmallow.controller;

import squishmallow.model.Squishmallow;
import squishmallow.service.SquishmallowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/squishmallows")
public class SquishmallowController {

    private final SquishmallowService squishmallowService;

    @Autowired
    public SquishmallowController(SquishmallowService squishmallowService) {
        this.squishmallowService = squishmallowService;
    }

    // Squishmallow hozzáadása
    @PostMapping
    public ResponseEntity<?> createSquishmallow(@RequestBody Squishmallow squishmallow) {
        try {
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

    public SquishmallowService getSquishmallowService() {
        return squishmallowService;
    }

    @GetMapping("/check-usercollection/{id}")
    public ResponseEntity<Boolean> checkIfSquishmallowInUserCollection(@PathVariable Long id) {
        boolean exists = squishmallowService.isSquishmallowInUserCollection(id);
        return ResponseEntity.ok(exists);
    }

}
