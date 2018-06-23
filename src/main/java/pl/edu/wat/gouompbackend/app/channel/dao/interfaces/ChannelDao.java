package pl.edu.wat.gouompbackend.app.channel.dao.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;

public interface ChannelDao extends MongoRepository<Channel, String> {
	Channel findByName(String name);
}
