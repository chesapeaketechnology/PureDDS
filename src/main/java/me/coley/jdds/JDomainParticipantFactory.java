package me.coley.jdds;

import me.coley.jdds.config.Configurator;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory implementation for domain participants.
 *
 * @author Matt Coley
 */
public class JDomainParticipantFactory extends DomainParticipantFactory {
	private final JServiceProvider serviceProvider;
	private final Map<Integer, DomainParticipant> participantMap = new HashMap<>();

	/**
	 * @param serviceProvider
	 * 		Spawning provider that created the factory.
	 */
	public JDomainParticipantFactory(JServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Override
	public DomainParticipant createParticipant() {
		return createParticipant(getConfigurator().getDefaultDomain());
	}

	@Override
	public DomainParticipant createParticipant(int domainId) {
		return createParticipant(domainId, getConfigurator().getDefaultDomainParticipantQos(), null,
				Collections.emptySet());
	}

	@Override
	public DomainParticipant createParticipant(int domainId, DomainParticipantQos qos,
											   DomainParticipantListener listener,
											   Collection<Class<? extends Status>> statuses) {
		return participantMap.computeIfAbsent(domainId,
				d -> new JDomainParticipant(getEnvironment(), d, qos, listener, statuses));
	}

	@Override
	public DomainParticipant lookupParticipant(int domainId) {
		return null;
	}

	@Override
	public DomainParticipantFactoryQos getQos() {
		return null;
	}

	@Override
	public void setQos(DomainParticipantFactoryQos qos) {

	}

	@Override
	public DomainParticipantQos getDefaultParticipantQos() {
		return null;
	}

	@Override
	public void setDefaultParticipantQos(DomainParticipantQos qos) {

	}

	@Override
	public JServiceEnvironment getEnvironment() {
		return serviceProvider.getEnvironment();
	}

	/**
	 * @return Environment configurator.
	 */
	private Configurator getConfigurator() {
		return getEnvironment().getConfigurator();
	}
}
