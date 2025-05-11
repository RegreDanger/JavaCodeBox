package util.datastructures;

import java.util.Map;
import java.util.stream.Collectors;

public class BidirectionalMap<K, V> {
	private Map<K, V> map;
	private Map<V, K> reversedMap;
	
	public BidirectionalMap(Map<K, V> map) {
		this.map = map;
		this.reversedMap = map.entrySet().stream().collect(Collectors.toMap(v -> (V) v.getValue(), k -> (K) k.getKey()));
	}
	
	public void insert(K key, V value) {
		map.put(key, value);
		reversedMap.put(value, key);
	}
	
	public boolean containsValue(V value) {
		return map.containsValue(value);
	}
	
	public boolean containsKey(K key) {
		return reversedMap.containsValue(key);
	}
	
	public K getKey(V value) {
		return reversedMap.get(value);
	}
	
	public V getValue(K key) {
		return map.get(key);
	}
	
	public Map<K, V> getMap() {
		return map;
	}
	
	public Map<V, K> getReversedMap() {
		return reversedMap;
	}
	
}
