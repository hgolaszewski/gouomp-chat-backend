package pl.edu.wat.gouompbackend.app.user.logic.interfaces;

import pl.edu.wat.gouompbackend.app.user.dto.CreateUserDto;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;
import pl.edu.wat.gouompbackend.app.user.dto.UpdateUserDto;
import pl.edu.wat.gouompbackend.app.user.dto.UpdateUserLocationDto;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;

import java.util.List;

public interface UserService {
	SingleIdDto createUser(CreateUserDto createUserDto);
	GetUserDto findUserById(String id);
	GetUserDto findUserByUsername(String username);
	List<GetUserDto> listUsers();
	void updateUser(UpdateUserDto updateUserDto);
	void deleteUserByUsername(String username, String executorUsername);
	void updateUserLocation(UpdateUserLocationDto updateUserLocationDto);
}
