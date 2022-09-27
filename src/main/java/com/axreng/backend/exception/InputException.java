package com.axreng.backend.exception;

import java.io.IOException;

/**
 * @author Matheus Longo
 *
 */

public class InputException extends IOException{

	private static final long serialVersionUID = 1L;
	private static final String invalidUrlMessage = "Invalid Base URL: ";
	private static final String invalidKeywordErrorMessage = "Invalid! Keyword size must be between 4 and 32. Actual: ";
	
	public InputException(String message) {
		super(message);
	}
	public InputException(String baseURL, Throwable cause) {
		super(invalidUrlMessage + baseURL, cause);
	}

	public InputException(int keywordSize) {
		super(invalidKeywordErrorMessage + keywordSize);
	}
}
