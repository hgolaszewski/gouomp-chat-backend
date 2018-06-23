package pl.edu.wat.gouompbackend.app.message;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.message.base.BaseTestMessage;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;

public class TestFindMessageById extends BaseTestMessage {

	@Test
	public void findsMessageById() {
		/* given */
		Message persistedMessage = persistMessage();
		/* when */
		ResponseEntity<GetMessageDto> userDto = messageController.findMessageById(persistedMessage.getId());
		/* then */
		assertThat(userDto.getBody().getId()).isEqualTo(persistedMessage.getId());
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoMessageWithGivenId() {
		/* given */
		String fakeId = "fakeId";
		/* when/then */
		messageController.findMessageById(fakeId);
	}

}
