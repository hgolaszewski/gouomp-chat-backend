package pl.edu.wat.gouompbackend.app.channel.controller.interfaces;

import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.channel.dto.CreateChannelDto;
import pl.edu.wat.gouompbackend.app.channel.dto.GetChannelDto;
import pl.edu.wat.gouompbackend.app.channel.dto.LoginToPrivateChannelDto;
import pl.edu.wat.gouompbackend.app.channel.dto.UpdateChannelDto;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;

import java.util.List;

public interface ChannelController {
	ResponseEntity<SingleIdDto> createChannel(CreateChannelDto createChannelDto);
	void loginToPrivateChannel(LoginToPrivateChannelDto loginToPrivateChannelDto);
	ResponseEntity<GetChannelDto> findChannelById(String channelId);
	ResponseEntity<List<GetChannelDto>> listChannel();
	void updateChannel(UpdateChannelDto updateChannelDto);
	void deleteChannelById(String channelId, String username);
}
