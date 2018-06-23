package pl.edu.wat.gouompbackend.app.chatResource.service.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.gouompbackend.app.chatResource.dto.GetChatResourceDto;

public interface ChatResourceService {
	void uploadChatResourceForChannel(MultipartFile multipartFile, String channelId);
	List<GetChatResourceDto> listChatResourceForChannelById(String channelId);
	void deleteChatResourceById(String chatResourceId);
}
