package pl.edu.wat.gouompbackend.app.message.logic.interfaces;

import pl.edu.wat.gouompbackend.app.message.dto.CreateMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.UpdateMessageDto;

import java.util.List;

public interface MessageService {
	GetMessageDto createMessage(CreateMessageDto createMessageDto);
	GetMessageDto findMessageById(String id);
	List<GetMessageDto> listMessage();
	void updateMessage(UpdateMessageDto updateMessageDto);
	void deleteMessageById(String id);
}
