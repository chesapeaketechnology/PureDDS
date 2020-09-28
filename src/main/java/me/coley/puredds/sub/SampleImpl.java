package me.coley.puredds.sub;

import org.omg.dds.core.ModifiableInstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.ViewState;

/**
 * Sample impl.
 *
 * @param <T>
 * 		Topic data type.
 *
 * @author Matt Coley
 */
public class SampleImpl<T> implements Sample<T> {
	private final ServiceEnvironment environment;
	private final SampleState sampleState;
	private final ViewState viewState;
	private final InstanceState instanceState;
	private final Time srcTime;
	private final ModifiableInstanceHandle handle;
	private final ModifiableInstanceHandle publicationHandle;
	private final int numDisposed;
	private final int numNoWrters;
	private final int sampleRank;
	private final int generationRank;
	private final int absGenerationRank;
	private final T data;

	/**
	 * Create a sample.
	 *
	 * @param environment
	 * 		Environment context.
	 */
	public SampleImpl(ServiceEnvironment environment) {
		this.environment = environment;
		// TODO: How to collect this info and pass it effectively?
		//  - This will require laying out how the topic data types (T) are actually read
		sampleState = null;
		viewState = null;
		instanceState = null;
		srcTime = null;
		handle = null;
		publicationHandle = null;
		numDisposed = 0;
		numNoWrters = 0;
		sampleRank = 0;
		generationRank = 0;
		absGenerationRank = 0;
		data = null;
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public SampleState getSampleState() {
		return sampleState;
	}

	@Override
	public ViewState getViewState() {
		return viewState;
	}

	@Override
	public InstanceState getInstanceState() {
		return instanceState;
	}

	@Override
	public Time getSourceTimestamp() {
		return srcTime;
	}

	@Override
	public ModifiableInstanceHandle getInstanceHandle() {
		return handle;
	}

	@Override
	public ModifiableInstanceHandle getPublicationHandle() {
		return publicationHandle;
	}

	@Override
	public int getDisposedGenerationCount() {
		return numDisposed;
	}

	@Override
	public int getNoWritersGenerationCount() {
		return numNoWrters;
	}

	@Override
	public int getSampleRank() {
		return sampleRank;
	}

	@Override
	public int getGenerationRank() {
		return generationRank;
	}

	@Override
	public int getAbsoluteGenerationRank() {
		return absGenerationRank;
	}

	@Override
	public Sample<T> clone() {
		return new SampleImpl<>(getEnvironment());
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
