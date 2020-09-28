package me.coley.puredds.sub;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.ViewState;

import java.util.HashSet;
import java.util.Set;

/**
 * DataState impl.
 *
 * @author Matt Coley
 */
public class DataStateImpl implements Subscriber.DataState {
	private final ServiceEnvironment environment;
	private final Set<SampleState> sampleStates = new HashSet<>();
	private final Set<ViewState> viewStates = new HashSet<>();
	private final Set<InstanceState> instanceStates = new HashSet<>();

	/**
	 * Create empty data state.
	 *
	 * @param environment
	 * 		Environment context.
	 */
	public DataStateImpl(ServiceEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * Create data state.
	 *
	 * @param environment
	 * 		Environment context.
	 * @param sampleStates
	 * 		Sample states to use.
	 * @param viewStates
	 * 		View states to use.
	 * @param instanceStates
	 * 		Instance states to use.
	 */
	public DataStateImpl(ServiceEnvironment environment, Set<SampleState> sampleStates, Set<ViewState> viewStates,
						 Set<InstanceState> instanceStates) {
		this.environment = environment;
		this.sampleStates.addAll(sampleStates);
		this.viewStates.addAll(viewStates);
		this.instanceStates.addAll(instanceStates);
	}

	@Override
	public Set<SampleState> getSampleStates() {
		return sampleStates;
	}

	@Override
	public Set<ViewState> getViewStates() {
		return this.viewStates;
	}

	@Override
	public Set<InstanceState> getInstanceStates() {
		return this.instanceStates;
	}

	@Override
	public Subscriber.DataState with(SampleState state) {
		DataStateImpl copy = (DataStateImpl) clone();
		copy.sampleStates.add(state);
		return copy;
	}

	@Override
	public Subscriber.DataState with(ViewState state) {
		DataStateImpl copy = (DataStateImpl) clone();
		copy.viewStates.add(state);
		return copy;
	}

	@Override
	public Subscriber.DataState with(InstanceState state) {
		DataStateImpl copy = (DataStateImpl) clone();
		copy.instanceStates.add(state);
		return copy;
	}

	@Override
	public Subscriber.DataState withAnySampleState() {
		return clone()
				.with(SampleState.NOT_READ)
				.with(SampleState.READ);
	}

	@Override
	public Subscriber.DataState withAnyViewState() {
		return clone()
				.with(ViewState.NEW)
				.with(ViewState.NOT_NEW);
	}

	@Override
	public Subscriber.DataState withAnyInstanceState() {
		return clone()
				.with(InstanceState.ALIVE)
				.with(InstanceState.NOT_ALIVE_NO_WRITERS)
				.with(InstanceState.NOT_ALIVE_DISPOSED);
	}

	@Override
	public Subscriber.DataState withNotAliveInstanceStates() {
		return clone()
				.with(InstanceState.NOT_ALIVE_NO_WRITERS)
				.with(InstanceState.NOT_ALIVE_DISPOSED);
	}

	@Override
	public Subscriber.DataState clone() {
		return new DataStateImpl(getEnvironment(), getSampleStates(), getViewStates(), getInstanceStates());
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
