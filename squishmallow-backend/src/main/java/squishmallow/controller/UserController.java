package squishmallow.controller;

import squishmallow.model.User;
import squishmallow.service.UserService;
import squishmallow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;



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
            Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
            if (existingUserByEmail.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A felhasználó már létezik ezzel az email címmel: " + user.getEmail());
            }

            Optional<User> existingUserByUsername = userRepository.findByUsername(user.getUsername());
            if (existingUserByUsername.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A felhasználónév már létezik: " + user.getUsername());
            }

            User savedUser = userService.addUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Hiba történt a felhasználó hozzáadása közben: " + e.getMessage());
        }
    }
    // GET végpont a felhasználó adatainak lekérésére
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {

        try {
            User user = userService.findById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with ID: " + userId);
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: " + e.getMessage());
        }
    }

    // Minden felhasználó lekérdezése
    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //Felhasználó update
    @PutMapping("/{currentUserId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long currentUserId,
            @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(currentUserId, updatedUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


}
