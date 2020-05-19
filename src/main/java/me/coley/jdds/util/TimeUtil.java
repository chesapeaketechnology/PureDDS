package me.coley.jdds.util;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.System.nanoTime;

/**
 * Utilities pertaining to time.
 *
 * @author Matt Coley
 */
public class TimeUtil {
	/**
	 * Nanosecond accurate clock.
	 */
	public static final NanoClock NANO_CLOCK = NanoClock.get();

	/**
	 * @return Nanosecond accurate instant.
	 */
	public static Instant nanoInstant() {
		return NANO_CLOCK.instant();
	}

	/**
	 * Convert another time to nanoseconds.
	 *
	 * @param time
	 * 		Some time.
	 * @param inputUnit
	 * 		Input time units.
	 *
	 * @return Nanosecond representation of time.
	 * {@code -1} when the time is invalid.
	 * {@link Long#MAX_VALUE} when the given time is out of bounds.
	 */
	public static long toNanos(long time, TimeUnit inputUnit) {
		if (time == -1) {
			// Invalid times must remain invalid
			return -1;
		} else if (inputUnit == TimeUnit.NANOSECONDS) {
			// No modification needed
			return time;
		} else {
			// Convert to nano-seconds if bounds are ok
			return time == Long.MAX_VALUE ? Long.MAX_VALUE : inputUnit.convert(time, TimeUnit.NANOSECONDS);
		}
	}

	/**
	 * Fetch the remainder in nanoseconds of a time when converting it to another time unit.
	 *
	 * @param time
	 * 		Input time in nanoseconds.
	 * @param targetUnit
	 * 		Target time unit to apply to time.
	 *
	 * @return Remainder of time in nanoseconds when changing from nanoseconds to the given unit.
	 * {@link Long#MAX_VALUE} when the given time is out of bounds.
	 */
	public static long remainder(long time, TimeUnit targetUnit) {
		if (time == -1) {
			// Invalid times must remain invalid
			return -1;
		} else if (time == Long.MAX_VALUE) {
			// Out if bounds times stay out of bounds
			return Long.MAX_VALUE;
		} else {
			// Find difference
			long timeInNanos = toNanos(time, targetUnit);
			long timeInTarget = TimeUnit.NANOSECONDS.convert(timeInNanos, targetUnit);
			return time - timeInTarget;
		}
	}

	/**
	 * Add two nanosecond times.
	 *
	 * @param current
	 * 		Current time.
	 * @param diff
	 * 		Time to add.
	 *
	 * @return Updated time.
	 * {@link Long#MAX_VALUE} when the given time is out of bounds.
	 */
	public static long add(long current, long diff) {
		// Out of bounds times
		if (current == Long.MAX_VALUE || diff == Long.MAX_VALUE) {
			return Long.MAX_VALUE;
		}
		// add
		long max = Math.max(current, diff);
		long min = Math.min(current, diff);
		if (max > 0 && min > 0 && min > Long.MAX_VALUE - max) {
			// Positive out of bounds check
			return Long.MAX_VALUE;
		} else if (max < 0 && min < -Long.MAX_VALUE - max) {
			// Negative out of bounds check
			return -Long.MAX_VALUE;
		} else {
			// Normal addition
			return current + diff;
		}
	}

	/**
	 * Subtract two nanosecond times.
	 *
	 * @param current
	 * 		Current time.
	 * @param diff
	 * 		Time to subtract.
	 *
	 * @return Updated time.
	 * {@link Long#MAX_VALUE} when the given time is out of bounds.
	 * Negative {@link Long#MAX_VALUE} when the given time is out of bounds in the negative direction.
	 */
	public static long subtract(long current, long diff) {
		// Out of bounds times
		if (current == Long.MAX_VALUE || diff == Long.MAX_VALUE) {
			return Long.MAX_VALUE;
		}
		// subtract
		long max = Math.max(current, diff);
		long min = Math.min(current, diff);
		if (max > 0L && min > 0L && min > Long.MAX_VALUE - max) {
			return Long.MAX_VALUE;
		} else if (max < 0L && -Long.MAX_VALUE - max > min) {
			return -Long.MAX_VALUE;
		} else {
			// Normal subtraction
			return current - diff;
		}
	}

	/**
	 * A clock extension that tracks accuracy to the nanosecond.
	 *
	 * @author <a href="https://stackoverflow.com/a/38658066/">Lorenzo Gallucci</a>
	 * @author Matt Coley
	 */
	static class NanoClock extends Clock {
		private static final Map<ZoneId, NanoClock> INSTANCES = new HashMap<>();
		private final long initialNanos = nanoTime();
		private final Clock clock;
		private final Instant initialInstant;

		private NanoClock(Clock clock) {
			this.clock = clock;
			initialInstant = clock.instant();
		}

		@Override
		public ZoneId getZone() {
			return clock.getZone();
		}

		@Override
		public Instant instant() {
			return initialInstant.plusNanos(nanoTime() - initialNanos);
		}

		@Override
		public Clock withZone(final ZoneId zone) {
			return INSTANCES.computeIfAbsent(zone, z -> new NanoClock(system(zone)));
		}

		/**
		 * @return Nanosecond accurate clock instance.
		 */
		static NanoClock get() {
			return INSTANCES.computeIfAbsent(ZoneId.systemDefault(), z -> new NanoClock(system(z)));
		}
	}
}
