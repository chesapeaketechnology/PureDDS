package me.coley.puredds.util.struct;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Minimal multimap using hashmap backing.
 *
 * @param <K>
 * 		Key type.
 * @param <V>
 * 		Value type.
 *
 * @author Matt Coley
 */
public class HashMultiMap<K, V> extends HashMap<K, Collection<V>> implements MultiMap<K, V> {
	@Override
	public void putSingle(K key, V value) {
		computeIfAbsent(key, k -> new HashSet<>()).add(value);
	}

	@Override
	public boolean contains(K key, V value) {
		Collection<V> set = get(key);
		if (set == null) {
			return false;
		}
		return set.contains(value);
	}

	@Override
	public void forAll(BiConsumer<? super K, ? super V> action) {
		for (Map.Entry<K, Collection<V>> entry : entrySet()) {
			K k = entry.getKey();
			entry.getValue().forEach(v -> action.accept(k, v));
		}
	}
}