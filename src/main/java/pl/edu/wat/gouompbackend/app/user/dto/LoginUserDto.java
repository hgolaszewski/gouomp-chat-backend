package pl.edu.wat.gouompbackend.app.user.dto;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class LoginUserDto implements Serializable {

	String username;
	String password;

}
