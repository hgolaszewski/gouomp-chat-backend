package pl.edu.wat.gouompbackend.app.util.builder;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import pl.edu.wat.gouompbackend.app.chatResource.model.ChatResource;

@Wither
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ChatResourceBuilder {

	private static int idSequence = 0;

	String id = "someId_" + ++idSequence;
	String channelId = null;
	String name = "name";
	String awsLocation = "awsLocation";

	public ChatResource build() {
		return ChatResource.builder()
				.id(id)
				.channelId(channelId)
				.name(name)
				.awsLocation(awsLocation)
				.build();
	}

}
