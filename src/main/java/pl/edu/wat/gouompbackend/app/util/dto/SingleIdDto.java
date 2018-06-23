package pl.edu.wat.gouompbackend.app.util.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Setter @Getter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class SingleIdDto {

	String id;

	public static SingleIdDto create(String id) {
		SingleIdDto singleIdDto = new SingleIdDto();
		singleIdDto.setId(id);
		return singleIdDto;
	}

}
