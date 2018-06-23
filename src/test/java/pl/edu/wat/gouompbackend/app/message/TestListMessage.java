package pl.edu.wat.gouompbackend.app.message;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Iterables;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.message.base.BaseTestMessage;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.util.builder.MessageBuilder;

public class TestListMessage extends BaseTestMessage {

	@Test
	public void listsMessages() {
		/* given */
		Iterable<Message> persistedMessages = persistMessages();
		/* when */
		ResponseEntity<List<GetMessageDto>> listedMessages = messageController.listMessage();
		/* then */
		int persistedMessagesSize = Iterables.size(persistedMessages);
		assertThat(listedMessages.getBody()).hasSize(persistedMessagesSize);
	}

	private Iterable<Message> persistMessages() {
		Message firstMessage = new MessageBuilder().build();
		Message secondMessage = new MessageBuilder().build();
		List<Message> messagesToPersist = Arrays.asList(firstMessage, secondMessage);
		return saveMessages(messagesToPersist);
	}
	
}
