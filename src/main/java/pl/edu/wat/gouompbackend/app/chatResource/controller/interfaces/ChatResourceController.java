package pl.edu.wat.gouompbackend.app.chatResource.controller.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.gouompbackend.app.chatResource.dto.GetChatResourceDto;

public interface ChatResourceController {
	ResponseEntity uploadChatResourceForChannel(MultipartFile multipartFile, String channelId);
	ResponseEntity<List<GetChatResourceDto>> listChatResourceForChannelById(String channelId);
	ResponseEntity deleteChatResourceById(String chatResourceId);
}
