package pl.edu.wat.gouompbackend.app.channel;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.channel.base.BaseTestChannel;
import pl.edu.wat.gouompbackend.app.channel.dto.CreateChannelDto;
import pl.edu.wat.gouompbackend.app.channel.enums.ChannelType;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.user.dao.interfaces.UserDao;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.builder.UserBuilder;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;
import pl.edu.wat.gouompbackend.app.util.exception.EmptyFieldException;
import pl.edu.wat.gouompbackend.app.util.exception.EntityAlreadyExistsException;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestCreateChannel extends BaseTestChannel {

	User user;

	@Before
	public void setUp() {
		this.user = new UserBuilder().build();
		((UserDao) getBean(UserDao.class)).save(user);
	}

	@Test
	public void createsChannel() throws Exception {
		/* given */
		CreateChannelDto createChannelDto = prepareCreateChannelDto();
		/* when */
		ResponseEntity<SingleIdDto> singleIdDto = channelController.createChannel(createChannelDto);
		/* then */
		Channel persistedChannel = getChannelById(singleIdDto.getBody().getId());
		assertFields(persistedChannel);
	}

	@Test(expected = EntityAlreadyExistsException.class)
	public void failsWhenChannelWithGivenNameAlreadyExists() {
		/* given */
		CreateChannelDto createChannelDto = prepareCreateChannelDto();
		CreateChannelDto createChannelDtoDuplicate = prepareCreateChannelDto();
		/* when/then */
		channelController.createChannel(createChannelDto);
		channelController.createChannel(createChannelDtoDuplicate);
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoSuchUserWithGivenUsernameAsCreator() {
		/* given */
		String fakeUsername = "fakeId";
		CreateChannelDto createChannelDto = prepareCreateChannelDto();
		createChannelDto.setUsername(fakeUsername);
		/* when/then */
		channelController.createChannel(createChannelDto);
	}

	@Test(expected = EmptyFieldException.class)
	public void failsWhenNoPasswordSet() {
		/* given */
		CreateChannelDto createChannelDto = prepareCreateChannelDto();
		createChannelDto.setPassword(null);
		/* when/then */
		channelController.createChannel(createChannelDto);
	}

	private CreateChannelDto prepareCreateChannelDto() {
		return CreateChannelDto.builder()
				.name("someName")
				.description("someDescription")
				.password("password")
				.username(user.getUsername())
				.channelType(ChannelType.PRIVATE)
				.build();
	}

	private static void assertFields(Channel persistedChannel) {
		assertThat(persistedChannel).isNotNull();
		assertThat(persistedChannel.getId()).isNotNull();
		assertThat(persistedChannel.getCreateDate()).isNotNull();
		assertThat(persistedChannel.getName()).isEqualTo("someName");
		assertThat(persistedChannel.getDescription()).isEqualTo("someDescription");
		assertThat(persistedChannel.getMessages()).isEmpty();
		assertThat(persistedChannel.getCreatorUsername()).isNotNull();
		assertThat(persistedChannel.getChannelType()).isEqualTo(ChannelType.PRIVATE);
		assertThat(persistedChannel.getPasswordHash()).isNotNull();
	}

}
