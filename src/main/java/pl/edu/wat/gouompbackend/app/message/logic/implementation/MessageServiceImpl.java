package pl.edu.wat.gouompbackend.app.message.logic.implementation;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.gouompbackend.app.channel.dao.interfaces.ChannelDao;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.message.dao.interfaces.MessageDao;
import pl.edu.wat.gouompbackend.app.message.dto.CreateMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.UpdateMessageDto;
import pl.edu.wat.gouompbackend.app.message.logic.interfaces.MessageService;
import pl.edu.wat.gouompbackend.app.message.model.Message;
import pl.edu.wat.gouompbackend.app.user.dao.interfaces.UserDao;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;
import pl.edu.wat.gouompbackend.app.user.model.User;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static pl.edu.wat.gouompbackend.app.util.exception.message.ExceptionMessage.*;

@Service
@FieldDefaults(level = PRIVATE)
public class MessageServiceImpl implements MessageService {

	MessageDao messageDao;
	ChannelDao channelDao;
	UserDao userDao;

	@Autowired
	public MessageServiceImpl(MessageDao message,
							  ChannelDao channelDao,
							  UserDao userDao) {
		this.messageDao = message;
		this.channelDao = channelDao;
		this.userDao = userDao;
	}

	@Override
	public GetMessageDto createMessage(CreateMessageDto createMessageDto) {
		Channel channelToAddMessage = channelDao.findById(createMessageDto.getChannelId())
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_CHANNEL_ID_MESSAGE));
		User messageSender = userDao.findByUsername(createMessageDto.getUsername())
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USERNAME_MESSAGE));
		Message messageToCreate = createMessageDto.convertToEntity();
		messageToCreate.setUser(messageSender);
		messageToCreate.setChannelId(channelToAddMessage.getId());
		Message savedMessage = messageDao.save(messageToCreate);
		channelToAddMessage.getMessages().add(savedMessage);
		channelDao.save(channelToAddMessage);
		return savedMessage.convertToDto();
	}

	@Override
	public GetMessageDto findMessageById(String id) {
		return messageDao.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_MESSAGE_ID_MESSAGE))
				.convertToDto();
	}

	@Override
	public List<GetMessageDto> listMessage() {
		return messageDao.findAll().stream()
				.map(Message::convertToDto)
				.collect(toList());
	}

	@Override
	public void updateMessage(UpdateMessageDto updateMessageDto) {
		Message messageToUpdate = messageDao.findById(updateMessageDto.getId())
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_MESSAGE_ID_MESSAGE));
		messageToUpdate.setBody(updateMessageDto.getBody());
		messageDao.save(messageToUpdate);
	}

	@Override
	public void deleteMessageById(String messageId) {
		messageDao.findById(messageId).orElseThrow(() -> new EntityNotFoundException(NO_SUCH_MESSAGE_ID_MESSAGE));
		messageDao.deleteById(messageId);
	}

}
