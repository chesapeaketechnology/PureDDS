package me.coley.jdds.core.event;

import me.coley.jdds.core.status.InconsistentTopicStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.InconsistentTopicEvent;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.omg.dds.topic.Topic;

/**
 * InconsistentTopicEvent impl.
 *
 * @param <T>
 *        {@link Topic} data type.
 *
 * @author Matt Coley
 * @see InconsistentTopicEvent
 */
public class InconsistentTopicEventImpl<T> extends InconsistentTopicEvent<T> {
	private final ServiceEnvironment environment;
	private final InconsistentTopicStatus status;


	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public InconsistentTopicEventImpl(ServiceEnvironment environment, Topic<T> source, InconsistentTopicStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}


	@Override
	public InconsistentTopicStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Topic<T> getSource() {
		return (Topic<T>) source;
	}

	@Override
	public InconsistentTopicEvent<T> clone() {
		return new InconsistentTopicEventImpl<>(getEnvironment(), getSource(), new InconsistentTopicStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
