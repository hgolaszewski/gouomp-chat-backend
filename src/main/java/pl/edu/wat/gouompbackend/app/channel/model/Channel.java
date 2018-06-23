package pl.edu.wat.gouompbackend.app.channel.model;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wat.gouompbackend.app.channel.dto.GetChannelDto;
import pl.edu.wat.gouompbackend.app.channel.enums.ChannelType;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;
import pl.edu.wat.gouompbackend.app.user.model.User;

@Builder
@Document
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Channel {

	@Id
	String id;
	String name;
	String description;
	String creatorUsername;
	String passwordHash;
	Date createDate;
	ChannelType channelType;
	@DBRef
	List<Message> messages;

	public GetChannelDto convertToDto() {
		List<GetMessageDto> messages = this.messages != null ? this.messages.stream()
				.map(Message::convertToDto)
				.collect(Collectors.toList()) : Collections.emptyList();
		return GetChannelDto.builder()
				.id(id)
				.name(name)
				.description(description)
				.createDate(createDate)
				.username(creatorUsername)
				.channelType(channelType)
				.messages(messages)
				.build();
	}

}
