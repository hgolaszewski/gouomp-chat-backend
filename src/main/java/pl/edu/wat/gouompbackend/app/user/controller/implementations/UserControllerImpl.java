package pl.edu.wat.gouompbackend.app.user.controller.implementations;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.gouompbackend.app.user.controller.interfaces.UserController;
import pl.edu.wat.gouompbackend.app.user.dto.CreateUserDto;
import pl.edu.wat.gouompbackend.app.user.dto.GetUserDto;
import pl.edu.wat.gouompbackend.app.user.dto.UpdateUserDto;
import pl.edu.wat.gouompbackend.app.user.dto.UpdateUserLocationDto;
import pl.edu.wat.gouompbackend.app.user.logic.interfaces.UserService;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@FieldDefaults(level = PRIVATE)
public class UserControllerImpl implements UserController {

	UserService userService;

	@Autowired
	public UserControllerImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	@PostMapping("/user/sign-up")
	public ResponseEntity<SingleIdDto> createUser(@RequestBody CreateUserDto createUserDto) {
		return ResponseEntity.status(201).body(userService.createUser(createUserDto));
	}

	@Override
	@GetMapping("/user/findById/{userId}")
	public ResponseEntity<GetUserDto> findUserById(@PathVariable String userId) {
		return ResponseEntity.status(200).body(userService.findUserById(userId));
	}

	@Override
	@GetMapping("/user/findByUsername/{username}")
	public ResponseEntity<GetUserDto> findUserByUsername(@PathVariable String username) {
		return ResponseEntity.status(200).body(userService.findUserByUsername(username));
	}

	@Override
	@GetMapping("/user/list")
	public ResponseEntity<List<GetUserDto>> listUsers() {
		return ResponseEntity.status(200).body(userService.listUsers());
	}

	@Override
	@PutMapping("/user/update")
	public ResponseEntity updateUser(@RequestBody UpdateUserDto updateUserDto) {
		userService.updateUser(updateUserDto);
		return ResponseEntity.status(200).build();
	}

	@Override
	@DeleteMapping("/user/delete/{username}/executor/{executorUsername}")
	public ResponseEntity deleteUserByUsername(@PathVariable String username, @PathVariable String executorUsername) {
		userService.deleteUserByUsername(username, executorUsername);
		return ResponseEntity.status(200).build();
	}

	@Override
	@PutMapping("/user/updateUserLocation")
	public ResponseEntity updateUserLocation(@RequestBody UpdateUserLocationDto updateUserLocationDto) {
		userService.updateUserLocation(updateUserLocationDto);
		return ResponseEntity.status(200).build();
	}

}
