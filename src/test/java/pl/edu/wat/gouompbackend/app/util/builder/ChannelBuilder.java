package pl.edu.wat.gouompbackend.app.util.builder;

import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import pl.edu.wat.gouompbackend.app.channel.enums.ChannelType;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.user.model.User;

@Wither
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ChannelBuilder {

	private static int idSequence = 0;

	String id = "someId_" + ++idSequence;
	String name = "name";
	String description = "description";
	String username = "username";
	String passwordHash = "passwordHash";
	ChannelType channelType = ChannelType.PRIVATE;
	Date createDate = new Date();
	List<Message> messages = new ArrayList<>();

	public Channel build() {
		return Channel.builder()
				.id(id)
				.name(name)
				.description(description)
				.createDate(createDate)
				.messages(messages)
				.creatorUsername(username)
				.channelType(channelType)
				.passwordHash(passwordHash)
				.build();
	}

}
