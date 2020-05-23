package me.coley.jdds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Ownership;

/**
 * Ownership impl.
 *
 * @author Matt Coley
 * @see Ownership
 */
public class OwnershipImpl extends AbstractPolicy implements Ownership {
	private final Kind kind;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param kind
	 * 		Ownership kind.
	 */
	public OwnershipImpl(ServiceEnvironment environment, Kind kind) {
		super(environment);
		this.kind = kind;
	}

	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public Ownership withKind(Kind kind) {
		return new OwnershipImpl(getEnvironment(), kind);
	}

	@Override
	public Ownership withShared() {
		return withKind(Kind.SHARED);
	}

	@Override
	public Ownership withExclusive() {
		return withKind(Kind.EXCLUSIVE);
	}

	@Override
	public Comparable<Ownership> requestedOfferedContract() {
		return this;
	}

	@Override
	public int compareTo(Ownership o) {
		return getKind().compareTo(o.getKind());
	}
}
