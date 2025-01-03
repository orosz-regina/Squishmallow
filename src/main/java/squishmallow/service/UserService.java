package squishmallow.service;

import squishmallow.model.User;
import squishmallow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Felhasználó keresése felhasználónév alapján
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    // Felhasználó hozzáadása
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // Felhasználó törlése ID alapján
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // Minden felhasználó lekérdezése
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
