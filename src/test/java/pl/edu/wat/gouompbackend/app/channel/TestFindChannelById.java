package pl.edu.wat.gouompbackend.app.channel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.channel.base.BaseTestChannel;
import pl.edu.wat.gouompbackend.app.channel.dto.GetChannelDto;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;

public class TestFindChannelById extends BaseTestChannel {

	@Test
	public void findsChannelById() {
		/* given */
		Channel persistedChannel = persistChannel();
		/* when */
		ResponseEntity<GetChannelDto> userDto = channelController.findChannelById(persistedChannel.getId());
		/* then */
		assertThat(userDto.getBody().getId()).isEqualTo(persistedChannel.getId());
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoChannelWithGivenId() {
		/* given */
		String fakeId = "fakeId";
		/* when/then */
		channelController.findChannelById(fakeId);
	}

}
