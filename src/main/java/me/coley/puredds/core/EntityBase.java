package me.coley.puredds.core;

import me.coley.puredds.core.condition.StatusConditionImpl;
import org.omg.dds.core.Entity;
import org.omg.dds.core.EntityQos;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.Status;

import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Common entity implementation logic.
 *
 * @param <E>
 * 		Entity type being implemented.
 * @param <L>
 * 		Listener type of entity.
 * @param <Q>
 * 		QoS type of entity.
 *
 * @author Matt Coley
 */
public abstract class EntityBase<E extends Entity<L, Q>, L extends EventListener, Q extends EntityQos<?>>
		implements Entity<L, Q> {
	private final InstanceHandle handle;
	private final ServiceEnvironment environment;
	private final Map<Class<? extends Status>, Status> statusMap = new HashMap<>();
	private final Set<Class<? extends Status>> activeStatuses = new HashSet<>();
	private final Set<Class<? extends Status>> inactiveStatuses = new HashSet<>();
	private final Set<Class<? extends Status>> dirtyStatuses = new HashSet<>();
	private Q qos;
	// TODO: Use listener
	private L listener;
	private Collection<Class<? extends Status>> listenerStatuses;
	// TODO: Use enabled/closed states
	private boolean enabled;
	private boolean closed;

	/**
	 * @param environment
	 * 		Environment context.
	 */
	public EntityBase(ServiceEnvironment environment) {
		this.environment = environment;
		this.handle = environment.getSPI().newInstanceHandle();
	}

	/**
	 * @param provider
	 * 		Provider instance.
	 *
	 * @return QoS type from provider associated with the current entity implementation type.
	 */
	protected abstract Q fetchProviderQos(QosProvider provider);

	@Override
	public void enable() {
		this.enabled = true;
	}

	@Override
	public void close() {
		this.enabled = false;
		this.closed = true;
	}

	/**
	 * @param matchingState
	 * 		Active state to filter by.
	 *
	 * @return All statuses matching the given state.
	 */
	public Collection<? extends Status> getStatuses(boolean matchingState) {
		if (matchingState) {
			return activeStatuses.stream().map(statusMap::get).collect(Collectors.toSet());
		} else {
			return inactiveStatuses.stream().map(statusMap::get).collect(Collectors.toSet());
		}
	}

	/**
	 * Registers a status in the entity, initially assuming the status is inactive.
	 *
	 * @param status
	 * 		Status to add to the entity.
	 */
	public void registerStatus(Status status) {
		Class<? extends Status> key = getStatusClass(status);
		statusMap.put(key, status);
		inactiveStatuses.add(key);
	}

	/**
	 * @param statusClass
	 * 		Status class key.
	 *
	 * @return {@code true} when the status is active.
	 */
	public boolean getStatusState(Class<? extends Status> statusClass) {
		return activeStatuses.contains(getStatusClass(statusClass));
	}

	/**
	 * @param statusClass
	 * 		Status class key.
	 * @param state
	 * 		New state for the status.
	 */
	public void setStatusState(Class<? extends Status> statusClass, boolean state) {
		Class<? extends Status> key = getStatusClass(statusClass);
		// Update states by moving the class to-and-from
		boolean changed = false;
		if (state) {
			changed = inactiveStatuses.remove(key);
			activeStatuses.add(key);
		} else {
			changed = inactiveStatuses.add(key);
			activeStatuses.remove(key);
		}
		// Mark the status as being changed
		if (changed) {
			dirtyStatuses.add(key);
		}
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public StatusCondition<E> getStatusCondition() {
		// TODO: Reorganize generics so <> can be used
		return new StatusConditionImpl(getEnvironment(), this);
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		Set<Class<? extends Status>> copy = Collections.unmodifiableSet(dirtyStatuses);
		dirtyStatuses.clear();
		return copy;
	}

	@Override
	public L getListener() {
		return listener;
	}

	@Override
	public void setListener(L listener) {
		setListener(listener, null);
	}

	@Override
	public void setListener(L listener, Collection<Class<? extends Status>> statuses) {
		this.listener = listener;
		this.listenerStatuses = statuses;
	}

	@Override
	public Q getQos() {
		return qos;
	}

	@Override
	public void setQos(Q qos) {
		this.qos = qos;
	}

	@Override
	public void setQos(String qosLibraryName, String qosProfileName) {
		QosProvider provider = getEnvironment().getSPI().newQosProvider(qosLibraryName, qosProfileName);
		if (provider != null) {
			setQos(fetchProviderQos(provider));
		}
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		return handle;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}

	/**
	 * @return Active state of the entity.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return Closed state of the entity.
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * @param status
	 * 		Status implementation instance.
	 *
	 * @return DDS specification class instance.
	 */
	public static Class<? extends Status> getStatusClass(Status status) {
		return getStatusClass(status.getClass());
	}

	/**
	 * @param cls
	 * 		Status class.
	 *
	 * @return DDS specification class instance.
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends Status> getStatusClass(Class<? extends Status> cls) {
		while (!cls.getName().startsWith("org.omg.dds.core.status"))
			cls = (Class<? extends Status>) cls.getSuperclass();
		return cls;
	}
}
