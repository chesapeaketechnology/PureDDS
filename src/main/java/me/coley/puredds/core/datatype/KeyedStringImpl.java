package me.coley.puredds.core.datatype;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.type.builtin.KeyedString;

/**
 * Key-value <i>({@code String,String})</i> implementation.
 *
 * @author Matt Coley
 */
public class KeyedStringImpl extends KeyedString {
	private final ServiceEnvironment environment;
	private String value;
	private String key;

	/**
	 * @param environment
	 * 		Context environment.
	 * @param key
	 * 		Pair key.
	 * @param value
	 * 		Pair value
	 */
	public KeyedStringImpl(ServiceEnvironment environment, CharSequence key, CharSequence value) {
		this.environment = environment;
		this.key = key.toString();
		this.value = value.toString();
	}

	@Override
	public KeyedString setKey(CharSequence key) {
		this.key = key.toString();
		return this;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public KeyedString setValue(CharSequence value) {
		this.value = value.toString();
		return this;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void copyFrom(KeyedString src) {
		this.key = src.getKey();
		this.value = src.getKey();
	}

	@Override
	public KeyedString clone() {
		return new KeyedStringImpl(getEnvironment(), getKey(), getValue());
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
