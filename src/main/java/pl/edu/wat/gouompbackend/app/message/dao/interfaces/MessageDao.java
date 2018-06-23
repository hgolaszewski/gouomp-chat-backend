package pl.edu.wat.gouompbackend.app.message.dao.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wat.gouompbackend.app.message.model.Message;

public interface MessageDao extends MongoRepository<Message, String> {}
