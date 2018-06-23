package pl.edu.wat.gouompbackend.app.user.dto;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserLocationDto implements Serializable {

	String username;
	Double latitude;
	Double longitude;

}

