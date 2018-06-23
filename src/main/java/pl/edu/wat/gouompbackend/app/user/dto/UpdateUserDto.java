package pl.edu.wat.gouompbackend.app.user.dto;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UpdateUserDto implements Serializable {

	String username;
	String name;
	String surname;
	String password;
	String email;
	String executorUsername;

}
