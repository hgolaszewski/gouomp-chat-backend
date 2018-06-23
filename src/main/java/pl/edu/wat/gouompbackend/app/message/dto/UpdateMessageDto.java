package pl.edu.wat.gouompbackend.app.message.dto;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UpdateMessageDto implements Serializable {

	String id;
	String body;

}
