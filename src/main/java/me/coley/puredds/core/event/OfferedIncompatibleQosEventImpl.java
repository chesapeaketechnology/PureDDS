package me.coley.puredds.core.event;

import me.coley.puredds.core.status.OfferedIncompatibleQosStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.OfferedIncompatibleQosEvent;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.pub.DataWriter;

/**
 * OfferedIncompatibleQosEvent impl.
 *
 * @param <T>
 *        {@link DataWriter} data type.
 *
 * @author Matt Coley
 * @see OfferedIncompatibleQosEvent
 */
public class OfferedIncompatibleQosEventImpl<T> extends OfferedIncompatibleQosEvent<T> {
	private final ServiceEnvironment environment;
	private final OfferedIncompatibleQosStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public OfferedIncompatibleQosEventImpl(ServiceEnvironment environment, DataWriter<T> source,
										   OfferedIncompatibleQosStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public OfferedIncompatibleQosStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataWriter<T> getSource() {
		return (DataWriter<T>) source;
	}

	@Override
	public OfferedIncompatibleQosEvent<T> clone() {
		return new OfferedIncompatibleQosEventImpl<>(getEnvironment(), getSource(),
				new OfferedIncompatibleQosStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
