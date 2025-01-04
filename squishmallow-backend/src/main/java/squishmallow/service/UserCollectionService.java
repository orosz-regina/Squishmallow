package squishmallow.service;

import squishmallow.model.UserCollection;
import squishmallow.repository.UserCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCollectionService {

    private final UserCollectionRepository userCollectionRepository;

    @Autowired
    public UserCollectionService(UserCollectionRepository userCollectionRepository) {
        this.userCollectionRepository = userCollectionRepository;
    }

    // Új UserCollection hozzáadása
    public UserCollection addUserCollection(UserCollection userCollection) {
        return userCollectionRepository.save(userCollection);
    }

    // UserCollection törlése ID alapján
    public void deleteUserCollection(Long userCollectionId) {
        userCollectionRepository.deleteById(userCollectionId);
    }

    // Minden UserCollection lekérdezése
    public Iterable<UserCollection> getAllUserCollections() {
        return userCollectionRepository.findAll();
    }
}
