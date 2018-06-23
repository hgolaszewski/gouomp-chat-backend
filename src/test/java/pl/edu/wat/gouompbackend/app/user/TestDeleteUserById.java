package pl.edu.wat.gouompbackend.app.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import pl.edu.wat.gouompbackend.app.user.base.BaseTestUser;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;
import pl.edu.wat.gouompbackend.app.util.exception.NoPermissionException;

public class TestDeleteUserById extends BaseTestUser {

	@Test
	public void deletesUser() {
		/* given */
		User persistedUser = persistUser();
		/* when */
		userController.deleteUserByUsername(persistedUser.getUsername(), persistedUser.getUsername());
		/* then */
		assertThat(getAllUsers()).hasSize(0);
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoUserWithGivenId() {
		/* given */
		String fakeUsername = "fakeUsername";
		/* when */
		userController.deleteUserByUsername(fakeUsername, fakeUsername);
	}


	@Test(expected = NoPermissionException.class)
	public void failsWhenExecutorDoesNotOwnAccount() {
		/* given */
		User firstPersistedUser = persistUser();
		User secondPersistedUser = persistUser();
		/* when */
		userController.deleteUserByUsername(firstPersistedUser.getUsername(), secondPersistedUser.getUsername());
	}

}
