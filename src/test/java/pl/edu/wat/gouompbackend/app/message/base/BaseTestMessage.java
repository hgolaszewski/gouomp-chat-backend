package pl.edu.wat.gouompbackend.app.message.base;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.wat.gouompbackend.app.message.controller.interfaces.MessageController;
import pl.edu.wat.gouompbackend.app.message.dao.interfaces.MessageDao;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.util.builder.MessageBuilder;
import pl.edu.wat.gouompbackend.app.util.test.BaseMongoEmbeddedTest;

@FieldDefaults(level = PROTECTED)
public abstract class BaseTestMessage extends BaseMongoEmbeddedTest {

	@Autowired
	MessageController messageController;

	protected Message getMessageById(String id) throws Exception {
		return ((MessageDao) getBean(MessageDao.class))
				.findById(id)
				.orElseThrow(() -> new Exception("No message with given id"));
	}

	protected Message persistMessage() {
		Message messagesToPersist = new MessageBuilder().build();
		return ((MessageDao) getBean(MessageDao.class)).save(messagesToPersist);
	}

	protected List<Message> getAllMessages() {
		return ((MessageDao) getBean(MessageDao.class)).findAll();
	}

	protected Iterable<Message> saveMessages(List<Message> massagesToPersist) {
		return ((MessageDao) getBean(MessageDao.class)).saveAll(massagesToPersist);
	}
	
}
