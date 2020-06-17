package me.coley.puredds.sub;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.ViewState;

import java.util.Set;

/**
 * ReadCondition impl.
 *
 * @param <T>
 * 		Topic data type.
 *
 * @author Matt Coley
 */
public class ReadConditionImpl<T> implements ReadCondition<T> {
	private final ServiceEnvironment environment;
	private final DataReader<T> parent;
	private final Subscriber.DataState states;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param parent
	 * 		DataReader context.
	 * @param states
	 * 		Data states accessor.
	 */
	public ReadConditionImpl(ServiceEnvironment environment, DataReader<T> parent, Subscriber.DataState states) {
		this.environment = environment;
		this.parent = parent;
		this.states = states;
	}

	@Override
	public void close() {
		// TODO: What needs validated here? What needs to be closed?
	}

	@Override
	public boolean getTriggerValue() {
		// TODO: How to determine trigger value?
		return false;
	}

	@Override
	public Set<SampleState> getSampleStates() {
		return states.getSampleStates();
	}

	@Override
	public Set<ViewState> getViewStates() {
		return states.getViewStates();
	}

	@Override
	public Set<InstanceState> getInstanceStates() {
		return states.getInstanceStates();
	}

	@Override
	public DataReader<T> getParent() {
		return parent;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
