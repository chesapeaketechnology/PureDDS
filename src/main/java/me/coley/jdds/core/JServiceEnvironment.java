package me.coley.jdds.core;

import me.coley.jdds.config.Configurator;
import org.omg.dds.core.ServiceEnvironment;

/**
 * Implementation of the service environment.
 *
 * @author Matt Coley
 */
public class JServiceEnvironment extends ServiceEnvironment {
	private final Configurator configurator = new Configurator();
	private final JServiceProvider spi = new JServiceProvider(this);

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
