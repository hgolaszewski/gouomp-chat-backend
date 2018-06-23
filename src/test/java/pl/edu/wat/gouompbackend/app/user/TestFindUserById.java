package pl.edu.wat.gouompbackend.app.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.user.base.BaseTestUser;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;

public class TestFindUserById extends BaseTestUser {

	@Test
	public void findsUserById() {
		/* given */
		User persistedUser = persistUser();
		/* when */
		ResponseEntity<GetUserDto> userDto = userController.findUserById(persistedUser.getId());
		/* then */
		assertThat(userDto.getBody().getId()).isEqualTo(persistedUser.getId());
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoUserWithGivenId() {
		/* given */
		String fakeId = "fakeId";
		/* when/then */
		userController.findUserById(fakeId);
	}

}
