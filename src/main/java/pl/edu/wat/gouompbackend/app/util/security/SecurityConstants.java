package pl.edu.wat.gouompbackend.app.util.security;

import static pl.edu.wat.gouompbackend.app.util.exception.message.ExceptionMessage.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pl.edu.wat.gouompbackend.app.util.exception.CreatingUserFailedException;

public class SecurityConstants {

	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/user/sign-up";

	public static String getSHA512SecurePassword(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
			byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new CreatingUserFailedException(CREATING_USER_FAILED_MESSAGE);
		}
	}

}
