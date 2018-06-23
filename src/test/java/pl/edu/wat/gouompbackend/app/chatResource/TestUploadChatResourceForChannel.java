package pl.edu.wat.gouompbackend.app.chatResource;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import pl.edu.wat.gouompbackend.app.chatResource.base.BaseTestChatResource;

@FieldDefaults(level = PRIVATE)
public class TestUploadChatResourceForChannel extends BaseTestChatResource {

	@Test(expected = Exception.class)
	public void failsWhenNoChannelWithGivenId() {
		/* given */
		String fakeChannelId = "fakeChannelId";
		/* when */
		chatResourceController.uploadChatResourceForChannel(null, fakeChannelId);
	}

}
