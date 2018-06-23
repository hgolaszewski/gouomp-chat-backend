package pl.edu.wat.gouompbackend.app.channel;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.edu.wat.gouompbackend.app.util.security.SecurityConstants.getSHA512SecurePassword;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import pl.edu.wat.gouompbackend.app.channel.base.BaseTestChannel;
import pl.edu.wat.gouompbackend.app.channel.dao.interfaces.ChannelDao;
import pl.edu.wat.gouompbackend.app.channel.dto.UpdateChannelDto;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.user.dao.interfaces.UserDao;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.builder.ChannelBuilder;
import pl.edu.wat.gouompbackend.app.util.builder.UserBuilder;
import pl.edu.wat.gouompbackend.app.util.exception.EmptyFieldException;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;
import pl.edu.wat.gouompbackend.app.util.exception.NoPermissionException;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestUpdateChannel extends BaseTestChannel {

	Channel channel;
	User firstUser;
	User secondUser;

	@Before
	public void setUp() {
		this.firstUser = new UserBuilder().build();
		this.secondUser = new UserBuilder().build();
		this.channel = new ChannelBuilder().withUsername(firstUser.getUsername()).build();
		((ChannelDao) getBean(ChannelDao.class)).save(channel);
		((UserDao) getBean(UserDao.class)).save(firstUser);
		((UserDao) getBean(UserDao.class)).save(secondUser);
	}

	@Test
	public void updatesChannel() throws Exception {
		/* given */
		UpdateChannelDto updateChannelDto = prepareUpdateChannelDto();
		/* when */
		channelController.updateChannel(updateChannelDto);
		/* then */
		Channel updatedChannel = getChannelById(channel.getId());
		assertThat(getAllChannels()).hasSize(1);
		assertFields(updatedChannel);
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoChannelWithGivenId() {
		/* given */
		String fakeId = "fakeId";
		UpdateChannelDto updateChannelDto = prepareUpdateChannelDto();
		updateChannelDto.setId(fakeId);
		/* when/then */
		channelController.updateChannel(updateChannelDto);
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoUserWithGivenUsername() {
		/* given */
		String fakeUsername = "fakeUsername";
		UpdateChannelDto updateChannelDto = prepareUpdateChannelDto();
		updateChannelDto.setExecutorUsername(fakeUsername);
		/* when/then */
		channelController.updateChannel(updateChannelDto);
	}

	@Test(expected = NoPermissionException.class)
	public void failsWhenExecutorIsNotOwner() {
		/* given */
		UpdateChannelDto updateChannelDto = prepareUpdateChannelDto();
		updateChannelDto.setExecutorUsername(secondUser.getUsername());
		/* when/then */
		channelController.updateChannel(updateChannelDto);
	}

	@Test(expected = EmptyFieldException.class)
	public void failsWhenPasswordIsEmpty() {
		/* given */
		UpdateChannelDto updateChannelDto = prepareUpdateChannelDto();
		updateChannelDto.setPassword(null);
		/* when/then */
		channelController.updateChannel(updateChannelDto);
	}

	private UpdateChannelDto prepareUpdateChannelDto() {
		return UpdateChannelDto.builder()
				.id(channel.getId())
				.name("newName")
				.description("newDescription")
				.password("newPassword")
				.executorUsername(firstUser.getUsername())
				.build();
	}

	private static void assertFields(Channel updatedChannel) {
		assertThat(updatedChannel.getName()).isEqualTo("newName");
		assertThat(updatedChannel.getDescription()).isEqualTo("newDescription");
		assertThat(updatedChannel.getPasswordHash()).isEqualTo(getSHA512SecurePassword("newPassword"));
	}

}
