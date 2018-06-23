package pl.edu.wat.gouompbackend.app.chatResource.service.implementation;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static pl.edu.wat.gouompbackend.app.util.exception.message.ExceptionMessage.*;

import java.util.List;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.gouompbackend.app.aws.client.AWSFileMetaData;
import pl.edu.wat.gouompbackend.app.aws.client.AWSS3Client;
import pl.edu.wat.gouompbackend.app.channel.dao.interfaces.ChannelDao;
import pl.edu.wat.gouompbackend.app.channel.model.Channel;
import pl.edu.wat.gouompbackend.app.chatResource.dao.interfaces.ChatResourceDao;
import pl.edu.wat.gouompbackend.app.chatResource.dto.GetChatResourceDto;
import pl.edu.wat.gouompbackend.app.chatResource.model.ChatResource;
import pl.edu.wat.gouompbackend.app.chatResource.service.interfaces.ChatResourceService;
import pl.edu.wat.gouompbackend.app.util.exception.EntityNotFoundException;
import pl.edu.wat.gouompbackend.app.util.exception.UploadingFileFailedException;

@Service
@FieldDefaults(level = PRIVATE)
public class ChatResourceServiceImpl implements ChatResourceService {

	ChannelDao channelDao;
	AWSS3Client awss3Client;
	ChatResourceDao chatResourceDao;

	@Autowired
	public ChatResourceServiceImpl(ChannelDao channelDao, AWSS3Client awss3Client, ChatResourceDao chatResourceDao) {
		this.channelDao = channelDao;
		this.awss3Client = awss3Client;
		this.chatResourceDao = chatResourceDao;
	}

	@Override
	public void uploadChatResourceForChannel(MultipartFile multipartFile, String channelId) {
		Channel channel = channelDao.findById(channelId)
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_CHANNEL_ID_MESSAGE));
		AWSFileMetaData awsFileMetaData;
		try {
			awsFileMetaData = awss3Client.uploadFile(multipartFile);
			ChatResource chatResource = ChatResource.builder()
					.name(awsFileMetaData.getName())
					.awsLocation(awsFileMetaData.getAwsLocation())
					.channelId(channel.getId())
					.build();
			chatResourceDao.save(chatResource);
		} catch (Exception e) {
			throw new UploadingFileFailedException(UPLOADING_FILE_FAILED_MESSAGE);
		}
	}

	@Override
	public List<GetChatResourceDto> listChatResourceForChannelById(String channelId) {
		return chatResourceDao.findAllByChannelId(channelId).stream()
				.map(ChatResource::convertToDto)
				.collect(toList());
	}

	@Override
	public void deleteChatResourceById(String chatResourceId) {
		ChatResource chatResourceToDelete = chatResourceDao.findById(chatResourceId)
				.orElseThrow(() -> new EntityNotFoundException(NO_SUCH_RESOURCE_ID_MESSAGE));
		awss3Client.deleteFile(chatResourceToDelete.getName());
		chatResourceDao.deleteById(chatResourceId);
	}

}
