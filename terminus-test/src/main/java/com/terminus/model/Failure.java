package com.terminus.model;

import java.util.HashMap;
import java.util.Map;

public class Failure {
	private final Map<String, String> error;

	private Failure(Exception exception) {
		this.error = new HashMap<>();
		this.error.put("message", buildMessage(exception));
	}

	public Map<String, String> getError() {
		return error;
	}

	public static Failure newInstance(Exception exception) {
		return new Failure(exception);
	}

	private String buildMessage(Exception exception) {
		String message = "";
		switch (exception.getClass().getName()) {
		case "java.lang.IllegalArgumentException" -> {
			message = exception.getMessage();
		}
		case "java.lang.RuntimeException" -> {
			message = exception.getMessage();
		}
		case "javax.persistence.NoResultException" -> {
			message = exception.getMessage();
		}
		case "org.springframework.dao.DataIntegrityViolationException" -> {
			message = "Email Already Exist";
		}
		default -> {
			message = "Internal Server Error";
		}
		}
		return message;
	}
}
