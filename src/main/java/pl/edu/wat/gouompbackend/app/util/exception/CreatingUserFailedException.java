package pl.edu.wat.gouompbackend.app.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class CreatingUserFailedException extends RuntimeException {

	public CreatingUserFailedException(String message) {
		super(message);
	}

}
