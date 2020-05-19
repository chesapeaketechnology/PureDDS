package me.coley.jdds.datatype;

import me.coley.jdds.JServiceEnvironment;
import me.coley.jdds.util.TimeUtil;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.Time;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * A mutable time object.
 *
 * @author Matt Coley
 */
public class JModifiableTime extends ModifiableTime {
	private final JServiceEnvironment environment;
	private long nanoTime;

	/**
	 * Create a modifiable time instance with the current time.
	 *
	 * @param environment
	 * 		Context environment.
	 */
	public JModifiableTime(JServiceEnvironment environment) {
		this(environment, TimeUtil.nanoInstant());
	}

	/**
	 * Create a modifiable time instance with the given time.
	 *
	 * @param environment
	 * 		Context environment.
	 * @param instant
	 * 		Time to use.
	 */
	public JModifiableTime(JServiceEnvironment environment, Instant instant) {
		this(environment, instant.getEpochSecond() + instant.getNano());
	}

	/**
	 * Create a modifiable time instance with the given time.
	 *
	 * @param environment
	 * 		Context environment.
	 * @param nanoTime
	 * 		Time to use in nanoseconds.
	 */
	public JModifiableTime(JServiceEnvironment environment, long nanoTime) {
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
		return new JModifiableTime(getEnvironment(), nanoTime);
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

	@Override
	public void copyFrom(Time src) {
		this.nanoTime = src.getTime(TimeUnit.NANOSECONDS);
	}

	@Override
	public Time immutableCopy() {
		return new JTime(getEnvironment(), nanoTime);
	}

	@Override
	public void setTime(long time, TimeUnit unit) {
		// Illegal bounds
		if (time < 0) {
			throw new IllegalArgumentException("Cannot set to negative time: " + time);
		}
		nanoTime = TimeUtil.toNanos(time, unit);
	}

	@Override
	public void add(long duration, TimeUnit unit) {
		add(Duration.newDuration(duration, unit, getEnvironment()));
	}

	@Override
	public void add(Duration duration) {
		if (duration.isInfinite()) {
			// Out of bounds
			nanoTime = Long.MAX_VALUE;
		} else {
			nanoTime = TimeUtil.add(nanoTime, duration.getDuration(TimeUnit.NANOSECONDS));
		}
	}

	@Override
	public void subtract(long duration, TimeUnit unit) {
		subtract(Duration.newDuration(duration, unit, getEnvironment()));
	}

	@Override
	public void subtract(Duration duration) {
		// Illegal bounds
		if (duration.isInfinite()) {
			throw new IllegalArgumentException("Cannot subtract infinite time");
		}
		long durationNanos = duration.getDuration(TimeUnit.NANOSECONDS);
		if (durationNanos > nanoTime) {
			// Out of bounds
			nanoTime = 0;
		} else {
			nanoTime = TimeUtil.subtract(nanoTime, durationNanos);
		}
	}
}
