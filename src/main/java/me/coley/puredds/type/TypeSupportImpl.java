package me.coley.puredds.type;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.type.TypeSupport;

/**
 * TypeSupport impl.
 *
 * @param <T>
 * 		Supported type.
 *
 * @author Matt Coley
 */
public class TypeSupportImpl<T> extends TypeSupport<T> {
	private final ServiceEnvironment environment;
	private final Class<T> clazz;
	private final String name;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param clazz
	 * 		Class to generate instances of.
	 * @param name
	 * 		Type name.
	 */
	public TypeSupportImpl(ServiceEnvironment environment, Class<T> clazz, String name) {
		this.environment = environment;
		this.clazz = clazz;
		this.name = name;
	}

	@Override
	public T newData() {
		try {
			// Naive approach, will likely swap out for something later
			return getType().getDeclaredConstructor().newInstance();
		} catch(Exception e) {
			throw new TypeCreationException(e, getType());
		}
	}

	@Override
	public Class<T> getType() {
		return clazz;
	}

	@Override
	public String getTypeName() {
		return name;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
