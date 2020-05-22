package me.coley.jdds.core;

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
	private DomainParticipantFactoryQos qos;
	private DomainParticipantQos defaultParticipantQos;

	/**
	 * Create a factory with default options.
	 *
	 * @param serviceProvider
	 * 		Spawning provider that created the factory.
	 */
	public JDomainParticipantFactory(JServiceProvider serviceProvider) {
		this(serviceProvider,
				serviceProvider.getEnvironment().getConfigurator().getDefaultDomainParticipantFactoryQos(),
				serviceProvider.getEnvironment().getConfigurator().getDefaultDomainParticipantQos());
	}

	/**
	 * Create a factory with the given configurations.
	 *
	 * @param serviceProvider
	 * 		Spawning provider that created the factory.
	 * @param qos
	 * 		Quality of service for the factory.
	 * @param defaultDomainParticipantQos
	 * 		Quality of service for generated participants.
	 */
	public JDomainParticipantFactory(JServiceProvider serviceProvider,
									 DomainParticipantFactoryQos qos,
									 DomainParticipantQos defaultDomainParticipantQos) {
		this.serviceProvider = serviceProvider;
		this.qos = qos;
		this.defaultParticipantQos = defaultDomainParticipantQos;
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
				d -> {
					DomainParticipant participant = new JDomainParticipant(getEnvironment(), d, qos, listener, statuses);
					participant.enable();
					return participant;
				});
	}

	@Override
	public DomainParticipant lookupParticipant(int domainId) {
		return participantMap.get(domainId);
	}

	@Override
	public DomainParticipantFactoryQos getQos() {
		return qos;
	}

	@Override
	public void setQos(DomainParticipantFactoryQos qos) {
		this.qos = qos;
	}

	@Override
	public DomainParticipantQos getDefaultParticipantQos() {
		return defaultParticipantQos;
	}

	@Override
	public void setDefaultParticipantQos(DomainParticipantQos defaultParticipantQos) {
		this.defaultParticipantQos = defaultParticipantQos;
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
