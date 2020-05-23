package me.coley.jdds.core.handle;

import me.coley.jdds.core.ServiceProviderImpl;
import org.omg.dds.core.Entity;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableInstanceHandle;
import org.omg.dds.core.ServiceEnvironment;

/**
 * Handle implementation pointing to an entity.
 * Entity cannot be changed.
 *
 * @author Matt Coley
 */
public final class ImmutableInstanceHandleImpl extends InstanceHandle implements IEntityHandle {
	private final Entity<?, ?> entity;
	private final ServiceProviderImpl spi;

	/**
	 * Create empty handle.
	 *
	 * @param spi
	 * 		Spawning service that created the handle.
	 * @param entity
	 * 		Entity to point to.
	 */
	public ImmutableInstanceHandleImpl(ServiceProviderImpl spi, Entity<?, ?> entity) {
		this.spi = spi;
		this.entity = entity;
	}

	@Override
	public ModifiableInstanceHandle modifiableCopy() {
		return null;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return spi.getEnvironment();
	}

	@Override
	public Entity<?, ?> getEntity() {
		return entity;
	}

	@Override
	public boolean isNil() {
		return entity == null;
	}

	@Override
	public int compareTo(InstanceHandle o) {
		// Check same type
		if (o instanceof IEntityHandle) {
			return compare(this, (IEntityHandle) o);
		}
		return -1;
	}

	@Override
	public int hashCode() {
		if (getEntity() == null)
			return 0;
		return getEntity().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IEntityHandle) {
			IEntityHandle otherHandle = (IEntityHandle) obj;
			if (getEntity() == null)
				return otherHandle.getEntity() == null;
			return getEntity().equals(otherHandle.getEntity());
		}
		return false;
	}
}
