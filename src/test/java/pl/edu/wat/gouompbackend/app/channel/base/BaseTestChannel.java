package pl.edu.wat.gouompbackend.app.channel.base;

import static lombok.AccessLevel.PROTECTED;
import static pl.edu.wat.gouompbackend.app.util.exception.message.ExceptionMessage.NO_SUCH_CHANNEL_ID_MESSAGE;

import java.util.List;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.wat.gouompbackend.app.channel.controller.interfaces.ChannelController;
import pl.edu.wat.gouompbackend.app.channel.dao.interfaces.ChannelDao;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.util.builder.ChannelBuilder;
import pl.edu.wat.gouompbackend.app.util.test.BaseMongoEmbeddedTest;

@FieldDefaults(level = PROTECTED)
public abstract class BaseTestChannel extends BaseMongoEmbeddedTest {

	@Autowired
	ChannelController channelController;

	protected Channel getChannelById(String id) throws Exception {
		return ((ChannelDao) getBean(ChannelDao.class))
				.findById(id)
				.orElseThrow(() -> new Exception(NO_SUCH_CHANNEL_ID_MESSAGE));
	}

	protected Channel persistChannel() {
		Channel channelsToPersist = new ChannelBuilder().build();
		return ((ChannelDao) getBean(ChannelDao.class)).save(channelsToPersist);
	}

	protected List<Channel> getAllChannels() {
		return ((ChannelDao) getBean(ChannelDao.class)).findAll();
	}

	protected Iterable<Channel> saveChannels(List<Channel> channelsToPersist) {
		return ((ChannelDao) getBean(ChannelDao.class)).saveAll(channelsToPersist);
	}
	
}
