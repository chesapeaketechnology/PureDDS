package me.coley.puredds.core.datatype;

import me.coley.puredds.util.TimeUtil;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;

import java.util.concurrent.TimeUnit;

/**
 * An immutable duration of time.
 *
 * @author Matt Coley
 */
public class DurationImpl extends Duration {
	private final ServiceEnvironment environment;
	private final long nanoDuration;

	/**
	 * Create a duration with the given length.
	 *
	 * @param environment
	 * 		Context environment.
	 * @param duration
	 * 		Time to use.
	 * @param durationUnit
	 * 		Unit of time the given time is declared in.
	 */
	public DurationImpl(ServiceEnvironment environment, long duration, TimeUnit durationUnit) {
		this(environment, TimeUnit.NANOSECONDS.convert(duration, durationUnit));
	}

	/**
	 * Create a duration with the given length in nanoseconds.
	 *
	 * @param environment
	 * 		Context environment.
	 * @param nanoDuration
	 * 		Time to use in nanoseconds.
	 */
	public DurationImpl(ServiceEnvironment environment, long nanoDuration) {
		this.environment = environment;
		this.nanoDuration = nanoDuration;
	}

	@Override
	public long getDuration(TimeUnit inThisUnit) {
		if (isInfinite()) {
			return Long.MAX_VALUE;
		}
		return inThisUnit.convert(nanoDuration, TimeUnit.NANOSECONDS);
	}

	@Override
	public long getRemainder(TimeUnit primaryUnit, TimeUnit remainderUnit) {
		if (isInfinite()) {
			return Long.MAX_VALUE;
		}
		if (isZero()) {
			return 0;
		}
		return TimeUtil.toNanos(TimeUtil.remainder(nanoDuration, primaryUnit), remainderUnit);
	}

	@Override
	public boolean isZero() {
		return nanoDuration == 0;
	}

	@Override
	public boolean isInfinite() {
		return nanoDuration == Long.MAX_VALUE;
	}

	@Override
	public Duration add(long duration, TimeUnit unit) {
		return add(Duration.newDuration(duration, unit, getEnvironment()));
	}

	@Override
	public Duration add(Duration duration) {
		// Check if should return copy of self
		if (isInfinite() || duration.isZero()) {
			return new DurationImpl(getEnvironment(), nanoDuration);
		}
		return new DurationImpl(getEnvironment(),
				TimeUtil.add(nanoDuration, duration.getDuration(TimeUnit.NANOSECONDS)));
	}


	@Override
	public Duration subtract(long duration, TimeUnit unit) {
		return subtract(Duration.newDuration(duration, unit, getEnvironment()));
	}

	@Override
	public Duration subtract(Duration duration) {
		// Check if should return copy of self
		if (isInfinite() || duration.isZero()) {
			return new DurationImpl(getEnvironment(), nanoDuration);
		}
		// Out of bounds
		if (duration.getDuration(TimeUnit.NANOSECONDS) > nanoDuration) {
			return new DurationImpl(getEnvironment(), 0);
		}
		return new DurationImpl(getEnvironment(),
				TimeUtil.subtract(nanoDuration, duration.getDuration(TimeUnit.NANOSECONDS)));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public int compareTo(Duration other) {
		if (isInfinite() && other.isInfinite()) {
			return 0;
		} else if (isZero() && other.isZero()) {
			return 0;
		}
		long otherNanos = other.getDuration(TimeUnit.NANOSECONDS);
		return Long.compare(nanoDuration, otherNanos);
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		} else if (other instanceof Duration) {
			Duration otherTime = (Duration) other;
			return nanoDuration == otherTime.getDuration(TimeUnit.NANOSECONDS);
		}
		return false;
	}
}
