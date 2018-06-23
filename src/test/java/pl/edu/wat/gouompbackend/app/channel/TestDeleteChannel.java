package pl.edu.wat.gouompbackend.app.channel;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import pl.edu.wat.gouompbackend.app.channel.base.BaseTestChannel;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.user.dao.interfaces.UserDao;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.builder.UserBuilder;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;
import pl.edu.wat.gouompbackend.app.util.exception.NoPermissionException;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDeleteChannel extends BaseTestChannel {

	User firstUser;
	User secondUser;

	@Before
	public void setUp() {
		this.firstUser = new UserBuilder()
				.withUsername("username")
				.build();
		this.secondUser = new UserBuilder()
				.withUsername("username2")
				.build();
		((UserDao) getBean(UserDao.class)).save(firstUser);
		((UserDao) getBean(UserDao.class)).save(secondUser);
	}

	@Test
	public void deletesChannelById() {
		/* given */
		Channel persistedChannel = persistChannel();
		/* when */
		channelController.deleteChannelById(persistedChannel.getId(), persistedChannel.getCreatorUsername());
		/* then */
		assertThat(getAllChannels()).hasSize(0);
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoChannelWithGivenId() {
		/* given */
		String fakeId = "fakeId";
		Channel persistedChannel = persistChannel();
		/* when */
		channelController.deleteChannelById(fakeId, persistedChannel.getCreatorUsername());
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoUserWithGivenUsername() {
		/* given */
		String fakeUsername = "fakeUsername";
		Channel persistedChannel = persistChannel();
		/* when */
		channelController.deleteChannelById(persistedChannel.getId(), fakeUsername);
	}

	@Test(expected = NoPermissionException.class)
	public void failsWhenNoPermissionToDeleteChannel() {
		/* given */
		Channel persistedChannel = persistChannel();
		/* when */
		channelController.deleteChannelById(persistedChannel.getId(), secondUser.getUsername());
	}

}
