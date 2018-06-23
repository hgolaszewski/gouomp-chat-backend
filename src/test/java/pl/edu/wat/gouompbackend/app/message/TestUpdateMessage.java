package pl.edu.wat.gouompbackend.app.message;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import pl.edu.wat.gouompbackend.app.message.base.BaseTestMessage;
import pl.edu.wat.gouompbackend.app.message.dto.UpdateMessageDto;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;

public class TestUpdateMessage extends BaseTestMessage {

	@Test
	public void updatesMessage() throws Exception {
		/* given */
		Message persistedMessage = persistMessage();
		UpdateMessageDto updateMessageDto = prepareUpdateMessageDto(persistedMessage.getId());
		/* when */
		messageController.updateMessage(updateMessageDto);
		/* then */
		Message updatedMessage = getMessageById(persistedMessage.getId());
		assertThat(getAllMessages()).hasSize(1);
		assertThat(updatedMessage.getBody()).isEqualTo("newBody");
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoMessageWithGivenId() {
		/* given */
		String fakeId = "fakeId";
		UpdateMessageDto updateMessageDto = prepareUpdateMessageDto(fakeId);
		/* when/then */
		messageController.updateMessage(updateMessageDto);
	}

	private UpdateMessageDto prepareUpdateMessageDto(String messageId) {
		return UpdateMessageDto.builder()
				.id(messageId)
				.body("newBody")
				.build();
	}
	
}
