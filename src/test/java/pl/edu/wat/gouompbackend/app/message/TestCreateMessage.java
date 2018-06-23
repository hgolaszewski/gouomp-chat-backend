package pl.edu.wat.gouompbackend.app.message;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.channel.dao.interfaces.ChannelDao;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.message.base.BaseTestMessage;
import pl.edu.wat.gouompbackend.app.message.dto.CreateMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.user.dao.interfaces.UserDao;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.builder.ChannelBuilder;
import pl.edu.wat.gouompbackend.app.util.builder.UserBuilder;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;

@FieldDefaults(level = PRIVATE)
public class TestCreateMessage extends BaseTestMessage {

	User user;
	Channel channel;

	@Before
	public void setUp() {
		this.user = new UserBuilder().build();
		this.channel = new ChannelBuilder().build();
		((UserDao) getBean(UserDao.class)).save(user);
		((ChannelDao) getBean(ChannelDao.class)).save(channel);
	}

	@Test
	public void createsMessage() throws Exception {
		/* given */
		CreateMessageDto createMessageDto = prepareCreateMessageDto();
		/* when */
		ResponseEntity<GetMessageDto> getMessageDto = messageController.createMessage(createMessageDto);
		/* then */
		Message persistedMessage = getMessageById(getMessageDto.getBody().getId());
		assertFields(persistedMessage);
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoUserWithGivenUsername() {
		/* given */
		String fakeChannelId = "fakeChannelId";
		CreateMessageDto createMessageDto = prepareCreateMessageDto();
		createMessageDto.setChannelId(fakeChannelId);
		/* when/then */
		messageController.createMessage(createMessageDto);
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoChannelWithGivenId() {
		/* given */
		String fakeUsername = "fakeUsername";
		CreateMessageDto createMessageDto = prepareCreateMessageDto();
		createMessageDto.setUsername(fakeUsername);
		/* when/then */
		messageController.createMessage(createMessageDto);
	}

	private CreateMessageDto prepareCreateMessageDto() {
		return CreateMessageDto.builder()
				.body("someBody")
				.channelId(channel.getId())
				.username(user.getUsername())
				.build();
	}

	private void assertFields(Message persistedMessage) {
		assertThat(persistedMessage).isNotNull();
		assertThat(persistedMessage.getBody()).isEqualTo("someBody");
		assertThat(persistedMessage.getCreateDate()).isNotNull();
		assertThat(persistedMessage.getUser()).isNotNull();
		assertThat(persistedMessage.getChannelId()).isEqualTo(channel.getId());
	}

}
