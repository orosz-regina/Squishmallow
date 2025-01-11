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
}
