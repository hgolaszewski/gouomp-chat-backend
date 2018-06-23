package pl.edu.wat.gouompbackend.app.channel.dto;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.wat.gouompbackend.app.channel.enums.ChannelType;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class GetChannelDto implements Serializable {

	String id;
	String name;
	String description;
	ChannelType channelType;
	String username;
	Date createDate;
	List<GetMessageDto> messages;

}
