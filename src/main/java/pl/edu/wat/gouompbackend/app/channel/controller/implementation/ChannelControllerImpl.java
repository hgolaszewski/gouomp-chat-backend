package pl.edu.wat.gouompbackend.app.channel.controller.implementation;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.gouompbackend.app.channel.controller.interfaces.ChannelController;
import pl.edu.wat.gouompbackend.app.channel.dto.*;
import pl.edu.wat.gouompbackend.app.channel.logic.interfaces.ChannelService;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@FieldDefaults(level = PRIVATE)
public class ChannelControllerImpl implements ChannelController {

	ChannelService channelService;

	@Autowired
	public ChannelControllerImpl(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Override
	@PostMapping("/channel/create")
	public ResponseEntity<SingleIdDto> createChannel(@RequestBody CreateChannelDto createChannelDto) {
		return ResponseEntity.ok(channelService.createChannel(createChannelDto));
	}

	@Override
	@PostMapping("/channel/login")
	public void loginToPrivateChannel(@RequestBody LoginToPrivateChannelDto loginToPrivateChannelDto) {
		channelService.loginToPrivateChannel(loginToPrivateChannelDto);
	}

	@Override
	@GetMapping("/channel/find/{channelId}")
	public ResponseEntity<GetChannelDto> findChannelById(@PathVariable String channelId) {
		return ResponseEntity.ok(channelService.findChannelById(channelId));
	}

	@Override
	@GetMapping("/channel/list")
	public ResponseEntity<List<GetChannelDto>> listChannel() {
		return ResponseEntity.ok(channelService.listChannel());
	}

	@Override
	@PutMapping("/channel/update")
	public void updateChannel(@RequestBody UpdateChannelDto updateChannelDto) {
		channelService.updateChannel(updateChannelDto);
	}

	@Override
	@DeleteMapping("/channel/delete/{channelId}/user/{username}")
	public void deleteChannelById(@PathVariable String channelId, @PathVariable String username) {
		channelService.deleteChannelById(channelId, username);
	}

}
