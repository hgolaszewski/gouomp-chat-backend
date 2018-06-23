package pl.edu.wat.gouompbackend.app.chatResource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import pl.edu.wat.gouompbackend.app.chatResource.base.BaseTestChatResource;
import pl.edu.wat.gouompbackend.app.chatResource.model.ChatResource;

public class TestDeleteChatResourceById extends BaseTestChatResource {

	@Test
	public void deletesChatResourceById() {
		/* given */
		ChatResource persistedChatResource = persistChatResource();
		/* when */
		chatResourceController.deleteChatResourceById(persistedChatResource.getId());
		/* then */
		assertThat(getAllChatResources()).hasSize(0);
	}

	@Test(expected = Exception.class)
	public void failsWhenNoChatResourceWithGivenId() {
		/* given */
		String fakeId = "fakeId";
		/* when */
		chatResourceController.deleteChatResourceById(fakeId);
	}

}
