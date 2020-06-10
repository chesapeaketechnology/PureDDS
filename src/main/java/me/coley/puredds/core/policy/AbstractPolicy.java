package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;

/**
 * Base policy class.
 *
 * @author Matt Coley
 */
public abstract class AbstractPolicy {
	private final ServiceEnvironment environment;

	/**
	 * @param environment
	 * 		Environment context.
	 */
	public AbstractPolicy(ServiceEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * @return Environment context.
	 */
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
