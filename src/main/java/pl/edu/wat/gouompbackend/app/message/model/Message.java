package pl.edu.wat.gouompbackend.app.message.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.user.model.User;

import java.io.Serializable;
import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@Document
@Builder
@Getter @Setter
@FieldDefaults(level = PRIVATE)
public class Message implements Serializable {

	@Id
	String id;
	String body;
	Date createDate;
	User user;
	String channelId;

	public GetMessageDto convertToDto() {
		return GetMessageDto.builder()
				.id(id)
				.body(body)
				.createDate(createDate)
				.user(user != null ? user.convertToDto() : null)
				.channelId(channelId)
				.build();
	}

}
