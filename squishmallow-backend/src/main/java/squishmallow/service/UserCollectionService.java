package squishmallow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import squishmallow.model.UserCollection;
import squishmallow.repository.UserCollectionRepository;

import java.util.List;

@Service
public class UserCollectionService {

    private final UserCollectionRepository userCollectionRepository;

    @Autowired
    public UserCollectionService(UserCollectionRepository userCollectionRepository) {
        this.userCollectionRepository = userCollectionRepository;
    }

    // Korábban létező metódusok

    public UserCollection addUserCollection(UserCollection userCollection) {
        return userCollectionRepository.save(userCollection);
    }

    public Iterable<UserCollection> getAllUserCollections() {
        return userCollectionRepository.findAll();
    }

    public void deleteUserCollection(Long id) {
        userCollectionRepository.deleteById(id);
    }

    // Új metódus: Lekérdezi a felhasználó gyűjteményeit userId alapján
    public List<UserCollection> getUserCollectionsByUserId(Long userId) {
        return userCollectionRepository.findByUserId(userId);
    }
}
