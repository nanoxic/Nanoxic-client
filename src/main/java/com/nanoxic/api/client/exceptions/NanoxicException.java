package com.nanoxic.api.client.exceptions;

public abstract class NanoxicException extends RuntimeException {

	private static final long serialVersionUID = 7401304686968120790L;

	public NanoxicException(final String message) {
		this(message, null);
	}

	public NanoxicException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
