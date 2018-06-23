package pl.edu.wat.gouompbackend.app.user.dto;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class GetUserDto implements Serializable {

	@Id
	String id;
	String username;
	String name;
	String surname;
	String email;
	Date createDate;
	String lastLocation;

}
