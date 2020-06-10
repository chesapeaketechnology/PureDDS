package me.coley.puredds.core.datatype;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.type.builtin.KeyedBytes;

import java.util.Arrays;

/**
 * Key-value <i>({@code String,byte[]})</i> implementation.
 *
 * @author Matt Coley
 */
public class KeyedBytesImpl extends KeyedBytes {
	private final ServiceEnvironment environment;
	private byte[] value;
	private String key;

	/**
	 * @param environment
	 * 		Context environment.
	 * @param key
	 * 		Pair key.
	 * @param value
	 * 		Pair value
	 */
	public KeyedBytesImpl(ServiceEnvironment environment, CharSequence key, byte[] value) {
		this.environment = environment;
		this.key = key.toString();
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public KeyedBytes setKey(CharSequence key) {
		return this;
	}

	@Override
	public byte[] getValue() {
		return value;
	}

	@Override
	public KeyedBytes setValue(byte[] value) {
		this.value = value;
		return this;
	}

	@Override
	public KeyedBytes setValue(byte[] value, int offset, int length) {
		this.value = Arrays.copyOfRange(value, offset, length);
		return this;
	}

	@Override
	public void copyFrom(KeyedBytes src) {
		this.key = src.getKey();
		this.value = src.getValue();
	}

	@Override
	public KeyedBytes clone() {
		return new KeyedBytesImpl(getEnvironment(), getKey(), getValue());
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
