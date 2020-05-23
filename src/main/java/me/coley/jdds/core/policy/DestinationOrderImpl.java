package me.coley.jdds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DestinationOrder;

/**
 * DestinationOrder impl.
 *
 * @author Matt Coley
 * @see DestinationOrder
 */
public class DestinationOrderImpl extends AbstractPolicy implements DestinationOrder {
	private final Kind kind;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param kind
	 * 		Order kind
	 */
	public DestinationOrderImpl(ServiceEnvironment environment, Kind kind) {
		super(environment);
		this.kind = kind;
	}

	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public DestinationOrder withKind(Kind kind) {
		return new DestinationOrderImpl(getEnvironment(), kind);
	}

	@Override
	public DestinationOrder withReceptionTimestamp() {
		return withKind(Kind.BY_RECEPTION_TIMESTAMP);
	}

	@Override
	public DestinationOrder withSourceTimestamp() {
		return withKind(Kind.BY_SOURCE_TIMESTAMP);
	}

	@Override
	public Comparable<DestinationOrder> requestedOfferedContract() {
		return this;
	}

	@Override
	public int compareTo(DestinationOrder o) {
		return getKind().compareTo(o.getKind());
	}
}
