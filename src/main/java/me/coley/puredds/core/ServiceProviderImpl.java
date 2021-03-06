package me.coley.puredds.core;

import me.coley.puredds.core.condition.GuardConditionImpl;
import me.coley.puredds.core.condition.WaitSetImpl;
import me.coley.puredds.core.datatype.DurationImpl;
import me.coley.puredds.core.datatype.KeyedBytesImpl;
import me.coley.puredds.core.datatype.KeyedStringImpl;
import me.coley.puredds.core.datatype.ModifiableTimeImpl;
import me.coley.puredds.core.datatype.TimeImpl;
import me.coley.puredds.domain.DomainParticipantFactoryImpl;
import me.coley.puredds.core.handle.ImmutableInstanceHandleImpl;
import me.coley.puredds.core.handle.InstanceHandleImpl;
import me.coley.puredds.core.policy.PolicyFactoryImpl;
import me.coley.puredds.type.TypeSupportImpl;
import me.coley.puredds.util.Implementations;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static me.coley.puredds.util.MiscUtil.emptyBytes;
import static me.coley.puredds.util.MiscUtil.emptyString;

/**
 * Service provider implementation.
 *
 * @author Matt Coley
 */
public class ServiceProviderImpl implements ServiceEnvironment.ServiceProviderInterface {
	private final PureServiceEnvironment environment;
	private final PolicyFactoryImpl policyFactory = new PolicyFactoryImpl(getEnvironment());

	/**
	 * Create a new service provider.
	 *
	 * @param environment
	 * 		Spawning environment responsible for generating this instance.
	 */
	public ServiceProviderImpl(PureServiceEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public DomainParticipantFactory getParticipantFactory() {
		return new DomainParticipantFactoryImpl(getEnvironment());
	}

	@Override
	public DynamicTypeFactory getTypeFactory() {
		// TODO: Dynamic type support
		//  - DOCS: Literally no idea how though, there is NO JAVADOCS AT ALL >:(
		throw new UnsupportedOperationException();
	}

	@Override
	public DynamicDataFactory getDynamicDataFactory() {
		// TODO: Dynamic data factory
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> TypeSupport<T> newTypeSupport(Class<T> type, String registeredName) {
		return new TypeSupportImpl<>(getEnvironment(), type, registeredName);
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
		return new InstanceHandleImpl(getEnvironment());
	}

	@Override
	public InstanceHandle nilHandle() {
		return new ImmutableInstanceHandleImpl(getEnvironment(), null);
	}

	@Override
	public GuardCondition newGuardCondition() {
		return new GuardConditionImpl(getEnvironment());
	}

	@Override
	public WaitSet newWaitSet() {
		return new WaitSetImpl(getEnvironment());
	}

	@Override
	public Set<Class<? extends Status>> allStatusKinds() {
		// DOCS: Is this correct? There is no documentation for this method
		return new HashSet<>(Implementations.getAllStatuses());
	}

	@Override
	public Set<Class<? extends Status>> noStatusKinds() {
		// DOCS: Is this correct? There is no documentation for this method
		return Collections.emptySet();
	}

	@Override
	public QosProvider newQosProvider(String uri, String profile) {
		// TODO: QoS system?
		throw new UnsupportedOperationException();
	}

	@Override
	public PolicyFactory getPolicyFactory() {
		return policyFactory;
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
	public PureServiceEnvironment getEnvironment() {
		return environment;
	}
}
