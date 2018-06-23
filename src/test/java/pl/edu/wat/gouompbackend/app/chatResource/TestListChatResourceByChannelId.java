package pl.edu.wat.gouompbackend.app.chatResource;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Iterables;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.channel.dao.interfaces.ChannelDao;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.chatResource.base.BaseTestChatResource;
import pl.edu.wat.gouompbackend.app.chatResource.dto.GetChatResourceDto;
import pl.edu.wat.gouompbackend.app.chatResource.model.ChatResource;
import pl.edu.wat.gouompbackend.app.util.builder.ChannelBuilder;
import pl.edu.wat.gouompbackend.app.util.builder.ChatResourceBuilder;

@FieldDefaults(level = PRIVATE)
public class TestListChatResourceByChannelId extends BaseTestChatResource {

	Channel firstChannel;
	Channel secondChannel;

	@Before
	public void setUp() {
		firstChannel = new ChannelBuilder().build();
		secondChannel = new ChannelBuilder().build();
		((ChannelDao) getBean(ChannelDao.class)).save(firstChannel);
		((ChannelDao) getBean(ChannelDao.class)).save(secondChannel);
	}

	@Test
	public void listChatResourcesByChannelId() {
		/* given */
		Iterable<ChatResource> persistedChatResources = persistChatResources();
		/* when */
		ResponseEntity<List<GetChatResourceDto>> listedChatResources =
				chatResourceController.listChatResourceForChannelById(firstChannel.getId()
		);
		/* then */
		int persistedChatResourcesSize = Iterables.size(persistedChatResources);
		assertThat(listedChatResources.getBody()).hasSize(persistedChatResourcesSize - 1);
	}

	private Iterable<ChatResource> persistChatResources() {
		ChatResource firstChatResource = new ChatResourceBuilder()
				.withChannelId(firstChannel.getId())
				.build();
		ChatResource secondChatResource = new ChatResourceBuilder()
				.withChannelId(firstChannel.getId())
				.build();
		ChatResource thirdChatResource = new ChatResourceBuilder()
				.withChannelId(secondChatResource.getId())
				.build();
		List<ChatResource> chatResourcesToPersist = Arrays.asList(
				firstChatResource, secondChatResource, thirdChatResource
		);
		return saveChatResources(chatResourcesToPersist);
	}


}
