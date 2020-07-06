package me.coley.puredds.util;

import me.coley.puredds.core.event.DataAvailableEventImpl;
import me.coley.puredds.core.event.DataOnReadersEventImpl;
import me.coley.puredds.core.event.InconsistentTopicEventImpl;
import me.coley.puredds.core.event.LivelinessChangedEventImpl;
import me.coley.puredds.core.event.LivelinessLostEventImpl;
import me.coley.puredds.core.event.OfferedDeadlineMissedEventImpl;
import me.coley.puredds.core.event.OfferedIncompatibleQosEventImpl;
import me.coley.puredds.core.event.PublicationMatchedEventImpl;
import me.coley.puredds.core.event.RequestedDeadlineMissedEventImpl;
import me.coley.puredds.core.event.RequestedIncompatibleQosEventImpl;
import me.coley.puredds.core.event.SampleLostEventImpl;
import me.coley.puredds.core.event.SampleRejectedEventImpl;
import me.coley.puredds.core.event.SubscriptionMatchedEventImpl;
import me.coley.puredds.core.policy.AbstractPolicy;
import me.coley.puredds.core.policy.DataRepresentationImpl;
import me.coley.puredds.core.policy.DeadlineImpl;
import me.coley.puredds.core.policy.DestinationOrderImpl;
import me.coley.puredds.core.policy.DurabilityImpl;
import me.coley.puredds.core.policy.DurabilityServiceImpl;
import me.coley.puredds.core.policy.EntityFactoryImpl;
import me.coley.puredds.core.policy.GroupDataImpl;
import me.coley.puredds.core.policy.HistoryImpl;
import me.coley.puredds.core.policy.LatencyBudgetImpl;
import me.coley.puredds.core.policy.LifespanImpl;
import me.coley.puredds.core.policy.LivelinessImpl;
import me.coley.puredds.core.policy.OwnershipImpl;
import me.coley.puredds.core.policy.OwnershipStrengthImpl;
import me.coley.puredds.core.policy.PartitionImpl;
import me.coley.puredds.core.policy.QosPolicyCountImpl;
import me.coley.puredds.core.policy.ReaderDataLifecycleImpl;
import me.coley.puredds.core.policy.ReliabilityImpl;
import me.coley.puredds.core.policy.ResourceLimitsImpl;
import me.coley.puredds.core.policy.TimeBasedFilterImpl;
import me.coley.puredds.core.policy.TopicDataImpl;
import me.coley.puredds.core.policy.TransportPriorityImpl;
import me.coley.puredds.core.policy.TypeConsistencyEnforcementImpl;
import me.coley.puredds.core.policy.UserDataImpl;
import me.coley.puredds.core.policy.WriterDataLifecycleImpl;
import me.coley.puredds.core.status.DataAvailableStatusImpl;
import me.coley.puredds.core.status.DataOnReaderStatusImpl;
import me.coley.puredds.core.status.InconsistentTopicStatusImpl;
import me.coley.puredds.core.status.LivelinessChangedStatusImpl;
import me.coley.puredds.core.status.LivelinessLostStatusImpl;
import me.coley.puredds.core.status.OfferedDeadlineMissedStatusImpl;
import me.coley.puredds.core.status.OfferedIncompatibleQosStatusImpl;
import me.coley.puredds.core.status.PublicationMatchedStatusImpl;
import me.coley.puredds.core.status.RequestedDeadlineMissedStatusImpl;
import me.coley.puredds.core.status.RequestedIncompatibleQosStatusImpl;
import me.coley.puredds.core.status.SampleLostStatusImpl;
import me.coley.puredds.core.status.SampleRejectedStatusImpl;
import me.coley.puredds.core.status.SubscriptionMatchedStatusImpl;
import org.omg.dds.core.status.Status;

import java.util.Collection;
import java.util.EventObject;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableCollection;

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

	private static final Collection<Class<? extends AbstractPolicy>> ALL_POLICIES = unmodifiableCollection(asList(
			DataRepresentationImpl.class,
			DeadlineImpl.class,
			DestinationOrderImpl.class,
			DurabilityImpl.class,
			DurabilityServiceImpl.class,
			EntityFactoryImpl.class,
			GroupDataImpl.class,
			HistoryImpl.class,
			LatencyBudgetImpl.class,
			LifespanImpl.class,
			LivelinessImpl.class,
			OwnershipImpl.class,
			OwnershipStrengthImpl.class,
			PartitionImpl.class,
			QosPolicyCountImpl.class,
			ReaderDataLifecycleImpl.class,
			ReliabilityImpl.class,
			ResourceLimitsImpl.class,
			TimeBasedFilterImpl.class,
			TopicDataImpl.class,
			TransportPriorityImpl.class,
			TypeConsistencyEnforcementImpl.class,
			UserDataImpl.class,
			WriterDataLifecycleImpl.class
	));

	private static final Collection<Class<? extends EventObject>> ALL_EVENTS = unmodifiableCollection(asList(
			DataAvailableEventImpl.class,
			DataOnReadersEventImpl.class,
			InconsistentTopicEventImpl.class,
			LivelinessChangedEventImpl.class,
			LivelinessLostEventImpl.class,
			OfferedDeadlineMissedEventImpl.class,
			OfferedIncompatibleQosEventImpl.class,
			PublicationMatchedEventImpl.class,
			RequestedDeadlineMissedEventImpl.class,
			RequestedIncompatibleQosEventImpl.class,
			SampleLostEventImpl.class,
			SampleRejectedEventImpl.class,
			SubscriptionMatchedEventImpl.class
	));

	/**
	 * @return All implementations of status classes.
	 */
	public static Collection<Class<? extends Status>> getAllStatuses() {
		return ALL_STATUSES;
	}

	/**
	 * @return All implementations of policy classes.
	 */
	public static Collection<Class<? extends AbstractPolicy>> getAllPolicies() {
		return ALL_POLICIES;
	}

	/**
	 * @return All implementations of event classes.
	 */
	public static Collection<Class<? extends EventObject>> getAllEvents() {
		return ALL_EVENTS;
	}
}
