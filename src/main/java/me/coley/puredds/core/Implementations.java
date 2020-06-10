package me.coley.puredds.core;

import me.coley.puredds.core.status.*;
import org.omg.dds.core.status.Status;

import java.util.Collection;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Arrays.asList;

/**
 * Reference utility to all implementation classes of differing types.
 *
 * @author Matt Coley
 */
public class Implementations {
	private static final Collection<Class<? extends Status>> ALL_STATUSES = unmodifiableCollection(asList(
			DataAvailableStatusImpl.class,
			DataOnReaderStatusImpl.class,
			InconsistentTopicStatusImpl.class,
			LivelinessChangedStatusImpl.class,
			LivelinessLostStatusImpl.class,
			OfferedDeadlineMissedStatusImpl.class,
			OfferedIncompatibleQosStatusImpl.class,
			PublicationMatchedStatusImpl.class,
			RequestedDeadlineMissedStatusImpl.class,
			RequestedIncompatibleQosStatusImpl.class,
			SampleLostStatusImpl.class,
			SampleRejectedStatusImpl.class,
			SubscriptionMatchedStatusImpl.class
	));

	/**
	 * @return All implementations of status classes.
	 */
	public static Collection<Class<? extends Status>> getAllStatuses() {
		return ALL_STATUSES;
	}
}
