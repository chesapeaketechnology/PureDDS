package me.coley.puredds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.TimeBasedFilter;

import java.util.concurrent.TimeUnit;

/**
 * TimeBasedFilter impl.
 *
 * @author Matt Coley
 * @see TimeBasedFilter
 */
public class TimeBasedFilterImpl extends AbstractPolicy implements TimeBasedFilter {
	private final Duration minimumSeparation;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param minimumSeparation
	 * 		Minimum separation between read messages.
	 */
	public TimeBasedFilterImpl(ServiceEnvironment environment, Duration minimumSeparation) {
		super(environment);
		this.minimumSeparation = minimumSeparation;
	}

	@Override
	public Duration getMinimumSeparation() {
		return minimumSeparation;
	}

	@Override
	public TimeBasedFilter withMinimumSeparation(Duration minimumSeparation) {
		return new TimeBasedFilterImpl(getEnvironment(), minimumSeparation);
	}

	@Override
	public TimeBasedFilter withMinimumSeparation(long minimumSeparation, TimeUnit unit) {
		return withMinimumSeparation(getEnvironment().getSPI().newDuration(minimumSeparation, unit));
	}
}
