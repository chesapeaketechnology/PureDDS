package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Partition;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Partition impl.
 *
 * @author Matt Coley
 * @see Partition
 */
public class PartitionImpl extends AbstractPolicy implements Partition {
	private final Set<String> name;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param name
	 * 		Partition name.
	 */
	public PartitionImpl(ServiceEnvironment environment, Set<String> name) {
		super(environment);
		this.name = name;
	}

	@Override
	public Set<String> getName() {
		return name;
	}

	@Override
	public Partition withName(Collection<String> name) {
		return new PartitionImpl(getEnvironment(), new HashSet<>(name));
	}
}
