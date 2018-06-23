package pl.edu.wat.gouompbackend.app.user.dto;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserLocationDto implements Serializable {

	Address address;

	@Getter @Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@FieldDefaults(level = AccessLevel.PRIVATE)
	private class Address implements Serializable {

		String city;
		String village;
		String county;
		String country;

	}

	public String getCity() {
		return address.getCity();
	}

	public String getVillage() {
		return address.getVillage();
	}

	public String getCounty() {
		return address.getCounty();
	}

	public String getCountry() {
		return address.getCountry();
	}

}
