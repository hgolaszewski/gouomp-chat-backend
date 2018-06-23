package pl.edu.wat.gouompbackend.app.channel.dto;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.wat.gouompbackend.app.channel.enums.ChannelType;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateChannelDto implements Serializable {

	String name;
	String description;
	String username;
	ChannelType channelType;
	String password;

	public Channel convertToEntity() {
		return Channel.builder()
				.name(name)
				.description(description)
				.channelType(channelType)
				.createDate(new Date())
				.messages(new ArrayList<>())
				.build();
	}

}
