package pl.edu.wat.gouompbackend.app.websocket.chat;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.edu.wat.gouompbackend.app.message.dto.CreateMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.message.logic.interfaces.MessageService;

@Controller
@CrossOrigin
@FieldDefaults(level = PRIVATE)
public class ChatWebSocket {

	MessageService messageService;

	@Autowired
	public ChatWebSocket(MessageService messageService) {
		this.messageService = messageService;
	}

	@SendTo("/topic/message")
	@MessageMapping("/broadcastMessage")
	public GetMessageDto createMessage(CreateMessageDto createMessageDto) {
		return messageService.createMessage(createMessageDto);
	}

}

