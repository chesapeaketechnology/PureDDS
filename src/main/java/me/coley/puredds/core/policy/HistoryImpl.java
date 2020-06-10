package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.History;

/**
 * History impl.
 *
 * @author Matt Coley
 * @see History
 */
public class HistoryImpl extends AbstractPolicy implements History {
	private final Kind kind;
	private final int depth;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param kind
	 * 		History kind.
	 * @param depth
	 * 		Depth of history.
	 */
	public HistoryImpl(ServiceEnvironment environment, Kind kind, int depth) {
		super(environment);
		this.kind = kind;
		this.depth = depth;
	}

	/**
	 * @return History kind.
	 *
	 * @see org.omg.dds.core.policy.History.Kind
	 */
	@Override
	public Kind getKind() {
		return kind;
	}

	/**
	 * Must be less than or equal to {@link org.omg.dds.core.policy.ResourceLimits#getMaxSamplesPerInstance()}.
	 *
	 * @return Depth of history.
	 */
	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public History withKind(Kind kind) {
		return new HistoryImpl(getEnvironment(), kind, getDepth());
	}

	@Override
	public History withDepth(int depth) {
		return new HistoryImpl(getEnvironment(), getKind(), depth);
	}

	@Override
	public History withKeepAll() {
		return withKind(Kind.KEEP_ALL);
	}

	@Override
	public History withKeepLast(int depth) {
		return new HistoryImpl(getEnvironment(), Kind.KEEP_LAST, depth);
	}
}
