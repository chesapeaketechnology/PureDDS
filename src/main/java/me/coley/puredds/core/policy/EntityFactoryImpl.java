package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.EntityFactory;

/**
 * EntityFactory impl.
 *
 * @author Matt Coley
 * @see EntityFactory
 */
public class EntityFactoryImpl extends AbstractPolicy implements EntityFactory {
	private final boolean autoEnableCreatedEntities;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param autoEnableCreatedEntities
	 * 		Automatic enable value.
	 */
	public EntityFactoryImpl(ServiceEnvironment environment, boolean autoEnableCreatedEntities) {
		super(environment);
		this.autoEnableCreatedEntities = autoEnableCreatedEntities;
	}

	@Override
	public boolean isAutoEnableCreatedEntities() {
		return autoEnableCreatedEntities;
	}

	@Override
	public EntityFactory withAutoEnableCreatedEntities(boolean autoEnableCreatedEntities) {
		return new EntityFactoryImpl(getEnvironment(), autoEnableCreatedEntities);
	}
}
