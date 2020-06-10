package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;

/**
 * TypeConsistencyEnforcement impl.
 *
 * @author Matt Coley
 * @see TypeConsistencyEnforcement
 */
public class TypeConsistencyEnforcementImpl extends AbstractPolicy implements TypeConsistencyEnforcement {
	private final Kind kind;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param kind
	 * 		Consistency enforcement kind.
	 */
	public TypeConsistencyEnforcementImpl(ServiceEnvironment environment, Kind kind) {
		super(environment);
		this.kind = kind;
	}

	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public TypeConsistencyEnforcement withKind(Kind kind) {
		return new TypeConsistencyEnforcementImpl(getEnvironment(), kind);
	}

	@Override
	public TypeConsistencyEnforcement withExactTypeTypeConsistency() {
		return withKind(Kind.EXACT_TYPE_TYPE_CONSISTENCY);
	}

	@Override
	public TypeConsistencyEnforcement withExactNameTypeConsistency() {
		return withKind(Kind.EXACT_NAME_TYPE_CONSISTENCY);
	}

	@Override
	public TypeConsistencyEnforcement withDeclaredTypeConsistency() {
		return withKind(Kind.DECLARED_TYPE_CONSISTENCY);
	}

	@Override
	public TypeConsistencyEnforcement withAssignableTypeConsistency() {
		return withKind(Kind.ASSIGNABLE_TYPE_CONSISTENCY);
	}

	@Override
	public Comparable<TypeConsistencyEnforcement> requestedOfferedContract() {
		return this;
	}

	@Override
	public int compareTo(TypeConsistencyEnforcement o) {
		return getKind().compareTo(o.getKind());
	}
}
