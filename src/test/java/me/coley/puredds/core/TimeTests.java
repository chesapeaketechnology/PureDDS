package me.coley.puredds.core;

import me.coley.puredds.core.datatype.ModifiableTimeImpl;
import org.junit.jupiter.api.Test;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.Time;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTests {
	private static final PureServiceEnvironment environment = new PureServiceEnvironment();

	@Test
	public void testSpecialDurations() {
		assertTrue(environment.getSPI().infiniteDuration().isInfinite());
		assertFalse(environment.getSPI().infiniteDuration().isZero());
		assertFalse(environment.getSPI().zeroDuration().isInfinite());
		assertTrue(environment.getSPI().zeroDuration().isZero());
	}

	@Test
	public void testInvalidTime() {
		Time time = environment.getSPI().invalidTime();
		assertFalse(time.isValid());
		assertFalse(time.modifiableCopy().isValid());
		assertEquals(-1, time.getTime(TimeUnit.NANOSECONDS));
	}

	@Test
	public void testConvertTimeUnit() {
		long base = 1;
		Time time = environment.getSPI().newTime(base, TimeUnit.SECONDS);
		assertEquals(base / 60, time.getTime(TimeUnit.MINUTES));
		assertEquals(base * 1000, time.getTime(TimeUnit.MILLISECONDS));
		assertEquals(base * 1000000, time.getTime(TimeUnit.MICROSECONDS));
		assertEquals(base * 1000000000, time.getTime(TimeUnit.NANOSECONDS));
	}

	@Test
	public void testCopyTime() {
		long base = 1;
		Time time = environment.getSPI().newTime(base, TimeUnit.SECONDS);
		assertEquals(time, time.modifiableCopy());
		assertEquals(time, copy(new ModifiableTimeImpl(environment, 0), t -> t.copyFrom(time)));
	}

	@Test
	public void testAddTime() {
		long base = 1;
		long diff = 1;
		long expectedDiffRatio = (base + diff) / base;
		ModifiableTime time = environment.getSPI().newTime(base, TimeUnit.SECONDS).modifiableCopy();
		// Add 0 seconds changes nothing
		assertEquals(time.getTime(TimeUnit.NANOSECONDS),
				copy(time, t -> t.add(0, TimeUnit.SECONDS)).getTime(TimeUnit.NANOSECONDS));
		// Add "diff" second changes by expected amount
		assertEquals(time.getTime(TimeUnit.NANOSECONDS) * expectedDiffRatio,
				copy(time, t -> t.add(diff, TimeUnit.SECONDS)).getTime(TimeUnit.NANOSECONDS));
		// Add "infinity" second changes to max value
		assertEquals(Long.MAX_VALUE,
				copy(time, t -> t.add(Long.MAX_VALUE, TimeUnit.NANOSECONDS)).getTime(TimeUnit.NANOSECONDS));
	}

	@Test
	public void testAddDuration() {
		long base = 1;
		long diff = 1;
		long expectedDiffRatio = (base + diff) / base;
		Duration duration = environment.getSPI().newDuration(base, TimeUnit.SECONDS);
		// Add 0 seconds changes nothing
		assertEquals(duration.getDuration(TimeUnit.NANOSECONDS),
				duration.add(0, TimeUnit.SECONDS).getDuration(TimeUnit.NANOSECONDS));
		// Add "diff" second changes by expected amount
		assertEquals(duration.getDuration(TimeUnit.NANOSECONDS) * expectedDiffRatio,
				duration.add(diff, TimeUnit.SECONDS).getDuration(TimeUnit.NANOSECONDS));
		// Add "infinity" second changes to max value
		assertEquals(Long.MAX_VALUE,
				duration.add(Long.MAX_VALUE, TimeUnit.NANOSECONDS).getDuration(TimeUnit.NANOSECONDS));
	}

	@Test
	public void testSubTime() {
		long base = 10;
		long diff = 5;
		long expectedDiffRatio = base / (base - diff);
		ModifiableTime time = environment.getSPI().newTime(base, TimeUnit.SECONDS).modifiableCopy();
		// Subtract 0 seconds changes nothing
		assertEquals(time.getTime(TimeUnit.NANOSECONDS),
				copy(time, t -> t.subtract(0, TimeUnit.SECONDS)).getTime(TimeUnit.NANOSECONDS));
		// Subtract "diff" second changes by expected amount
		assertEquals(time.getTime(TimeUnit.NANOSECONDS) / expectedDiffRatio,
				copy(time, t -> t.subtract(diff, TimeUnit.SECONDS)).getTime(TimeUnit.NANOSECONDS));
		// Subtract value >= base yields 0
		assertEquals(0,
				copy(time, t -> t.subtract(base + 1, TimeUnit.SECONDS)).getTime(TimeUnit.NANOSECONDS));
		// Subtract "infinity" throws exception
		assertThrows(IllegalArgumentException.class,
				() -> copy(time, t -> t.subtract(Long.MAX_VALUE, TimeUnit.NANOSECONDS)));
	}

	@Test
	public void testSubDuration() {
		long base = 10;
		long diff = 5;
		long expectedDiffRatio = base / (base - diff);
		Duration duration = environment.getSPI().newDuration(base, TimeUnit.SECONDS);
		// Subtract 0 seconds changes nothing
		assertEquals(duration.getDuration(TimeUnit.NANOSECONDS),
				duration.subtract(0, TimeUnit.SECONDS).getDuration(TimeUnit.NANOSECONDS));
		// Subtract "diff" second changes by expected amount
		assertEquals(duration.getDuration(TimeUnit.NANOSECONDS) / expectedDiffRatio,
				duration.subtract(diff, TimeUnit.SECONDS).getDuration(TimeUnit.NANOSECONDS));
		// Subtract value >= base yields 0
		assertTrue(duration.subtract(base + 1, TimeUnit.SECONDS).isZero());
	}

	private static ModifiableTime copy(Time source, Consumer<ModifiableTime> modifier) {
		ModifiableTime copy = source.modifiableCopy();
		modifier.accept(copy);
		return copy;
	}
}
