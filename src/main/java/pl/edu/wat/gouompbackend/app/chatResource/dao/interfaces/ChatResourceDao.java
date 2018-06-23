package pl.edu.wat.gouompbackend.app.chatResource.dao.interfaces;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.gouompbackend.app.chatResource.model.ChatResource;

@Repository
public interface ChatResourceDao extends MongoRepository<ChatResource, String> {
	List<ChatResource> findAllByChannelId(String channelId);
}
