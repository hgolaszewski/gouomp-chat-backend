package pl.edu.wat.gouompbackend.app.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Iterables;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.user.base.BaseTestUser;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.builder.UserBuilder;

public class TestListUser extends BaseTestUser {

	@Test
	public void listsUsers() {
		/* given */
		Iterable<User> persistedUsers = persistUsers();
		/* when */
		ResponseEntity<List<GetUserDto>> listedUsers = userController.listUsers();
		/* then */
		int persistedUsersSize = Iterables.size(persistedUsers);
		assertThat(listedUsers.getBody()).hasSize(persistedUsersSize);
	}

	private Iterable<User> persistUsers() {
		User firstUser = new UserBuilder().build();
		User secondUser = new UserBuilder().build();
		List<User> usersToPersist = Arrays.asList(firstUser, secondUser);
		return saveUsers(usersToPersist);
	}

}
