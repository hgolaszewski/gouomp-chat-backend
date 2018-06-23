package pl.edu.wat.gouompbackend.app.user.controller.interfaces;

import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.user.dto.CreateUserDto;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;
import pl.edu.wat.gouompbackend.app.user.dto.UpdateUserDto;
import pl.edu.wat.gouompbackend.app.user.dto.UpdateUserLocationDto;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;

import java.util.List;

public interface UserController {
	ResponseEntity<SingleIdDto> createUser(CreateUserDto createUserDto);
	ResponseEntity<GetUserDto> findUserById(String id);
	ResponseEntity<GetUserDto> findUserByUsername(String username);
	ResponseEntity<List<GetUserDto>> listUsers();
	ResponseEntity updateUser(UpdateUserDto updateUserDto);
	ResponseEntity deleteUserByUsername(String username, String executorUsername);
	ResponseEntity updateUserLocation(UpdateUserLocationDto updateUserLocationDto);
}
