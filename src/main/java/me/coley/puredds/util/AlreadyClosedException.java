package me.coley.puredds.util;

import org.omg.dds.core.ServiceEnvironment;

/**
 * AlreadyClosedException implementation.
 *
 * @author Matt Coley
 */
public class AlreadyClosedException extends org.omg.dds.core.AlreadyClosedException {
	private final ServiceEnvironment environment;

	/**
	 * @param environment
	 * 		Environment context.
	 */
	public AlreadyClosedException(ServiceEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param message
	 * 		Exception message.
	 */
	public AlreadyClosedException(ServiceEnvironment environment, String message) {
		super(message);
		this.environment = environment;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
