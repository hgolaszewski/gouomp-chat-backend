package pl.edu.wat.gouompbackend.app.util.builder;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.user.model.User;

@Wither
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class MessageBuilder {

	private static int idSequence = 0;

	String id = "someId_" + ++idSequence;
	String body = "body";
	Date createDate = new Date();
	User user = null;

	public Message build() {
		return Message.builder()
				.id(id)
				.body(body)
				.createDate(createDate)
				.user(user)
				.build();
	}

}
