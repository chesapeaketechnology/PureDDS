package me.coley.jdds.config;

import org.omg.dds.domain.DomainParticipantQos;

/**
 * Utility to provide default values to dds types.
 *
 * @author Matt Coley.
 */
public class Configurator {
	private int defaultDomain;
	private DomainParticipantQos defaultDomainParticipantQos;

	/**
	 * @return Default domain.
	 */
	public int getDefaultDomain() {
		return defaultDomain;
	}

	/**
	 * @param defaultDomain
	 * 		Default domain.
	 */
	public void setDefaultDomain(int defaultDomain) {
		this.defaultDomain = defaultDomain;
	}

	/**
	 * @return Default quality of service to apply to domain participants.
	 */
	public DomainParticipantQos getDefaultDomainParticipantQos() {
		return defaultDomainParticipantQos;
	}

	/**
	 * @param defaultDomainParticipantQos
	 * 		Default quality of service to apply to domain participants.
	 */
	public void setDefaultDomainParticipantQos(DomainParticipantQos defaultDomainParticipantQos) {
		this.defaultDomainParticipantQos = defaultDomainParticipantQos;
	}
}
