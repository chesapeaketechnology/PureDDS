package me.coley.jdds.core.handle;

import me.coley.jdds.core.ServiceProviderImpl;
import org.omg.dds.core.Entity;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableInstanceHandle;
import org.omg.dds.core.ServiceEnvironment;

/**
 * Handle implementation pointing to an entity.
 *
 * @author Matt Coley
 */
public class InstanceHandleImpl extends ModifiableInstanceHandle implements IEntityHandle {
	private final ServiceProviderImpl spi;
	private Entity<?, ?> entity;

	/**
	 * Create empty handle.
	 *
	 * @param spi
	 * 		Spawning service that created the handle.
	 */
	public InstanceHandleImpl(ServiceProviderImpl spi) {
		this.spi = spi;
	}

	/**
	 * @param entity
	 * 		Entity to set.
	 *
	 * @return Handle instance with entity.
	 */
	public InstanceHandleImpl withEntity(Entity<?, ?> entity) {
		this.entity = entity;
		return this;
	}

	@Override
	public void copyFrom(InstanceHandle src) {
		// Copy entity
		if (src instanceof InstanceHandleImpl) {
			withEntity(((InstanceHandleImpl) src).getEntity());
		}
	}

	@Override
	public InstanceHandle immutableCopy() {
		return new ImmutableInstanceHandleImpl(spi, getEntity());
	}

	@Override
	public ModifiableInstanceHandle modifiableCopy() {
		return spi.newInstanceHandle().withEntity(getEntity());
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
		return false;
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
