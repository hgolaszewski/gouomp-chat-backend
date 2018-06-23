package pl.edu.wat.gouompbackend.app.channel;

import static pl.edu.wat.gouompbackend.app.util.security.SecurityConstants.getSHA512SecurePassword;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import pl.edu.wat.gouompbackend.app.channel.base.BaseTestChannel;
import pl.edu.wat.gouompbackend.app.channel.dao.interfaces.ChannelDao;
import pl.edu.wat.gouompbackend.app.channel.dto.LoginToPrivateChannelDto;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.util.builder.ChannelBuilder;
import pl.edu.wat.gouompbackend.app.util.exception.EmptyFieldException;
import pl.edu.wat.gouompbackend.app.util.exception.WrongPasswordException;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestLoginToPrivateChannel extends BaseTestChannel {

	Channel channel;

	@Before
	public void setUp() {
		this.channel = new ChannelBuilder()
				.withPasswordHash(getSHA512SecurePassword("password"))
				.build();
		((ChannelDao) getBean(ChannelDao.class)).save(channel);
	}

	@Test
	public void logsToPrivateChannel() {
		/* given */
		LoginToPrivateChannelDto loginDto = prepareLoginToPrivateChannelDto();
		/* when/then */
		channelController.loginToPrivateChannel(loginDto);
	}

	@Test(expected = EmptyFieldException.class)
	public void failsWhenNoPasswordSet() {
		/* given */
		LoginToPrivateChannelDto loginDto = prepareLoginToPrivateChannelDto();
		loginDto.setPassword(null);
		/* when/then */
		channelController.loginToPrivateChannel(loginDto);
	}

	@Test(expected = WrongPasswordException.class)
	public void failsWhenWrongPasswordSet() {
		/* given */
		String fakePassword = "fakePassword";
		LoginToPrivateChannelDto loginDto = prepareLoginToPrivateChannelDto();
		loginDto.setPassword(fakePassword);
		/* when/then */
		channelController.loginToPrivateChannel(loginDto);
	}

	private LoginToPrivateChannelDto prepareLoginToPrivateChannelDto() {
		return LoginToPrivateChannelDto.builder()
				.password("password")
				.channelId(channel.getId())
				.build();
	}
}
