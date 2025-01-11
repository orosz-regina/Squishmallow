package squishmallow.controller;

import squishmallow.model.User;
import squishmallow.service.UserService;
import squishmallow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //felhasználó hozzáadása
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            // Ellenőrizzük, hogy létezik-e már a felhasználó email címe
            Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
            if (existingUserByEmail.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A felhasználó már létezik ezzel az email címmel: " + user.getEmail());
            }

            // Ellenőrizzük, hogy létezik-e már a felhasználó felhasználóneve
            Optional<User> existingUserByUsername = userRepository.findByUsername(user.getUsername());
            if (existingUserByUsername.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A felhasználónév már létezik: " + user.getUsername());
            }

            // Mentjük el a felhasználót az adatbázisba
            User savedUser = userService.addUser(user);

            // Visszaadjuk a mentett felhasználót
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Hiba történt a felhasználó hozzáadása közben: " + e.getMessage());
        }
    }



    // Felhasználó keresése username alapján
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Minden felhasználó lekérdezése
    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Felhasználó törlése ID alapján
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
}
