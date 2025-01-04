package squishmallow.controller;

import squishmallow.model.User;
import squishmallow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Felhasználó hozzáadása
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
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
