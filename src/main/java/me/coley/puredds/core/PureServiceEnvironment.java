package me.coley.puredds.core;

import me.coley.puredds.config.Configurator;
import org.omg.dds.core.ServiceEnvironment;

/**
 * Implementation of the service environment.
 *
 * @author Matt Coley
 */
public class PureServiceEnvironment extends ServiceEnvironment {
	private final Configurator configurator = new Configurator();
	private final ServiceProviderImpl spi = new ServiceProviderImpl(this);

	/**
	 * @return Configurator responsible for populating default values of dds types.
	 */
	public Configurator getConfigurator() {
		return configurator;
	}

	@Override
	public ServiceProviderInterface getSPI() {
		return spi;
	}
}
