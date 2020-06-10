package me.coley.puredds.core.condition;

import me.coley.puredds.util.PreconditionException;
import org.omg.dds.core.Condition;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.WaitSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * WaitSet implementation.
 *
 * @author Matt Coley
 */
public class WaitSetImpl extends WaitSet {
	private final ServiceEnvironment environment;
	private final Collection<Condition> conditions = new HashSet<>();
	private final Map<Long, List<?>> tmap = new HashMap<>();

	/**
	 * @param environment
	 * 		Environment context.
	 */
	public WaitSetImpl(ServiceEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public void waitForConditions() {
		waitForConditions(getConditionsWithState(true));
	}

	@Override
	public void waitForConditions(Collection<Condition> activeConditions) {
		try {
			waitForConditions(activeConditions, getEnvironment().getSPI().infiniteDuration());
		} catch (TimeoutException e) {
			// This is NOT going to happen with an infinite duration
		}
	}

	@Override
	public void waitForConditions(long timeout, TimeUnit unit) throws TimeoutException {
		waitForConditions(getEnvironment().getSPI().newDuration(timeout, unit));
	}

	@Override
	public void waitForConditions(Duration timeout) throws TimeoutException {
		waitForConditions(getConditionsWithState(true), timeout);
	}

	@Override
	public void waitForConditions(Collection<Condition> activeConditions, long timeout, TimeUnit unit)
			throws TimeoutException {
		waitForConditions(activeConditions, getEnvironment().getSPI().newDuration(timeout, unit));
	}

	@Override
	public void waitForConditions(Collection<Condition> activeConditions, Duration timeout) throws TimeoutException {
		// Check if all conditions have false state.
		// If all are false, we can stop.
		if (!activeConditions.isEmpty()) {
			return;
		}
		// Wait on the current thread for conditions to be satisfied (hopefully updated by other threads)
		long threadId = Thread.currentThread().getId();
		// Verify the calling thread is the expected thread
		if (tmap.containsKey(threadId)) {
			throw new PreconditionException(getEnvironment(), "Existing thread is already waiting on this WaitSet");
		}
		// Create a list and wait for somebody else to come along and populate it
		long startTimeMs = System.currentTimeMillis();
		long durationMs = timeout.getDuration(TimeUnit.MILLISECONDS);
		long endTimeMs = startTimeMs + durationMs;
		tmap.put(threadId, new ArrayList<>());
		while (tmap.get(threadId).isEmpty() && !Thread.interrupted()) {
			try {
				wait(durationMs);
			} catch (InterruptedException e) {
				// Expected
			}
			// Check break condition
			if (!timeout.isInfinite() && System.currentTimeMillis() > endTimeMs) {
				throw new TimeoutException();
			}
		}
		// DOCS: The docs say "the result of the wait operation is the list of..."
		//  but this is CLEARLY a void method... uhhh.....
		//  If there was a return, it would be "tmap.get(threadId)"
		tmap.remove(threadId);
	}

	@Override
	public void attachCondition(Condition cond) {
		// TODO: "throw OutOfResourcesException if attaching the condition requires the
		//        allocation of unavailable middleware or OS resources."
		conditions.add(cond);
	}

	@Override
	public void detachCondition(Condition cond) {
		if (!conditions.contains(cond)) {
			throw new PreconditionException(getEnvironment(),
					"Cannot detach condition that was not attached to WaitSet");
		}
		conditions.remove(cond);
	}

	@Override
	public Collection<Condition> getConditions() {
		return Collections.unmodifiableCollection(conditions);
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}

	/**
	 * @param triggerState
	 * 		Condition trigger state.
	 *
	 * @return All attached conditions matching the given state.
	 */
	public Collection<Condition> getConditionsWithState(boolean triggerState) {
		Collection<Condition> triggeredConditions = new HashSet<>(conditions.size());
		for (Condition condition : conditions) {
			if (condition.getTriggerValue() == triggerState) {
				triggeredConditions.add(condition);
			}
		}
		return Collections.unmodifiableCollection(triggeredConditions);
	}
}
