package me.coley.jdds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Liveliness;

import java.util.concurrent.TimeUnit;

/**
 * Liveliness impl.
 *
 * @author Matt Coley
 * @see Liveliness
 */
public class LivelinessImpl extends AbstractPolicy implements Liveliness {
	private final Kind kind;
	private final Duration leaseDuration;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param kind
	 * 		Liveliness type.
	 * @param leaseDuration
	 * 		Duration of alive state before invalidation.
	 */
	public LivelinessImpl(ServiceEnvironment environment, Kind kind, Duration leaseDuration) {
		super(environment);
		this.kind = kind;
		this.leaseDuration = leaseDuration;
	}

	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public Duration getLeaseDuration() {
		return leaseDuration;
	}

	@Override
	public Liveliness withKind(Kind kind) {
		return new LivelinessImpl(getEnvironment(), kind, getLeaseDuration());
	}

	@Override
	public Liveliness withLeaseDuration(Duration leaseDuration) {
		return new LivelinessImpl(getEnvironment(), getKind(), leaseDuration);
	}

	@Override
	public Liveliness withLeaseDuration(long leaseDuration, TimeUnit unit) {
		return withLeaseDuration(getEnvironment().getSPI().newDuration(leaseDuration, unit));
	}

	@Override
	public Liveliness withAutomatic() {
		return withKind(Kind.AUTOMATIC);
	}

	@Override
	public Liveliness withManualByParticipant() {
		return withKind(Kind.MANUAL_BY_PARTICIPANT);
	}

	@Override
	public Liveliness withManualByTopic() {
		return withKind(Kind.MANUAL_BY_TOPIC);
	}

	@Override
	public Comparable<Liveliness> requestedOfferedContract() {
		return this;
	}

	@Override
	public int compareTo(Liveliness o) {
		return getKind().compareTo(o.getKind());
	}
}
