package pl.edu.wat.gouompbackend.app.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;

import java.io.Serializable;
import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@Document
@Builder
@Getter @Setter
@FieldDefaults(level = PRIVATE)
public class User implements Serializable {

	@Id
	String id;
	String username;
	String name;
	String surname;
	String passwordHash;
	String email;
	String lastLocation;
	Date createDate;


	public GetUserDto convertToDto() {
		return GetUserDto.builder()
				.id(id)
				.username(username)
				.name(name)
				.surname(surname)
				.email(email)
				.createDate(createDate)
				.lastLocation(lastLocation)
				.build();
	}

}
