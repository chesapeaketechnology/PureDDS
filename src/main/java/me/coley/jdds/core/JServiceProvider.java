package me.coley.jdds.core;

import me.coley.jdds.core.datatype.JDuration;
import me.coley.jdds.core.datatype.JModifiableTime;
import me.coley.jdds.core.datatype.JTime;
import me.coley.jdds.core.handle.ImmutableInstanceHandleImpl;
import me.coley.jdds.core.handle.InstanceHandleImpl;
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

/**
 * Service provider implementation.
 *
 * @author Matt Coley
 */
public class JServiceProvider implements ServiceEnvironment.ServiceProviderInterface {
	private final JServiceEnvironment environment;

	/**
	 * Create a new service provider.
	 *
	 * @param environment
	 * 		Spawning environment responsible for generating this instance.
	 */
	public JServiceProvider(JServiceEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public DomainParticipantFactory getParticipantFactory() {
		return new JDomainParticipantFactory(this);
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
		return new JDuration(getEnvironment(), duration, unit);
	}

	@Override
	public Duration infiniteDuration() {
		return new JDuration(getEnvironment(), Long.MAX_VALUE);
	}

	@Override
	public Duration zeroDuration() {
		return new JDuration(getEnvironment(), 0);
	}

	@Override
	public ModifiableTime newTime(long time, TimeUnit inputUnit) {
		return new JModifiableTime(getEnvironment(), inputUnit.toNanos(time));
	}

	@Override
	public Time invalidTime() {
		return new JTime(getEnvironment(), -1);
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
		return null;
	}

	@Override
	public WaitSet newWaitSet() {
		// TODO: WaitSet
		return null;
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
		// TODO: PolicyFactory and all policy implementation classes
		return null;
	}

	@Override
	public DynamicDataFactory getDynamicDataFactory() {
		// TODO: Dynamic data factory
		return null;
	}

	@Override
	public KeyedString newKeyedString() {
		// TODO: KString
		return null;
	}

	@Override
	public KeyedBytes newKeyedBytes() {
		// TODO: KBytes
		return null;
	}

	/**
	 * @return Spawning environment.
	 */
	public JServiceEnvironment getEnvironment() {
		return environment;
	}
}
