package pl.edu.wat.gouompbackend.app.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import pl.edu.wat.gouompbackend.app.user.base.BaseTestUser;
import pl.edu.wat.gouompbackend.app.user.dto.UpdateUserLocationDto;
import pl.edu.wat.gouompbackend.app.user.logic.interfaces.UserService;
import pl.edu.wat.gouompbackend.app.user.model.User;

public class TestUpdateUserLocation extends BaseTestUser {

	User user;

	@Before
	public void setUp() {
		user = persistUser();
	}

	@Test
	public void updatesUserLocation() throws Exception {
		/* given */
		assertThat(user.getLastLocation()).isEqualTo("lastLocation");
		UpdateUserLocationDto updateUserLocationDto = prepareUpdateUserLocationDto();
		/* when */
		((UserService) getBean(UserService.class)).updateUserLocation(updateUserLocationDto);
		/* then */
		User updatedUser = getUserById(user.getId());
		assertThat(updatedUser.getLastLocation()).isEqualTo("Warszawa");

	}

	private UpdateUserLocationDto prepareUpdateUserLocationDto() {
		return UpdateUserLocationDto.builder()
				.username(user.getUsername())
				.latitude(52.22967560)
				.longitude(21.01222870)
				.build();
	}

}
