package me.coley.jdds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Lifespan;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.OwnershipStrength;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.ReaderDataLifecycle;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TransportPriority;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.policy.WriterDataLifecycle;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static me.coley.jdds.util.MiscUtil.emptyBytes;

/**
 * Policy factory implementation.
 *
 * @author Matt Coley
 */
public class PolicyFactoryImpl extends PolicyFactory {
	private static final int DEFAULT_HISTORY_DEPTH = 1;
	private static final int DEFAULT_TRANSPORT_PRIORITY = 0;
	private static final int DEFAULT_OWNERSHIP_STRENGTH = 0;
	private static Duration defaultReliabilityDelay;
	private final ServiceEnvironment environment;

	/**
	 * @param environment
	 * 		Environment context.
	 */
	public PolicyFactoryImpl(ServiceEnvironment environment) {
		this.environment = environment;
		// TODO: Refactor these defaults into the configurator
		defaultReliabilityDelay = environment.getSPI().newDuration(100000000, TimeUnit.NANOSECONDS);
	}

	@Override
	public Durability Durability() {
		return new DurabilityImpl(getEnvironment(), Durability.Kind.VOLATILE);
	}

	@Override
	public Deadline Deadline() {
		return new DeadlineImpl(getEnvironment(), getEnvironment().getSPI().infiniteDuration());
	}

	@Override
	public LatencyBudget LatencyBudget() {
		return new LatencyBudgetImpl(getEnvironment(), getEnvironment().getSPI().zeroDuration());
	}

	@Override
	public Liveliness Liveliness() {
		return new LivelinessImpl(getEnvironment(), Liveliness.Kind.AUTOMATIC,
				getEnvironment().getSPI().infiniteDuration());
	}

	@Override
	public DestinationOrder DestinationOrder() {
		return new DestinationOrderImpl(getEnvironment(), DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP);
	}

	@Override
	public History History() {
		return new HistoryImpl(getEnvironment(), History.Kind.KEEP_LAST, DEFAULT_HISTORY_DEPTH);
	}

	@Override
	public ResourceLimits ResourceLimits() {
		return new ResourceLimitsImpl(getEnvironment(),
				ResourceLimits.LENGTH_UNLIMITED, ResourceLimits.LENGTH_UNLIMITED, ResourceLimits.LENGTH_UNLIMITED);
	}

	@Override
	public UserData UserData() {
		return new UserDataImpl(getEnvironment(), emptyBytes());
	}

	@Override
	public Ownership Ownership() {
		return new OwnershipImpl(getEnvironment(), Ownership.Kind.SHARED);
	}

	@Override
	public TimeBasedFilter TimeBasedFilter() {
		return new TimeBasedFilterImpl(getEnvironment(), getEnvironment().getSPI().zeroDuration());
	}

	@Override
	public DataRepresentation Representation() {
		// TODO: "Extended Common Data Representation" /  "XML"
		return new DataRepresentationImpl(getEnvironment(),
				Arrays.asList(
						DataRepresentation.Id.XCDR_DATA_REPRESENTATION,
						DataRepresentation.Id.XML_DATA_REPRESENTATION));
	}

	@Override
	public TypeConsistencyEnforcement TypeConsistency() {
		// DOCS: I can't find any documentation for how this is actually used
		return new TypeConsistencyEnforcementImpl(getEnvironment(),
				TypeConsistencyEnforcement.Kind.EXACT_TYPE_TYPE_CONSISTENCY);
	}

	@Override
	public DurabilityService DurabilityService() {
		return new DurabilityServiceImpl(getEnvironment(),
				getEnvironment().getSPI().zeroDuration(), History(), ResourceLimits());
	}

	@Override
	public Reliability Reliability() {
		return new ReliabilityImpl(getEnvironment(), Reliability.Kind.BEST_EFFORT, defaultReliabilityDelay);
	}

	@Override
	public TransportPriority TransportPriority() {
		return new TransportPriorityImpl(getEnvironment(), DEFAULT_TRANSPORT_PRIORITY);
	}

	@Override
	public Lifespan Lifespan() {
		return new LifespanImpl(getEnvironment(), getEnvironment().getSPI().infiniteDuration());
	}

	@Override
	public OwnershipStrength OwnershipStrength() {
		return new OwnershipStrengthImpl(getEnvironment(), DEFAULT_OWNERSHIP_STRENGTH);
	}

	@Override
	public ReaderDataLifecycle ReaderDataLifecycle() {
		return new ReaderDataLifecycleImpl(getEnvironment(),
				getEnvironment().getSPI().infiniteDuration(), getEnvironment().getSPI().infiniteDuration());
	}

	@Override
	public WriterDataLifecycle WriterDataLifecycle() {
		return new WriterDataLifecycleImpl(getEnvironment(), true);
	}

	@Override
	public Partition Partition() {
		return new PartitionImpl(getEnvironment(), Collections.emptySet());
	}

	@Override
	public GroupData GroupData() {
		return new GroupDataImpl(getEnvironment(), emptyBytes());
	}

	@Override
	public EntityFactory EntityFactory() {
		return new EntityFactoryImpl(getEnvironment(), true);
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
