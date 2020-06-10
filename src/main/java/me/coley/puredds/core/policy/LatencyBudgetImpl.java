package me.coley.puredds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.LatencyBudget;

import java.util.concurrent.TimeUnit;

/**
 * LatencyBudget impl.
 *
 * @author Matt Coley
 * @see LatencyBudget
 */
public class LatencyBudgetImpl extends AbstractPolicy implements LatencyBudget {
	private final Duration duration;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param duration
	 * 		Latency duration.
	 */
	public LatencyBudgetImpl(ServiceEnvironment environment, Duration duration) {
		super(environment);
		this.duration = duration;
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

	@Override
	public LatencyBudget withDuration(Duration duration) {
		return new LatencyBudgetImpl(getEnvironment(), duration);
	}

	@Override
	public LatencyBudget withDuration(long duration, TimeUnit unit) {
		return withDuration(getEnvironment().getSPI().newDuration(duration, unit));
	}

	@Override
	public Comparable<LatencyBudget> requestedOfferedContract() {
		return this;
	}

	@Override
	public int compareTo(LatencyBudget o) {
		return getDuration().compareTo(o.getDuration());
	}
}
