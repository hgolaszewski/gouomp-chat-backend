package pl.edu.wat.gouompbackend.app.aws.client;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AWSFileMetaData {

	String name;
	String awsLocation;

}
