package me.coley.jdds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Lifespan;

import java.util.concurrent.TimeUnit;

/**
 * Lifespan impl.
 *
 * @author Matt Coley
 * @see Lifespan
 */
public class LifespanImpl extends AbstractPolicy implements Lifespan {
	private final Duration duration;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param duration
	 * 		Lifespan duration.
	 */
	public LifespanImpl(ServiceEnvironment environment, Duration duration) {
		super(environment);
		this.duration = duration;
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

	@Override
	public Lifespan withDuration(Duration duration) {
		return new LifespanImpl(getEnvironment(), duration);
	}

	@Override
	public Lifespan withDuration(long duration, TimeUnit unit) {
		return withDuration(getEnvironment().getSPI().newDuration(duration, unit));
	}
}
