package me.coley.jdds;

import me.coley.jdds.config.Configurator;
import org.omg.dds.core.ServiceEnvironment;

/**
 * Implementation of the service environment.
 *
 * @author Matt Coley
 */
public class JServiceEnvironment extends ServiceEnvironment {
	private final Configurator configurator = new Configurator();

	/**
	 * @return Configurator responsible for populating default values of dds types.
	 */
	public Configurator getConfigurator() {
		return configurator;
	}

	@Override
	public ServiceProviderInterface getSPI() {
		return new JServiceProvider(this);
	}
}
