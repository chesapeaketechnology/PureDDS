package me.coley.puredds.core.condition;

import me.coley.puredds.core.EntityBase;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.Status;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * StatusCondition impl.
 *
 * @param <E>
 * 		Type of entity the condition belongs to.
 *
 * @author Matt Coley
 */
public class StatusConditionImpl<E extends EntityBase<?, ?, ?>> implements StatusCondition<E> {
	private final Set<Class<? extends Status>> enabledStatuses = new HashSet<>();
	private final ServiceEnvironment environment;
	private final E entity;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param entity
	 * 		Entity with conditions.
	 */
	public StatusConditionImpl(ServiceEnvironment environment, E entity) {
		this.environment = environment;
		this.entity = entity;
	}

	@Override
	public boolean getTriggerValue() {
		// Iterate over all statuses
		for (Status status : entity.getStatusesOfState(true)) {
			Class<? extends Status> key = EntityBase.getStatusClass(status);
			// Skip statuses that the condition is not sensitive to.
			if (!enabledStatuses.contains(key)) {
				continue;
			}
			// Any true status yields a true trigger value
			return true;
		}
		// Only false when all statuses the condition is sensitive to are false.
		return false;
	}

	@Override
	public void setEnabledStatuses(Collection<Class<? extends Status>> statuses) {
		enabledStatuses.clear();
		enabledStatuses.addAll(statuses);
	}

	@Override
	public Set<Class<? extends Status>> getEnabledStatuses() {
		return Collections.unmodifiableSet(enabledStatuses);
	}

	@Override
	public E getParent() {
		return entity;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}

}
