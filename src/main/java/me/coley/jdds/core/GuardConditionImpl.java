package me.coley.jdds.core;

import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.ServiceEnvironment;

/**
 * Guard condition implementation.
 *
 * @author Matt Coley
 */
public class GuardConditionImpl extends GuardCondition {
	private final JServiceEnvironment environment;
	private boolean trigger;

	/**
	 * @param spi
	 * 		Spawning provider.
	 */
	public GuardConditionImpl(JServiceProvider spi) {
		this.environment = spi.getEnvironment();
	}

	@Override
	public void setTriggerValue(boolean value) {
		if (getTriggerValue() != value) {
			trigger = value;
			// TODO: Read purpose of "GuardCondition"
			//   - do we need to do anything more here?
		}
	}

	@Override
	public boolean getTriggerValue() {
		return trigger;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
