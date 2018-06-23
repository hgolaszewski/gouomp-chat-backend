package pl.edu.wat.gouompbackend.app.chatResource.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter @Setter
@FieldDefaults(level = PRIVATE)
public class GetChatResourceDto {

	String id;
	String name;
	String awsLocation;
	String channelId;

}
