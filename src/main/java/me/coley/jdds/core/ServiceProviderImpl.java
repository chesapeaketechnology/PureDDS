package me.coley.jdds.core;

import me.coley.jdds.core.condition.GuardConditionImpl;
import me.coley.jdds.core.condition.WaitSetImpl;
import me.coley.jdds.core.datatype.DurationImpl;
import me.coley.jdds.core.datatype.KeyedBytesImpl;
import me.coley.jdds.core.datatype.KeyedStringImpl;
import me.coley.jdds.core.datatype.ModifiableTimeImpl;
import me.coley.jdds.core.datatype.TimeImpl;
import me.coley.jdds.domain.DomainParticipantFactoryImpl;
import me.coley.jdds.core.handle.ImmutableInstanceHandleImpl;
import me.coley.jdds.core.handle.InstanceHandleImpl;
import me.coley.jdds.core.policy.PolicyFactoryImpl;
import org.omg.dds.core.Duration;
import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.core.WaitSet;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.type.TypeSupport;
import org.omg.dds.type.builtin.KeyedBytes;
import org.omg.dds.type.builtin.KeyedString;
import org.omg.dds.type.dynamic.DynamicDataFactory;
import org.omg.dds.type.dynamic.DynamicTypeFactory;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static me.coley.jdds.util.MiscUtil.emptyBytes;
import static me.coley.jdds.util.MiscUtil.emptyString;

/**
 * Service provider implementation.
 *
 * @author Matt Coley
 */
public class ServiceProviderImpl implements ServiceEnvironment.ServiceProviderInterface {
	private final JServiceEnvironment environment;
	private final PolicyFactoryImpl policyFactory = new PolicyFactoryImpl(this);

	/**
	 * Create a new service provider.
	 *
	 * @param environment
	 * 		Spawning environment responsible for generating this instance.
	 */
	public ServiceProviderImpl(JServiceEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public DomainParticipantFactory getParticipantFactory() {
		return new DomainParticipantFactoryImpl(this);
	}

	@Override
	public DynamicTypeFactory getTypeFactory() {
		return null;
	}

	@Override
	public <T> TypeSupport<T> newTypeSupport(Class<T> type, String registeredName) {
		return null;
	}

	@Override
	public Duration newDuration(long duration, TimeUnit unit) {
		return new DurationImpl(getEnvironment(), duration, unit);
	}

	@Override
	public Duration infiniteDuration() {
		return new DurationImpl(getEnvironment(), Long.MAX_VALUE);
	}

	@Override
	public Duration zeroDuration() {
		return new DurationImpl(getEnvironment(), 0);
	}

	@Override
	public ModifiableTime newTime(long time, TimeUnit inputUnit) {
		return new ModifiableTimeImpl(getEnvironment(), inputUnit.toNanos(time));
	}

	@Override
	public Time invalidTime() {
		return new TimeImpl(getEnvironment(), -1);
	}

	@Override
	public InstanceHandleImpl newInstanceHandle() {
		return new InstanceHandleImpl(this);
	}

	@Override
	public InstanceHandle nilHandle() {
		return new ImmutableInstanceHandleImpl(this, null);
	}

	@Override
	public GuardCondition newGuardCondition() {
		return new GuardConditionImpl(this);
	}

	@Override
	public WaitSet newWaitSet() {
		return new WaitSetImpl(this);
	}

	@Override
	public Set<Class<? extends Status>> allStatusKinds() {
		// TODO: Status?
		return null;
	}

	@Override
	public Set<Class<? extends Status>> noStatusKinds() {
		// TODO: Status?
		return null;
	}

	@Override
	public QosProvider newQosProvider(String uri, String profile) {
		// TODO: QoS system?
		return null;
	}

	@Override
	public PolicyFactory getPolicyFactory() {
		return policyFactory;
	}

	@Override
	public DynamicDataFactory getDynamicDataFactory() {
		// TODO: Dynamic data factory
		return null;
	}

	@Override
	public KeyedString newKeyedString() {
		return new KeyedStringImpl(getEnvironment(), emptyString(), emptyString());
	}

	@Override
	public KeyedBytes newKeyedBytes() {
		return new KeyedBytesImpl(getEnvironment(), emptyString(), emptyBytes());
	}

	/**
	 * @return Spawning environment.
	 */
	public JServiceEnvironment getEnvironment() {
		return environment;
	}
}
