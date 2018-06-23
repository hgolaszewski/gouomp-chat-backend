package pl.edu.wat.gouompbackend.app.user.base;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.wat.gouompbackend.app.user.controller.interfaces.UserController;
import pl.edu.wat.gouompbackend.app.user.dao.interfaces.UserDao;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.test.BaseMongoEmbeddedTest;
import pl.edu.wat.gouompbackend.app.util.builder.UserBuilder;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;
import static pl.edu.wat.gouompbackend.app.util.exception.message.ExceptionMessage.NO_SUCH_USER_ID_MESSAGE;

@FieldDefaults(level = PROTECTED)
public abstract class BaseTestUser extends BaseMongoEmbeddedTest {

	@Autowired
	UserController userController;

	protected User getUserById(String id) throws Exception {
		return ((UserDao) getBean(UserDao.class))
				.findById(id)
				.orElseThrow(() -> new Exception(NO_SUCH_USER_ID_MESSAGE));
	}

	protected User persistUser() {
		User usersToPersist = new UserBuilder().build();
		return ((UserDao) getBean(UserDao.class)).save(usersToPersist);
	}

	protected List<User> getAllUsers() {
		return ((UserDao) getBean(UserDao.class)).findAll();
	}

	protected Iterable<User> saveUsers(List<User> usersToPersist) {
		return ((UserDao) getBean(UserDao.class)).saveAll(usersToPersist);
	}

}
