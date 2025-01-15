package squishmallow.service;

import squishmallow.model.User;
import squishmallow.model.UserCollection;
import squishmallow.repository.UserRepository;
import squishmallow.repository.UserCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserCollectionRepository userCollectionRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserCollectionRepository userCollectionRepository) {
        this.userRepository = userRepository;
        this.userCollectionRepository = userCollectionRepository;
    }

    // Felhasználó keresése felhasználónév alapján
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Felhasználó hozzáadása
    public User addUser(User user) {
        return userRepository.save(user);  // Mentjük el a felhasználót az adatbázisba
    }

    // Felhasználó törlése és a hozzá kapcsolódó userCollection törlése
    public void deleteUser(Long userId) {
        // A kapcsolódó user_collection sorok törlése a felhasználó ID-ja alapján
        List<UserCollection> userCollections = userCollectionRepository.findByUserId(userId);
        if (!userCollections.isEmpty()) {
            userCollectionRepository.deleteAll(userCollections);  // Töröljük a kapcsolódó user_collection rekordokat
        }

        // Töröljük a felhasználót
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Minden felhasználó lekérdezése
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Felhasználó keresése ID alapján
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    /**
     * Felhasználó frissítése.
     *
     * @param currentUserId A frissítendő felhasználó aktuális ID-ja.
     * @param updatedUser Az új adatokkal rendelkező felhasználói objektum.
     * @return A frissített felhasználó objektum.
     */
    public User updateUser(Long currentUserId, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(currentUserId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Frissítjük az adatokat
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());

            // Mentés az adatbázisba
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with ID " + currentUserId + " not found.");
        }
    }
}
