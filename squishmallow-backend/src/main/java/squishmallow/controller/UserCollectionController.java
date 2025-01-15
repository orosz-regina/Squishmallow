package squishmallow.controller;

import squishmallow.model.User;
import squishmallow.model.Squishmallow;
import squishmallow.model.UserCollection;
import squishmallow.service.UserCollectionService;
import squishmallow.service.UserService;
import squishmallow.service.SquishmallowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/collection")
public class UserCollectionController {

    private final UserCollectionService userCollectionService;
    private final UserService userService;
    private final SquishmallowService squishmallowService;

    // Konstruktor, amelyben injektáljuk a szolgáltatásokat
    @Autowired
    public UserCollectionController(UserCollectionService userCollectionService, UserService userService, SquishmallowService squishmallowService) {
        this.userCollectionService = userCollectionService;
        this.userService = userService;
        this.squishmallowService = squishmallowService;
    }

    // UserCollection hozzáadása (Felhasználóhoz hozzáadunk egy Squishmallow-t)
    @PostMapping("/add")
    public ResponseEntity<?> addUserCollection(@RequestBody Map<String, Long> ids) {
        Long userId = ids.get("userId");
        Long squishmallowId = ids.get("squishmallowId");

        if (userId == null || squishmallowId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID or Squishmallow ID is missing.");
        }

        // User betöltése
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        // Squishmallow betöltése
        Optional<Squishmallow> optionalSquishmallow = squishmallowService.findById(squishmallowId);
        if (optionalSquishmallow.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Squishmallow not found.");
        }

        Squishmallow squishmallow = optionalSquishmallow.get();

        // UserCollection létrehozása
        UserCollection userCollection = new UserCollection();
        userCollection.setUser(user);
        userCollection.setSquishmallow(squishmallow);

        // Mentés
        UserCollection savedUserCollection = userCollectionService.addUserCollection(userCollection);
        return new ResponseEntity<>(savedUserCollection, HttpStatus.CREATED);
    }


    // Minden UserCollection lekérdezése
    @GetMapping
    public ResponseEntity<Iterable<UserCollection>> getAllUserCollections() {
        Iterable<UserCollection> userCollections = userCollectionService.getAllUserCollections();
        return ResponseEntity.ok(userCollections);
    }

    // UserCollection törlése ID alapján
    @DeleteMapping("/delete/{userId}/{collectionId}")
    public ResponseEntity<Void> deleteFromCollection(@PathVariable Long userId, @PathVariable Long collectionId) {
        boolean isDeleted = userCollectionService.deleteSquishmallowFromCollection(userId, collectionId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // User ID alapján a UserCollection lekérdezése
    @GetMapping("/{userId}")
    public ResponseEntity<Iterable<UserCollection>> getUserCollectionsByUserId(@PathVariable Long userId) {
        Iterable<UserCollection> userCollections = userCollectionService.getUserCollectionsByUserId(userId);
        return ResponseEntity.ok(userCollections); // A felhasználó összes gyűjteménye
    }
}
