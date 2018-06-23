package pl.edu.wat.gouompbackend.app.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.user.base.BaseTestUser;
import pl.edu.wat.gouompbackend.app.user.dto.CreateUserDto;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;
import pl.edu.wat.gouompbackend.app.util.exception.EntityAlreadyExistsException;

public class TestCreateUser extends BaseTestUser {

	@Test
	public void createsUser() throws Exception {
		/* given */
		CreateUserDto createUserDto = prepareCreateUserDto();
		/* when */
		ResponseEntity<SingleIdDto> singleIdDto = userController.createUser(createUserDto);
		/* then */
		User persistedUser = getUserById(singleIdDto.getBody().getId());
		assertFields(persistedUser);
	}

	@Test(expected = EntityAlreadyExistsException.class)
	public void failsWhenUserWithGivenUsernameAlreadyExists() {
		/* given */
		CreateUserDto createUserDto = prepareCreateUserDto();
		CreateUserDto createUserDtoDuplicate = prepareCreateUserDto();
		/* when/then */
		userController.createUser(createUserDto);
		userController.createUser(createUserDtoDuplicate);
	}

	private static void assertFields(User user) {
		assertThat(user.getId()).isNotNull();
		assertThat(user.getCreateDate()).isNotNull();
		assertThat(user.getPasswordHash()).isNotNull();
		assertThat(user.getUsername()).isEqualTo("username");
		assertThat(user.getEmail()).isEqualTo("email");
		assertThat(user.getName()).isEqualTo("name");
		assertThat(user.getSurname()).isEqualTo("surname");
		assertThat(user.getEmail()).isEqualTo("email");
		assertThat(user.getLastLocation()).isNull();
	}

	private static CreateUserDto prepareCreateUserDto() {
		return CreateUserDto.builder()
				.username("username")
				.password("password")
				.name("name")
				.surname("surname")
				.email("email")
				.build();
	}

}

