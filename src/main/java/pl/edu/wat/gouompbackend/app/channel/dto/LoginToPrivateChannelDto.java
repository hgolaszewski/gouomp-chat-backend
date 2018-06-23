package pl.edu.wat.gouompbackend.app.channel.dto;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class LoginToPrivateChannelDto implements Serializable {

	String password;
	String channelId;

}
