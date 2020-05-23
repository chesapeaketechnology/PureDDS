package me.coley.jdds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.ReaderDataLifecycle;

import java.util.concurrent.TimeUnit;

/**
 * ReaderDataLifecycle impl.
 *
 * @author Matt Coley
 * @see ReaderDataLifecycle
 */
public class ReaderDataLifecycleImpl extends AbstractPolicy implements ReaderDataLifecycle {
	private final Duration autoPurgeNoWriterSamplesDelay;
	private final Duration autoPurgeDisposedSamplesDelay;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param autoPurgeNoWriterSamplesDelay
	 * 		Delay before purging samples that are not being used in data writers.
	 * @param autoPurgeDisposedSamplesDelay
	 * 		Delay before purging disposed samples.
	 */
	public ReaderDataLifecycleImpl(ServiceEnvironment environment, Duration autoPurgeNoWriterSamplesDelay,
								   Duration autoPurgeDisposedSamplesDelay) {
		super(environment);
		this.autoPurgeNoWriterSamplesDelay = autoPurgeNoWriterSamplesDelay;
		this.autoPurgeDisposedSamplesDelay = autoPurgeDisposedSamplesDelay;
	}

	@Override
	public Duration getAutoPurgeNoWriterSamplesDelay() {
		return autoPurgeNoWriterSamplesDelay;
	}

	@Override
	public Duration getAutoPurgeDisposedSamplesDelay() {
		return autoPurgeDisposedSamplesDelay;
	}

	@Override
	public ReaderDataLifecycle withAutoPurgeNoWriterSamplesDelay(Duration autoPurgeNoWriterSamplesDelay) {
		return new ReaderDataLifecycleImpl(getEnvironment(),
				autoPurgeNoWriterSamplesDelay, getAutoPurgeDisposedSamplesDelay());
	}

	@Override
	public ReaderDataLifecycle withAutoPurgeNoWriterSamplesDelay(long autoPurgeNoWriterSamplesDelay, TimeUnit unit) {
		return withAutoPurgeNoWriterSamplesDelay(getEnvironment().getSPI()
				.newDuration(autoPurgeNoWriterSamplesDelay, unit));
	}

	@Override
	public ReaderDataLifecycle withAutoPurgeDisposedSamplesDelay(Duration autoPurgeDisposedSamplesDelay) {
		return new ReaderDataLifecycleImpl(getEnvironment(),
				getAutoPurgeNoWriterSamplesDelay(), autoPurgeDisposedSamplesDelay);
	}

	@Override
	public ReaderDataLifecycle withAutoPurgeDisposedSamplesDelay(long autoPurgeDisposedSamplesDelay, TimeUnit unit) {
		return withAutoPurgeDisposedSamplesDelay(getEnvironment().getSPI()
				.newDuration(autoPurgeDisposedSamplesDelay, unit));
	}
}
