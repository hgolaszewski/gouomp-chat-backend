package pl.edu.wat.gouompbackend.app.user.logic.implementation;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.wat.gouompbackend.app.user.dao.interfaces.UserDao;
import pl.edu.wat.gouompbackend.app.user.dto.*;
import pl.edu.wat.gouompbackend.app.util.exception.EmptyFieldException;
import pl.edu.wat.gouompbackend.app.util.exception.EntityAlreadyExistsException;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;
import pl.edu.wat.gouompbackend.app.user.logic.interfaces.UserService;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;
import pl.edu.wat.gouompbackend.app.util.exception.NoPermissionException;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static pl.edu.wat.gouompbackend.app.util.exception.message.ExceptionMessage.*;

@Service
@FieldDefaults(level = PRIVATE)
public class UserServiceImpl implements UserService {

	UserDao userDao;
	BCryptPasswordEncoder passwordEncoder;
	static final String GEOLOCATION_API = "https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s";

	@Autowired
	public UserServiceImpl(UserDao userDao,
						   BCryptPasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public SingleIdDto createUser(CreateUserDto createUserDto) {
		String username = createUserDto.getUsername();
		if (username == null || username.isEmpty()) {
			throw new EmptyFieldException(USERNAME_CANNOT_BE_EMPTY_MESSAGE);
		}
		userDao.findByUsername(createUserDto.getUsername())
			.ifPresent(__ -> { throw new EntityAlreadyExistsException(USERNAME_ALREADY_EXISTS_MESSAGE); }
		);
		User userToCreate = createUserDto.convertToEntity();
		userToCreate.setPasswordHash(passwordEncoder.encode(createUserDto.getPassword()));
		User savedUser = userDao.save(userToCreate);
		return SingleIdDto.create(savedUser.getId());
	}

	@Override
	public GetUserDto findUserById(String id) {
		return userDao.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USER_ID_MESSAGE))
				.convertToDto();
	}

	@Override
	public GetUserDto findUserByUsername(String username) {
		User user = userDao.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USERNAME_MESSAGE));
		return user.convertToDto();
	}

	@Override
	public List<GetUserDto> listUsers() {
		return userDao.findAll().stream()
				.map(User::convertToDto)
				.collect(toList());
	}

	@Override
	public void updateUser(UpdateUserDto updateUserDto) {
		if (!updateUserDto.getUsername().equals(updateUserDto.getExecutorUsername())) {
			throw new NoPermissionException(NO_PERMISSION_TO_UPDATE_USER_MESSAGE);
		}
		User userToUpdate = userDao.findByUsername(updateUserDto.getUsername())
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USERNAME_MESSAGE));
		userToUpdate.setName(updateUserDto.getName());
		userToUpdate.setSurname(updateUserDto.getSurname());
		userToUpdate.setEmail(updateUserDto.getEmail());
		userToUpdate.setPasswordHash(passwordEncoder.encode(updateUserDto.getPassword()));
		userDao.save(userToUpdate);
	}

	@Override
	public void deleteUserByUsername(String username, String executorUsername) {
		if (!username.equals(executorUsername)) {
			throw new NoPermissionException(NO_PERMISSION_TO_DELETE_USER_MESSAGE);
		}
		User user = userDao.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USERNAME_MESSAGE));
		userDao.deleteById(user.getId());
	}

	@Override
	public void updateUserLocation(UpdateUserLocationDto updateUserLocationDto) {
		User userToUpdate = userDao.findByUsername(updateUserLocationDto.getUsername())
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USERNAME_MESSAGE));
		Double latitude = updateUserLocationDto.getLatitude();
		Double longitude = updateUserLocationDto.getLongitude();
		GetUserLocationDto getUserLocationDto = getLocation(latitude, longitude);
		String newLocation = resolveLocation(getUserLocationDto);
		userToUpdate.setLastLocation(newLocation);
		userDao.save(userToUpdate);
	}

	private GetUserLocationDto getLocation(Double latitude, Double longitude) {
		return new RestTemplate().exchange(
				String.format(GEOLOCATION_API, String.valueOf(latitude), String.valueOf(longitude)),
				HttpMethod.GET,
				prepareHeaders(),
				GetUserLocationDto.class
		).getBody();
	}

	private HttpEntity<String> prepareHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(
				"user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
				" AppleWebKit/537.36 (KHTML, like Gecko)" +
				" Chrome/54.0.2840.99 Safari/537.36"
		);
		return new HttpEntity<>("parameters", headers);
	}

	private String resolveLocation(GetUserLocationDto getUserLocationDto) {
		if (getUserLocationDto == null) {
			 return null;
		}
		String newLocation = null;
		if (getUserLocationDto.getCity() != null) {
			newLocation = getUserLocationDto.getCity();
		} else if (getUserLocationDto.getVillage() != null) {
			newLocation = getUserLocationDto.getVillage();
		} else if (getUserLocationDto.getCounty() != null) {
			newLocation = getUserLocationDto.getCounty();
		} else if (getUserLocationDto.getCountry() != null) {
			newLocation = getUserLocationDto.getCountry();
		}
		return newLocation;
	}

}
