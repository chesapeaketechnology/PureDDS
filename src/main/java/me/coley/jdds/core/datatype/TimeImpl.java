package me.coley.jdds.core.datatype;

import me.coley.jdds.core.JServiceEnvironment;
import me.coley.jdds.util.TimeUtil;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.Time;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * An immutable time object.
 *
 * @author Matt Coley
 */
public class TimeImpl extends Time {
	private final JServiceEnvironment environment;
	private final long nanoTime;

	/**
	 * Create a time instance with the current time.
	 *
	 * @param environment
	 * 		Context environment.
	 */
	public TimeImpl(JServiceEnvironment environment) {
		this(environment, TimeUtil.nanoInstant());
	}

	/**
	 * Create a time instance with the given time.
	 *
	 * @param environment
	 * 		Context environment.
	 * @param instant
	 * 		Time to use.
	 */
	public TimeImpl(JServiceEnvironment environment, Instant instant) {
		this(environment, instant.getEpochSecond() + instant.getNano());
	}

	/**
	 * Create a time instance with the given time.
	 *
	 * @param environment
	 * 		Context environment.
	 * @param nanoTime
	 * 		Time to use in nanoseconds.
	 */
	public TimeImpl(JServiceEnvironment environment, long nanoTime) {
		this.environment = environment;
		this.nanoTime = nanoTime;
	}

	@Override
	public long getTime(TimeUnit targetUnit) {
		return targetUnit.convert(nanoTime, TimeUnit.NANOSECONDS);
	}

	@Override
	public long getRemainder(TimeUnit primaryUnit, TimeUnit remainderUnit) {
		return TimeUtil.toNanos(TimeUtil.remainder(nanoTime, primaryUnit), remainderUnit);
	}

	@Override
	public boolean isValid() {
		return nanoTime != -1;
	}

	@Override
	public ModifiableTime modifiableCopy() {
		return new ModifiableTimeImpl(getEnvironment(), nanoTime);
	}

	@Override
	public JServiceEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public int compareTo(Time time) {
		return Long.compare(getTime(TimeUnit.NANOSECONDS), time.getTime(TimeUnit.NANOSECONDS));
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		} else if (other instanceof Time) {
			Time otherTime = (Time) other;
			// Check if both times are invalid
			if (!isValid() && !otherTime.isValid()) {
				return true;
			}
			return nanoTime == otherTime.getTime(TimeUnit.NANOSECONDS);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (isValid()) {
			return (int) getTime(TimeUnit.NANOSECONDS);
		}
		return 0;
	}
}
