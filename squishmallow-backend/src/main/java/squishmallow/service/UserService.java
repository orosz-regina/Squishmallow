package squishmallow.service;

import squishmallow.model.User;
import squishmallow.model.UserCollection; // Importáljuk a UserCollection osztályt
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
    public void deleteUser(String username) {
        // A kapcsolódó user_collection sorok törlése
        List<UserCollection> userCollections = userCollectionRepository.findByUserUsername(username);
        if (!userCollections.isEmpty()) {
            userCollectionRepository.deleteAll(userCollections);  // Töröljük a kapcsolódó user_collection rekordokat
        }

        // Töröljük a felhasználót
        Optional<User> userOptional = userRepository.findByUsername(username);
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

    // Felhasználó frissítése
    public User updateUser(String username, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(username); // A felhasználó keresése felhasználónév alapján

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Frissítjük az adatokat
            existingUser.setEmail(updatedUser.getEmail());   // Frissítjük az email címet
            existingUser.setPassword(updatedUser.getPassword());  // Frissítjük a jelszót (ne felejtsd el titkosítani, ha szükséges)

            // Mentés a frissített rekordról
            return userRepository.save(existingUser);
        } else {
            // Ha nem található a felhasználó a megadott ID-val (username), hibát jelez
            throw new RuntimeException("User with username " + username + " not found.");
        }
    }
}