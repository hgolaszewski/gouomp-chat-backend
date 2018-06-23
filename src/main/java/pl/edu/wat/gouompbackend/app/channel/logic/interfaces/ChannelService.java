package pl.edu.wat.gouompbackend.app.channel.logic.interfaces;

import pl.edu.wat.gouompbackend.app.channel.dto.*;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;

import java.util.List;

public interface ChannelService {
	SingleIdDto createChannel(CreateChannelDto createChannelDto);
	GetChannelDto findChannelById(String channelId);
	List<GetChannelDto> listChannel();
	void updateChannel(UpdateChannelDto updateChannelDto);
	void deleteChannelById(String channelId, String username);
	void loginToPrivateChannel(LoginToPrivateChannelDto loginRequest);
}
