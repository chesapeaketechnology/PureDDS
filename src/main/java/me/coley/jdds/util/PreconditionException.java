package me.coley.jdds.util;

import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.core.ServiceEnvironment;

/**
 * PreconditionNotMetException implementation.
 *
 * @author Matt Coley
 */
public class PreconditionException extends PreconditionNotMetException {
	private final ServiceEnvironment environment;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param cause
	 * 		Exception cause.
	 */
	public PreconditionException(ServiceEnvironment environment, Throwable cause) {
		super(cause);
		this.environment = environment;
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param message
	 * 		Exception message.
	 */
	public PreconditionException(ServiceEnvironment environment, String message) {
		super(message);
		this.environment = environment;
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param cause
	 * 		Exception cause.
	 * @param message
	 * 		Exception message.
	 */
	public PreconditionException(ServiceEnvironment environment, Throwable cause, String message) {
		super(message, cause);
		this.environment = environment;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
