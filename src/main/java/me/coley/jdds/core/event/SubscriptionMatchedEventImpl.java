package me.coley.jdds.core.event;

import me.coley.jdds.core.status.SubscriptionMatchedStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.SubscriptionMatchedEvent;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.sub.DataReader;

/**
 * SubscriptionMatchedEvent impl.
 *
 * @param <T>
 *        {@link DataReader} data type.
 *
 * @author Matt Coley
 * @see SubscriptionMatchedEvent
 */
public class SubscriptionMatchedEventImpl<T> extends SubscriptionMatchedEvent<T> {
	private final ServiceEnvironment environment;
	private final SubscriptionMatchedStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public SubscriptionMatchedEventImpl(ServiceEnvironment environment, DataReader<T> source,
										SubscriptionMatchedStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public SubscriptionMatchedStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataReader<T> getSource() {
		return (DataReader<T>) source;
	}

	@Override
	public SubscriptionMatchedEvent<T> clone() {
		return new SubscriptionMatchedEventImpl<>(getEnvironment(), getSource(),
				new SubscriptionMatchedStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
