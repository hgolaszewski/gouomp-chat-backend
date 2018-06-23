package pl.edu.wat.gouompbackend.app.user.dao.interfaces;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wat.gouompbackend.app.user.model.User;

public interface UserDao extends MongoRepository<User, String> {
	Optional<User> findByUsername(String username);
}
