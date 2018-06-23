package pl.edu.wat.gouompbackend.app.message.controller.interfaces;

import org.springframework.http.ResponseEntity;
import pl.edu.wat.gouompbackend.app.message.dto.CreateMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.UpdateMessageDto;

import java.util.List;

public interface MessageController {
	ResponseEntity<GetMessageDto> createMessage(CreateMessageDto message);
	ResponseEntity<GetMessageDto> findMessageById(String id);
	ResponseEntity<List<GetMessageDto>> listMessage();
	ResponseEntity updateMessage(UpdateMessageDto message);
	ResponseEntity deleteMessageById(String id);
}
