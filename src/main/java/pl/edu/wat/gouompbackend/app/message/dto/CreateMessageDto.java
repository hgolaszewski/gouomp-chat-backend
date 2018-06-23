package pl.edu.wat.gouompbackend.app.message.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import java.io.Serializable;
import java.util.Date;
import static lombok.AccessLevel.PRIVATE;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateMessageDto implements Serializable {

	String body;
	String username;
	String channelId;

	public Message convertToEntity() {
		return Message.builder()
				.body(body)
				.createDate(new Date())
				.build();
	}

}
