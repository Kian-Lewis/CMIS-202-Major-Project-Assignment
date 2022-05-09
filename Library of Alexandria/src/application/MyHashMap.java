package application;

import java.util.LinkedList;

public class MyHashMap<K, V> implements MyMap<K, V> {
	//create variables
	private static int DEFAULT_INITIAL_CAPACITY = 4;        //default hash-table size that is a power of 2
	private static int MAXIMUM_CAPACITY = 1 << 30;          //maximum hash-table size 1 << 30 = 2^30
	private int capacity;                                   //current hash-table capacity; will be a power of 2
	private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f;   //default load factor
	private float loadFactorThreshold;                      //specify load factor used in the hash-table
	private int size = 0;                                   //number of entries in map
	
	//Hash-table is an array with each cell being in a linked list
	LinkedList<MyMap.Entry<K, V>>[] table;
	
	//construct a map with default capacity and load factor
	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
	}
	
	//construct map with specified initial capacity
	public MyHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
	}
	
	//construct map with specified initial capacity and max load factor
	@SuppressWarnings("unchecked")
	public MyHashMap(int initialCapacity, float loadFactorThreshold) {
		if (initialCapacity > MAXIMUM_CAPACITY) {
			this.capacity = MAXIMUM_CAPACITY;
		}
		else {
			this.capacity = trimToPowerOf2(initialCapacity);
		}
		
		this.loadFactorThreshold = loadFactorThreshold;
		table = new LinkedList[capacity];
	}
	
	//return true if the key is in the map
	@Override
	public void clear() {
		size = 0;
		removeEntries();
	}
	
	//return true if map contains the key
	@Override
	public boolean containsKey(K key) {
		if (get(key) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//return true if map contains the value
	@Override
	public boolean containsValue(V value) {
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				LinkedList<Entry<K, V>> bucket = table[i];
				for (Entry<K, V> entry: bucket) {
					if (entry.getValue().equals(value)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	//return a set of entries in the map
	@Override
	public java.util.Set<MyMap.Entry<K, V>> entrySet() {
		java.util.Set<MyMap.Entry<K, V>> set = new java.util.HashSet<>();
		
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				LinkedList<Entry<K, V>> bucket = table[i];
				for (Entry<K, V> entry: bucket) {
					set.add(entry);
				}
			}
		}
		
		return set;
	}
	
	//return the value matching specified key
	@Override
	public V get(K key) {
		int bucketIndex = hash(key.hashCode());
		if (table[bucketIndex] != null) {
			LinkedList<Entry<K, V>> bucket = table[bucketIndex];
			for (Entry<K, V> entry: bucket) {
				if (entry.getKey().equals(key)) {
					return entry.getValue();
				}
			}
		}
		
		return null;
	}
	
	//return true if map contains no entries
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	//return a set of the keys in the map
	@Override
	public java.util.Set<K> keySet() {
		java.util.Set<K> set= new java.util.HashSet<>();
		
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				LinkedList<Entry<K, V>> bucket = table[i];
				for (Entry<K, V> entry: bucket) {
					set.add(entry.getKey());
				}
			}
		}
		
		return set;
	}
	
	//add an entry into the map
	@Override
	public V put(K key, V value) {
		if (get(key) != null) {  //check if key is already in the map
			int bucketIndex = hash(key.hashCode());
			LinkedList<Entry<K, V>> bucket = table[bucketIndex];
			for (Entry<K, V> entry: bucket) {
				if (entry.getKey().equals(key)) {
					V oldValue = entry.getValue();
					//replace old value with new value
					entry.value = value;
					//return old value for the key
					return oldValue;
				}
			}
		}
	
		//check load factor
		if (size >= capacity * loadFactorThreshold) {
			if (capacity == MAXIMUM_CAPACITY) {
				throw new RuntimeException("Exceeding maximum capacity");
			}
		
			rehash();
		}
		
		int bucketIndex = hash(key.hashCode());
		
		//create a linked list for bucket if not already made
		if (table[bucketIndex] == null) {
			table[bucketIndex] = new LinkedList<Entry<K, V>>();
		}
		
		//add a new entry to hashTable[index]
		table[bucketIndex].add(new MyMap.Entry<K, V>(key, value));
		
		size++;//increase size
		
		return value;
	}
	
	//remove the entries for the key specified
	@Override
	public void remove(K key) {
		int bucketIndex = hash(key.hashCode());
		
		//remove the first entry matching the key from a bucket
		if (table[bucketIndex] != null) {
			LinkedList<Entry<K, V>> bucket = table[bucketIndex];
			for(Entry<K, V> entry: bucket) {
				if (entry.getKey().equals(key)) {
					bucket.remove(entry);
					size--; //decrease size
					break; // remove just one entry matching the key
				}
			}
		}
	}
	
	//return number of entries in the map
	@Override
	public int size() {
		return size;
	}
	
	//return a set of values in the map
	@Override
	public java.util.Set<V> values() {
		java.util.Set<V> set = new java.util.HashSet<>();
		
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				LinkedList<Entry<K, V>> bucket = table[i];
				for (Entry<K, V> entry: bucket) {
					set.add(entry.getValue());
				}
			}
		}
		
		return set;
	}
	
	//hash function
	private int hash(int hashCode) {
		return supplementalHash(hashCode) & (capacity - 1);
	}
	
	//ensure that the hashing is evenly distributed
	private static int supplementalHash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	//return power of 2 for initialCapacity
	private int trimToPowerOf2(int initialCapacity) {
		int capacity = 1;
		while (capacity < initialCapacity) {
			capacity <<= 1; //same as capacity *= 2. <= is more efficient
		}
		
		return capacity;
	}
	
	//remove all entries from each bucket
	private void removeEntries() {
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				table[i].clear();
			}
		}
	}
	
	//rehash the map
	@SuppressWarnings("unchecked")
	private void rehash() {
		java.util.Set<Entry<K, V>> set = entrySet(); //get entries
		capacity <<= 1;
		table = new LinkedList[capacity];//create a new hash table\
		size = 0; //reset size to 0
		
		for (Entry<K, V> entry: set) {
			put(entry.getKey(), entry.getValue()); //store to new table.
		}
	}
	
	//return a string representation of the map
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null && table[i].size() > 0) {
				for (Entry<K, V> entry: table[i]) {
					builder.append(entry);
				}
			}
		}
		
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	
	
}
