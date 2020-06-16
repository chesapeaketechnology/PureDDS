package me.coley.puredds.core.status;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.LivelinessChangedStatus;

/**
 * LivelinessChangedStatus impl.
 *
 * @author Matt Coley
 * @see LivelinessChangedStatus
 */
public class LivelinessChangedStatusImpl extends LivelinessChangedStatus {
	private final ServiceEnvironment environment;
	private final int aliveCount;
	private final int aliveCountDx;
	private final int deadCount;
	private final int deadCountDx;
	private final InstanceHandle lastPublicationHandle;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public LivelinessChangedStatusImpl(LivelinessChangedStatus status) {
		this(status.getEnvironment(), status.getAliveCount(), status.getNotAliveCount(),
				status.getAliveCountChange(), status.getNotAliveCountChange(), status.getLastPublicationHandle());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param aliveCount
	 * 		Number of alive {@link org.omg.dds.pub.DataWriter} instances.
	 * @param deadCount
	 * 		Number of dead {@link org.omg.dds.pub.DataWriter} instances.
	 * @param aliveCountDx
	 * 		Difference from last status read.
	 * @param deadCountDx
	 * 		Difference from last status read.
	 * @param lastPublicationHandle
	 * 		Handle to the {@link org.omg.dds.pub.DataWriter} that caused the status change.
	 */
	public LivelinessChangedStatusImpl(ServiceEnvironment environment,
									   int aliveCount,
									   int deadCount,
									   int aliveCountDx,
									   int deadCountDx,
									   InstanceHandle lastPublicationHandle) {
		this.environment = environment;
		this.aliveCount = aliveCount;
		this.deadCount = deadCount;
		this.aliveCountDx = aliveCountDx;
		this.deadCountDx = deadCountDx;
		this.lastPublicationHandle = lastPublicationHandle;
	}

	@Override
	public int getAliveCount() {
		return aliveCount;
	}

	@Override
	public int getNotAliveCount() {
		return deadCount;
	}

	@Override
	public int getAliveCountChange() {
		return aliveCountDx;
	}

	@Override
	public int getNotAliveCountChange() {
		return deadCountDx;
	}

	@Override
	public InstanceHandle getLastPublicationHandle() {
		return lastPublicationHandle;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
