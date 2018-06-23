package pl.edu.wat.gouompbackend.app.channel.logic.implementation;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.gouompbackend.app.channel.dao.interfaces.ChannelDao;
import pl.edu.wat.gouompbackend.app.channel.dto.CreateChannelDto;
import pl.edu.wat.gouompbackend.app.channel.dto.GetChannelDto;
import pl.edu.wat.gouompbackend.app.channel.dto.LoginToPrivateChannelDto;
import pl.edu.wat.gouompbackend.app.channel.dto.UpdateChannelDto;
import pl.edu.wat.gouompbackend.app.channel.enums.ChannelType;
import pl.edu.wat.gouompbackend.app.channel.logic.interfaces.ChannelService;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.user.dao.interfaces.UserDao;
import pl.edu.wat.gouompbackend.app.util.exception.*;
import pl.edu.wat.gouompbackend.app.user.model.User;
import pl.edu.wat.gouompbackend.app.util.dto.SingleIdDto;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static pl.edu.wat.gouompbackend.app.util.exception.message.ExceptionMessage.*;
import static pl.edu.wat.gouompbackend.app.util.security.SecurityConstants.getSHA512SecurePassword;

@Service
@FieldDefaults(level = PRIVATE)
public class ChannelServiceImpl implements ChannelService {

	ChannelDao channelDao;
	UserDao userDao;

	@Autowired
	public ChannelServiceImpl(ChannelDao channelDao,
							  UserDao userDao) {
		this.channelDao = channelDao;
		this.userDao = userDao;
	}

	@Override
	public SingleIdDto createChannel(CreateChannelDto createChannelDto) {
		Channel existingChannel = channelDao.findByName(createChannelDto.getName());
		if (existingChannel != null) {
			throw new EntityAlreadyExistsException(CHANNEL_ALREADY_EXISTS_MESSAGE);
		}
		User creator = userDao.findByUsername(createChannelDto.getUsername())
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USERNAME_MESSAGE));
		Channel channelToCreate = createChannelDto.convertToEntity();
		setPasswordIfPrivateChannel(createChannelDto, channelToCreate);
		channelToCreate.setCreatorUsername(creator.getUsername());
		Channel savedChannel = channelDao.save(channelToCreate);
		return SingleIdDto.create(savedChannel.getId());
	}

	private void setPasswordIfPrivateChannel(CreateChannelDto createChannelDto, Channel channelToCreate) {
		if (channelToCreate.getChannelType() == ChannelType.PRIVATE) {
			String channelPassword = createChannelDto.getPassword();
			if (channelPassword == null || channelPassword.isEmpty()) {
				throw new EmptyFieldException(PASSWORD_CANNOT_BE_EMPTY_MESSAGE);
			}
			String sha512SecurePassword = getSHA512SecurePassword(channelPassword);
			channelToCreate.setPasswordHash(sha512SecurePassword);
		}
	}

	@Override
	public GetChannelDto findChannelById(String id) {
		return channelDao.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_CHANNEL_ID_MESSAGE))
				.convertToDto();
	}

	@Override
	public List<GetChannelDto> listChannel() {
		return channelDao.findAll().stream()
				.map(Channel::convertToDto)
				.collect(toList());
	}

	@Override
	public void updateChannel(UpdateChannelDto updateChannelDto) {
		Channel channelToUpdate = channelDao.findById(updateChannelDto.getId())
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_CHANNEL_ID_MESSAGE));
		User owner = userDao.findByUsername(updateChannelDto.getExecutorUsername())
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USERNAME_MESSAGE));
		if (!channelToUpdate.getCreatorUsername().equals(owner.getUsername())) {
			throw new NoPermissionException(NO_PERMISSION_TO_UPDATE_CHANNEL_MESSAGE);
		}
		channelToUpdate.setName(updateChannelDto.getName());
		channelToUpdate.setDescription(updateChannelDto.getDescription());
		if (channelToUpdate.getChannelType() == ChannelType.PRIVATE) {
			String newPassword = updateChannelDto.getPassword();
			if (newPassword == null || newPassword.isEmpty()) {
				throw new EmptyFieldException(PASSWORD_CANNOT_BE_EMPTY_MESSAGE);
			}
			channelToUpdate.setPasswordHash(getSHA512SecurePassword(newPassword));
		}
		channelDao.save(channelToUpdate);
	}

	@Override
	public void deleteChannelById(String channelId, String username) {
		Channel channelToDelete = channelDao.findById(channelId).orElseThrow(
				() -> new EntityNotFoundException(NO_SUCH_CHANNEL_ID_MESSAGE)
		);
		User creator = userDao.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USERNAME_MESSAGE));
		if (!channelToDelete.getCreatorUsername().equals(creator.getUsername())) {
			throw new NoPermissionException(NO_PERMISSION_TO_DELETE_CHANNEL_MESSAGE);
		}
		channelDao.deleteById(channelId);
	}

	@Override
	public void loginToPrivateChannel(LoginToPrivateChannelDto loginRequest) {
		Channel channel = channelDao.findById(loginRequest.getChannelId())
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_CHANNEL_ID_MESSAGE));
		String password = loginRequest.getPassword();
		if (password == null || password.isEmpty()) {
			throw new EmptyFieldException(PASSWORD_CANNOT_BE_EMPTY_MESSAGE);
		}
		String passwordHash = getSHA512SecurePassword(password);
		if (!passwordHash.equals(channel.getPasswordHash())) {
			throw new WrongPasswordException(WRONG_PASSWORD_FOR_CHANNEL_MESSAGE);
		}
	}

}

