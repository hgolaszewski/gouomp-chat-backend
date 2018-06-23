package pl.edu.wat.gouompbackend.app.message.controller.implementations;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.gouompbackend.app.message.controller.interfaces.MessageController;
import pl.edu.wat.gouompbackend.app.message.dto.CreateMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.GetMessageDto;
import pl.edu.wat.gouompbackend.app.message.dto.UpdateMessageDto;
import pl.edu.wat.gouompbackend.app.message.logic.interfaces.MessageService;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@FieldDefaults(level = PRIVATE)
public class MessageControllerImpl implements MessageController {

	MessageService messageService;

	@Autowired
	public MessageControllerImpl(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	@PostMapping("/message/create")
	public ResponseEntity<GetMessageDto> createMessage(@RequestBody CreateMessageDto createMessageDto) {
		return ResponseEntity.status(201).body(messageService.createMessage(createMessageDto));
	}

	@Override
	//GetMapping("/message/find/{messageId}")
	public ResponseEntity<GetMessageDto> findMessageById(@PathVariable String messageId) {
		return ResponseEntity.status(200).body(messageService.findMessageById(messageId));
	}

	@Override
	//@GetMapping("/message/list")
	public ResponseEntity<List<GetMessageDto>> listMessage() {
		return ResponseEntity.status(200).body(messageService.listMessage());
	}

	@Override
	//@PutMapping("/message/update")
	public ResponseEntity updateMessage(@RequestBody UpdateMessageDto updateMessageDto) {
		messageService.updateMessage(updateMessageDto);
		return ResponseEntity.status(200).build();
	}

	@Override
	//@DeleteMapping("/message/delete/{messageId}")
	public ResponseEntity deleteMessageById(@PathVariable String messageId) {
		messageService.deleteMessageById(messageId);
		return ResponseEntity.status(200).build();
	}

}
