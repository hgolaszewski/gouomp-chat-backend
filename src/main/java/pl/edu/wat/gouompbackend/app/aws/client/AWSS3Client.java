package pl.edu.wat.gouompbackend.app.aws.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AWSS3Client {

	private AmazonS3 s3client;
	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@PostConstruct
	private void initialize() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	public AWSFileMetaData uploadFile(MultipartFile multipartFile) throws Exception {
		File file = convertMultiPartToFile(multipartFile);
		String fileName = generateFileName(multipartFile);
		String fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
		uploadFileTos3bucket(fileName, file);
		file.delete();
		return new AWSFileMetaData(fileName, fileUrl);
	}

	public void deleteFile(String fileName) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);
		s3client.deleteObject(deleteObjectRequest);
	}

	private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
		File convertedFile = new File(multipartFile.getOriginalFilename());
		FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
		fileOutputStream.write(multipartFile.getBytes());
		fileOutputStream.close();
		return convertedFile;
	}

	private String generateFileName(MultipartFile multipartFile) {
		return new Date().getTime() + "-" + multipartFile.getOriginalFilename().replace(" ", "_");
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
				.withCannedAcl(CannedAccessControlList.PublicRead)
		);
	}

}
