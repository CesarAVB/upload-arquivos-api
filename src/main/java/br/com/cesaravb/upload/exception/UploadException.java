package br.com.cesaravb.upload.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UploadException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public UploadException(String message) {
        super(message);
    }
}