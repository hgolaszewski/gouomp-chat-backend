package pl.edu.wat.gouompbackend.app.message.dto;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class GetMessageDto implements Serializable {

	String id;
	String body;
	Date createDate;
	GetUserDto user;
	String channelId;

}
