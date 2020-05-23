package me.coley.jdds.core;

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
	private final JServiceProvider spi;
	private final Map<Integer, DomainParticipant> participantMap = new HashMap<>();
	private final int defaultDomain;
	private DomainParticipantFactoryQos qos;
	private DomainParticipantQos defaultParticipantQos;

	/**
	 * Create a factory with default options.
	 *
	 * @param spi
	 * 		Spawning provider that created the factory.
	 */
	public JDomainParticipantFactory(JServiceProvider spi) {
		this(spi,
				spi.getEnvironment().getConfigurator().getDefaultDomainParticipantFactoryQos(),
				spi.getEnvironment().getConfigurator().getDefaultDomainParticipantQos(),
				spi.getEnvironment().getConfigurator().getDefaultDomain());
	}

	/**
	 * Create a factory with the given configurations.
	 *
	 * @param spi
	 * 		Spawning provider that created the factory.
	 * @param qos
	 * 		Quality of service for the factory.
	 * @param defaultDomainParticipantQos
	 * 		Quality of service for generated participants.
	 * @param defaultDomain The default domain.
	 */
	public JDomainParticipantFactory(JServiceProvider spi,
									 DomainParticipantFactoryQos qos,
									 DomainParticipantQos defaultDomainParticipantQos,
									 int defaultDomain) {
		this.spi = spi;
		this.qos = qos;
		this.defaultParticipantQos = defaultDomainParticipantQos;
		this.defaultDomain = defaultDomain;
	}

	@Override
	public DomainParticipant createParticipant() {
		return createParticipant(defaultDomain);
	}

	@Override
	public DomainParticipant createParticipant(int domainId) {
		return createParticipant(domainId, getDefaultParticipantQos(), null,
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
		return spi.getEnvironment();
	}
}
