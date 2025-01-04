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
    public ResponseEntity<Squishmallow> createSquishmallow(@RequestBody Squishmallow squishmallow) {
        Squishmallow savedSquishmallow = squishmallowService.addSquishmallow(squishmallow);
        return new ResponseEntity<>(savedSquishmallow, HttpStatus.CREATED);
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
}
