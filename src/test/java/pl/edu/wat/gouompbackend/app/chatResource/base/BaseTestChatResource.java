package pl.edu.wat.gouompbackend.app.chatResource.base;

import static lombok.AccessLevel.PROTECTED;
import static pl.edu.wat.gouompbackend.app.util.exception.message.ExceptionMessage.NO_SUCH_RESOURCE_ID_MESSAGE;

import java.util.List;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.wat.gouompbackend.app.chatResource.controller.interfaces.ChatResourceController;
import pl.edu.wat.gouompbackend.app.chatResource.dao.interfaces.ChatResourceDao;
import pl.edu.wat.gouompbackend.app.chatResource.model.ChatResource;
import pl.edu.wat.gouompbackend.app.util.builder.ChatResourceBuilder;
import pl.edu.wat.gouompbackend.app.util.test.BaseMongoEmbeddedTest;

@FieldDefaults(level = PROTECTED)
public abstract class BaseTestChatResource extends BaseMongoEmbeddedTest {

	@Autowired
	ChatResourceController chatResourceController;

	protected ChatResource getChatResourceById(String id) throws Exception {
		return ((ChatResourceDao) getBean(ChatResourceDao.class))
				.findById(id)
				.orElseThrow(() -> new Exception(NO_SUCH_RESOURCE_ID_MESSAGE));
	}

	protected ChatResource persistChatResource() {
		ChatResource chatResourcesToPersist = new ChatResourceBuilder().build();
		return ((ChatResourceDao) getBean(ChatResourceDao.class)).save(chatResourcesToPersist);
	}

	protected List<ChatResource> getAllChatResources() {
		return ((ChatResourceDao) getBean(ChatResourceDao.class)).findAll();
	}

	protected Iterable<ChatResource> saveChatResources(List<ChatResource> massagesToPersist) {
		return ((ChatResourceDao) getBean(ChatResourceDao.class)).saveAll(massagesToPersist);
	}
	
}
