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
        return userCollectionRepository.save(userCollection);
    }

    // A UserCollection listája egy adott UserID alapján
    public Iterable<UserCollection> getUserCollectionsByUserId(Long userId) {
        return userCollectionRepository.findByUserId(userId);
    }

    // Minden UserCollection lekérdezése
    public Iterable<UserCollection> getAllUserCollections() {
        return userCollectionRepository.findAll();
    }

    // UserCollection törlése ID alapján
    public boolean deleteSquishmallowFromCollection(Long userId, Long collectionId) {
        Optional<UserCollection> userCollection = userCollectionRepository.findByUserIdAndId(userId, collectionId);
        if (userCollection.isPresent()) {
            userCollectionRepository.delete(userCollection.get());
            return true;
        }
        return false;
    }
}
