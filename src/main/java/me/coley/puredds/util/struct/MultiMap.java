package me.coley.puredds.util.struct;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Map interface that uses a collection of values for a 1-to-N mapping.
 *
 * @param <K>
 * 		Key type.
 * @param <V>
 * 		Value type.
 *
 * @author Matt Coley
 */
public interface MultiMap<K, V> extends Map<K, Collection<V>> {
	/**
	 * Convenience call to chained {@code compute-if-absent}/{@code add}.
	 *
	 * @param key
	 * 		Key value.
	 * @param value
	 * 		A value to add.
	 */
	void putSingle(K key, V value);

	/**
	 * Convenience call to chained {@code get(key).contains(value)}.
	 *
	 * @param key
	 * 		Key value.
	 * @param value
	 * 		A value to check.
	 *
	 * @return {@code true} When the key-value pair exists.
	 */
	boolean contains(K key, V value);

	/**
	 * @param action
	 * 		Some action to run on all key-value pairs.
	 */
	void forAll(BiConsumer<? super K, ? super V> action);
}
