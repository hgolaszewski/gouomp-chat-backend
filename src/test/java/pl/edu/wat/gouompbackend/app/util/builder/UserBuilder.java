package pl.edu.wat.gouompbackend.app.util.builder;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import pl.edu.wat.gouompbackend.app.user.model.User;

@Wither
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserBuilder {

	private static int idSequence = 0;

	String id = "someId_" + ++idSequence;
	String username = "username_" + idSequence;
	String name = "name";
	String surname = "surname";
	String passwordHash = "passwordHash";
	String email = "email";
	String lastLocation = "lastLocation";
	Date createDate = new Date();

	public User build() {
		return User.builder()
				.id(id)
				.username(username)
				.name(name)
				.surname(surname)
				.passwordHash(passwordHash)
				.email(email)
				.createDate(createDate)
				.lastLocation(lastLocation)
				.build();
	}

}
