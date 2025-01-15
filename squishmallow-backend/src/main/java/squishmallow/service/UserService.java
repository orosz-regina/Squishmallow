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
        return userRepository.save(user);
    }

    // Felhasználó törlése és a hozzá kapcsolódó userCollection törlése
    public void deleteUser(Long userId) {
        List<UserCollection> userCollections = userCollectionRepository.findByUserId(userId);
        if (!userCollections.isEmpty()) {
            userCollectionRepository.deleteAll(userCollections);
        }
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

    //felhasználó frissítés
    public User updateUser(Long currentUserId, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(currentUserId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with ID " + currentUserId + " not found.");
        }
    }
}
