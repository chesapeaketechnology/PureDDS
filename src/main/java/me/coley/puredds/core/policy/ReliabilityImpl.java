package me.coley.puredds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Reliability;

import java.util.concurrent.TimeUnit;

/**
 * Reliability impl.
 *
 * @author Matt Coley
 * @see Reliability
 */
public class ReliabilityImpl extends AbstractPolicy implements Reliability {
	private final Kind kind;
	private final Duration maxBlockingTime;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param kind
	 * 		Reliability kind.
	 * @param maxBlockingTime
	 * 		Max duration to block.
	 */
	public ReliabilityImpl(ServiceEnvironment environment, Kind kind, Duration maxBlockingTime) {
		super(environment);
		this.kind = kind;
		this.maxBlockingTime = maxBlockingTime;
	}

	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public Duration getMaxBlockingTime() {
		return maxBlockingTime;
	}

	@Override
	public Reliability withKind(Kind kind) {
		return new ReliabilityImpl(getEnvironment(), kind, getMaxBlockingTime());
	}

	@Override
	public Reliability withMaxBlockingTime(Duration maxBlockingTime) {
		return new ReliabilityImpl(getEnvironment(), getKind(), maxBlockingTime);
	}

	@Override
	public Reliability withMaxBlockingTime(long maxBlockingTime, TimeUnit unit) {
		return new ReliabilityImpl(getEnvironment(), getKind(),
				getEnvironment().getSPI().newDuration(maxBlockingTime, unit));
	}

	@Override
	public Reliability withBestEffort() {
		return withKind(Kind.BEST_EFFORT);
	}

	@Override
	public Reliability withReliable() {
		return withKind(Kind.RELIABLE);
	}

	@Override
	public int compareTo(Reliability o) {
		return getKind().compareTo(o.getKind());
	}

	@Override
	public Comparable<Reliability> requestedOfferedContract() {
		return this;
	}
}
