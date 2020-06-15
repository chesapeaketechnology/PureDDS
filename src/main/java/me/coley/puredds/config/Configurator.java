package me.coley.puredds.config;

import me.coley.puredds.core.PureServiceEnvironment;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.omg.dds.domain.DomainParticipantQos;

/**
 * Utility to provide default values to dds types.
 *
 * @author Matt Coley.
 */
public class Configurator {
	private int defaultDomain = 0;
	private long defaultReliabilityBlockingMs = 100;
	private DomainParticipantQos defaultDomainParticipantQos;
	private DomainParticipantFactoryQos defaultDomainParticipantFactoryQos;

	/**
	 * @param environment
	 * 		Environment context.
	 *
	 * @return Configurator instance of the environment, or a new instance if no instance is associated.
	 */
	public static Configurator getConfigurator(ServiceEnvironment environment) {
		// Use environment's instance
		if (environment instanceof PureServiceEnvironment) {
			return ((PureServiceEnvironment) environment).getConfigurator();
		}
		// Create default impl
		return new Configurator();
	}

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

	/**
	 * @return Default quality of service to apply to domain participant factories.
	 */
	public DomainParticipantFactoryQos getDefaultDomainParticipantFactoryQos() {
		return defaultDomainParticipantFactoryQos;
	}

	/**
	 * @param defaultDomainParticipantFactoryQos
	 * 		Default quality of service to apply to domain participant factories.
	 */
	public void setDefaultDomainParticipantFactoryQos(DomainParticipantFactoryQos defaultDomainParticipantFactoryQos) {
		this.defaultDomainParticipantFactoryQos = defaultDomainParticipantFactoryQos;
	}

	/**
	 * @return Default milliseconds for reliability blocking.
	 */
	public long getDefaultReliabilityBlockingMs() {
		return defaultReliabilityBlockingMs;
	}

	/**
	 * @param defaultReliabilityBlockingMs
	 * 		Default milliseconds for reliability blocking.
	 */
	public void setDefaultReliabilityBlockingMs(long defaultReliabilityBlockingMs) {
		this.defaultReliabilityBlockingMs = defaultReliabilityBlockingMs;
	}
}
