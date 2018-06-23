package pl.edu.wat.gouompbackend.app.message;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import pl.edu.wat.gouompbackend.app.message.base.BaseTestMessage;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;

public class TestDeleteMessageById extends BaseTestMessage {

	@Test
	public void deletesMessageById() {
		/* given */
		Message persistedMessage = persistMessage();
		/* when */
		messageController.deleteMessageById(persistedMessage.getId());
		/* then */
		assertThat(getAllMessages()).hasSize(0);
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoMessageWithGivenId() {
		/* given */
		String fakeId = "fakeId";
		/* when */
		messageController.deleteMessageById(fakeId);
	}

}
