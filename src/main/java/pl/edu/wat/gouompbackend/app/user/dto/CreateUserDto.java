package pl.edu.wat.gouompbackend.app.user.dto;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.wat.gouompbackend.app.user.model.User;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateUserDto implements Serializable {

	String username;
	String name;
	String surname;
	String password;
	String email;

	public User convertToEntity() {
		return User.builder()
				.username(username)
				.name(name)
				.surname(surname)
				.email(email)
				.createDate(new Date())
				.build();
	}

}
