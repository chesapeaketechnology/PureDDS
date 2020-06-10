package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Durability;

/**
 * Durability impl.
 *
 * @author Matt Coley
 * @see Durability
 */
public class DurabilityImpl extends AbstractPolicy implements Durability {
	private final Kind kind;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param kind
	 * 		Type of durability.
	 */
	public DurabilityImpl(ServiceEnvironment environment, Kind kind) {
		super(environment);
		this.kind = kind;
	}

	/**
	 * @return Type of durability.
	 *
	 * @see Durability.Kind
	 */
	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public Durability withKind(Kind kind) {
		return new DurabilityImpl(getEnvironment(), kind);
	}

	@Override
	public Durability withVolatile() {
		return withKind(Kind.VOLATILE);
	}

	@Override
	public Durability withTransientLocal() {
		return withKind(Kind.TRANSIENT_LOCAL);
	}

	@Override
	public Durability withTransient() {
		return withKind(Kind.TRANSIENT);
	}

	@Override
	public Durability withPersitent() {
		return withKind(Kind.PERSISTENT);
	}

	@Override
	public Comparable<Durability> requestedOfferedContract() {
		return this;
	}

	@Override
	public int compareTo(Durability o) {
		return getKind().compareTo(o.getKind());
	}
}
