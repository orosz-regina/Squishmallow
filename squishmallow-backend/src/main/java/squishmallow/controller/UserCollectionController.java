package squishmallow.controller;

import squishmallow.model.UserCollection;
import squishmallow.service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usercollections")
public class UserCollectionController {

    private final UserCollectionService userCollectionService;

    @Autowired
    public UserCollectionController(UserCollectionService userCollectionService) {
        this.userCollectionService = userCollectionService;
    }

    // UserCollection hozzáadása (Felhasználóhoz hozzáadunk egy Squishmallow-t)
    @PostMapping
    public ResponseEntity<UserCollection> addUserCollection(@RequestBody UserCollection userCollection) {
        UserCollection savedUserCollection = userCollectionService.addUserCollection(userCollection);
        return new ResponseEntity<>(savedUserCollection, HttpStatus.CREATED);
    }

    // Minden UserCollection lekérdezése
    @GetMapping
    public ResponseEntity<Iterable<UserCollection>> getAllUserCollections() {
        return ResponseEntity.ok(userCollectionService.getAllUserCollections());
    }

    // UserCollection törlése ID alapján
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserCollection(@PathVariable Long id) {
        userCollectionService.deleteUserCollection(id);
        return ResponseEntity.noContent().build();
    }
}
