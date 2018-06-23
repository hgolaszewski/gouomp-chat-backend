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
public class UpdateChannelDto implements Serializable {

	String id;
	String name;
	String description;
	String password;
	String executorUsername;

}
