package pl.edu.wat.gouompbackend.app.channel;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Iterables;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.channel.base.BaseTestChannel;
import pl.edu.wat.gouompbackend.app.channel.dto.GetChannelDto;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.util.builder.ChannelBuilder;

public class TestListChannel extends BaseTestChannel {

	@Test
	public void listsChannels() {
		/* given */
		Iterable<Channel> persistedChannels = persistChannels();
		/* when */
		ResponseEntity<List<GetChannelDto>> listedChannels = channelController.listChannel();
		/* then */
		int persistedChannelsSize = Iterables.size(persistedChannels);
		assertThat(listedChannels.getBody()).hasSize(persistedChannelsSize);
	}

	private Iterable<Channel> persistChannels() {
		Channel firstChannel = new ChannelBuilder().build();
		Channel secondChannel = new ChannelBuilder().build();
		List<Channel> channelsToPersist = Arrays.asList(firstChannel, secondChannel);
		return saveChannels(channelsToPersist);
	}
	
}
