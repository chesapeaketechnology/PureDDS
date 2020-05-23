package me.coley.jdds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Deadline;

import java.util.concurrent.TimeUnit;

/**
 * Deadline impl.
 *
 * @author Matt Coley
 * @see Deadline
 */
public class DeadlineImpl extends AbstractPolicy implements Deadline {
	private final Duration period;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param period
	 * 		Deadline period.
	 */
	public DeadlineImpl(ServiceEnvironment environment, Duration period) {
		super(environment);
		this.period = period;
	}

	@Override
	public Duration getPeriod() {
		return period;
	}

	@Override
	public Deadline withPeriod(Duration period) {
		return new DeadlineImpl(getEnvironment(), period);
	}

	@Override
	public Deadline withPeriod(long period, TimeUnit unit) {
		return new DeadlineImpl(getEnvironment(), getEnvironment().getSPI().newDuration(period, unit));
	}

	@Override
	public Comparable<Deadline> requestedOfferedContract() {
		return this;
	}

	@Override
	public int compareTo(Deadline o) {
		return getPeriod().compareTo(o.getPeriod());
	}
}
