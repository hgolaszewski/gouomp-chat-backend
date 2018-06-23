package pl.edu.wat.gouompbackend.app.chatResource.controller.implementation;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.gouompbackend.app.chatResource.controller.interfaces.ChatResourceController;
import pl.edu.wat.gouompbackend.app.chatResource.dto.GetChatResourceDto;
import pl.edu.wat.gouompbackend.app.chatResource.service.interfaces.ChatResourceService;

@RestController
@FieldDefaults(level = PRIVATE)
public class ChatResourceControllerImpl implements ChatResourceController {

	ChatResourceService chatResourceService;

	@Autowired
	public ChatResourceControllerImpl(ChatResourceService chatResourceService) {
		this.chatResourceService = chatResourceService;
	}

	@Override
	@PostMapping("/chatResource/upload/{channelId}")
	public ResponseEntity uploadChatResourceForChannel(@RequestPart(value = "file") MultipartFile multipartFile,
											 @PathVariable String channelId) {
		chatResourceService.uploadChatResourceForChannel(multipartFile, channelId);
		return ResponseEntity.status(201).build();
	}

	@Override
	@GetMapping("/chatResource/list/{channelId}")
	public ResponseEntity<List<GetChatResourceDto>> listChatResourceForChannelById(@PathVariable String channelId) {
		return ResponseEntity.status(200).body(chatResourceService.listChatResourceForChannelById(channelId));
	}

	@Override
	@DeleteMapping("/chatResource/delete/{chatResourceId}")
	public ResponseEntity deleteChatResourceById(@PathVariable String chatResourceId) {
		chatResourceService.deleteChatResourceById(chatResourceId);
		return ResponseEntity.status(200).build();
	}

}
