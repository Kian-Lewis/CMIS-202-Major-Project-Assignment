package application;

public interface MyMap<K, V> {
	//Remove all entries from the map
	public void clear();
	
	//return true if the specified key is in the map
	public boolean containsKey(K key);
	
	//return true if the map contains the specified value
	public boolean containsValue(V value);
	
	//return a set of entries in the map
	public java.util.Set<Entry<K, V>> entrySet();
	
	//return the value matching the key
	public V get(K key);
	
	//return true if the map is empty
	public boolean isEmpty();
	
	//return a set of the keys in the map
	public java.util.Set<K> keySet();
	
	//add an entry into the map
	public V put(K key, V value);
	
	//remove an entry from the map
	public void remove(K key);
	
	//return the number of mappings in the map
	public int size();
	
	//return a set of the values in the map
	public java.util.Set<V> values();
	
	//Inner class for entry
	public static class Entry<K, V> {
		K key;
		V value;
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return "[" + key + ", " + value + "]";
		}
	}
}
