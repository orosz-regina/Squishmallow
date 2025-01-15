package squishmallow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import squishmallow.model.User;
import squishmallow.model.Squishmallow;
import squishmallow.model.UserCollection;
import squishmallow.repository.UserCollectionRepository;

import java.util.Optional;

@Service
public class UserCollectionService {

    private final UserCollectionRepository userCollectionRepository;
    private final UserService userService;
    private final SquishmallowService squishmallowService;

    // Konstruktor, amely injektálja a szükséges szolgáltatásokat
    @Autowired
    public UserCollectionService(UserCollectionRepository userCollectionRepository, UserService userService, SquishmallowService squishmallowService) {
        this.userCollectionRepository = userCollectionRepository;
        this.userService = userService;
        this.squishmallowService = squishmallowService;
    }

    // UserCollection hozzáadása
    public UserCollection addUserCollection(UserCollection userCollection) {
        return userCollectionRepository.save(userCollection); // Mentés az adatbázisba
    }

    // A UserCollection listája egy adott UserID alapján
    public Iterable<UserCollection> getUserCollectionsByUserId(Long userId) {
        return userCollectionRepository.findByUserId(userId);  // A UserCollection-okat az User ID alapján keresve
    }

    // Minden UserCollection lekérdezése
    public Iterable<UserCollection> getAllUserCollections() {
        return userCollectionRepository.findAll();
    }

    // UserCollection törlése ID alapján
    public void deleteUserCollection(Long id) {
        userCollectionRepository.deleteById(id);
    }

    /*// Az UserCollection-okat a Squishmallow ID és User ID alapján kereshetjük, ha szükséges
    public Optional<UserCollection> findByUserAndSquishmallow(User user, Squishmallow squishmallow) {
        return userCollectionRepository.findByUserAndSquishmallow(user, squishmallow);
    }*/
}
