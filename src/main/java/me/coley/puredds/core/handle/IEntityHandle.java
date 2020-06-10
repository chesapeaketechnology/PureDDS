package me.coley.puredds.core.handle;

import org.omg.dds.core.Entity;
import org.omg.dds.core.InstanceHandle;

import java.util.Comparator;

/**
 * Common logic between {@link ImmutableInstanceHandleImpl} and {@link InstanceHandleImpl}.
 *
 * @author Matt Coley
 */
public interface IEntityHandle extends Comparator<IEntityHandle> {
	/**
	 * @return Entity contained by the handle.
	 */
	Entity<?, ?> getEntity();

	/**
	 * @return See {@link InstanceHandle#isNil()}.
	 */
	boolean isNil();

	@Override
	default int compare(IEntityHandle o1, IEntityHandle o2) {
		// Handle nulls
		if (o1 == null || o2 == null) {
			return (o1 == o2) ? 0 : -1;
		}
		// Check both nil values
		if (o1.isNil() && o2.isNil()) {
			return 0;
		}
		// Check entity equality
		return o1.getEntity() == o2.getEntity() ? 0 : -1;
	}
}
