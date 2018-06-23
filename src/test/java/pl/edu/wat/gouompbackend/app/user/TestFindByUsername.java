package pl.edu.wat.gouompbackend.app.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.user.base.BaseTestUser;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;

public class TestFindByUsername extends BaseTestUser {

	@Test
	public void findsUserByUsername() {
		/* given */
		User persistedUser = persistUser();
		/* when */
		ResponseEntity<GetUserDto> userDto = userController.findUserByUsername(persistedUser.getUsername());
		/* then */
		assertThat(userDto.getBody().getUsername()).isEqualTo(persistedUser.getUsername());
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoUserWithGivenUsername() {
		/* given */
		String fakeUsername = "fakeUsername";
		/* when/then */
		userController.findUserByUsername(fakeUsername);
	}

}
