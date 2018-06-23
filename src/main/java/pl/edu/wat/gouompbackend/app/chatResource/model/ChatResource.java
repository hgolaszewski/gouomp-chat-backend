package pl.edu.wat.gouompbackend.app.chatResource.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wat.gouompbackend.app.chatResource.dto.GetChatResourceDto;

@Document
@Builder
@Getter @Setter
@FieldDefaults(level = PRIVATE)
public class ChatResource {

	@Id
	String id;
	String channelId;
	String name;
	String awsLocation;

	public GetChatResourceDto convertToDto() {
		return GetChatResourceDto.builder()
				.id(id)
				.name(name)
				.awsLocation(awsLocation)
				.channelId(channelId)
				.build();
	}

}
