package pl.edu.wat.gouompbackend.app.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import pl.edu.wat.gouompbackend.app.user.base.BaseTestUser;
import pl.edu.wat.gouompbackend.app.user.dto.UpdateUserDto;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;
import pl.edu.wat.gouompbackend.app.util.exception.NoPermissionException;

public class TestUpdateUser extends BaseTestUser {

	@Test
	public void updatesUser() throws Exception {
		/* given */
		User persistedUser = persistUser();
		UpdateUserDto updateUserDto = prepareUpdateUserDto(persistedUser.getUsername());
		/* when */
		userController.updateUser(updateUserDto);
		/* then */
		User updatedUser = getUserById(persistedUser.getId());
		assertThat(getAllUsers()).hasSize(1);
		assertFields(updatedUser);
	}

	@Test(expected = EntityNotFoundException.class)
	public void failsWhenNoUserWithGivenUsername() {
		/* given */
		String fakeUsername = "fakeUsername";
		UpdateUserDto updateUserDto = prepareUpdateUserDto(fakeUsername);
		/* when/then */
		userController.updateUser(updateUserDto);
	}

	@Test(expected = NoPermissionException.class)
	public void failsWhenNoPermissionToUpdateUser() {
		/* given */
		User firstPersistedUser = persistUser();
		User secondPersistedUser = persistUser();
		UpdateUserDto updateUserDto = prepareUpdateUserDto(firstPersistedUser.getUsername());
		updateUserDto.setExecutorUsername(secondPersistedUser.getUsername());
		/* when/then */
		userController.updateUser(updateUserDto);
	}

	private UpdateUserDto prepareUpdateUserDto(String username) {
		return UpdateUserDto.builder()
				.username(username)
				.name("newName")
				.surname("newSurname")
				.email("newEmail")
				.password("newPassword")
				.executorUsername(username)
				.build();
	}

	private static void assertFields(User updatedUser) {
		assertThat(updatedUser.getPasswordHash()).isNotNull();
		assertThat(updatedUser.getName()).isEqualTo("newName");
		assertThat(updatedUser.getSurname()).isEqualTo("newSurname");
		assertThat(updatedUser.getEmail()).isEqualTo("newEmail");
	}

}
