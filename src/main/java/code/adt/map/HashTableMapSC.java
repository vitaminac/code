package material.maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/*import material.maps.AbstractHashTableMap.HashEntry;
import material.maps.AbstractHashTableMap.HashEntryIndex;
import material.maps.AbstractHashTableMap.HashTableMapIterator;
import material.maps.AbstractHashTableMap.HashTableMapKeyIterator;*/

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 */
public class HashTableMapSC<K, V> implements Map<K, V> {

    private class HashEntry<T, U> implements Entry<T, U> {
    	protected T key;
    	protected U value; 
        public HashEntry(T k, U v) {
            this.key=k;
            this.value=v;
        }

        @Override
        public U getValue() {
        	return this.value;
        }

        @Override
        public T getKey() {
            return this.key;
        }

        public U setValue(U val) {
        	U oldV=this.value;
            this.value=val;
            return oldV;
        }

        @Override
        public boolean equals(Object o) {
            if(o.getClass()!=this.getClass())
            	return false;
            HashEntry<T,U> e;
            try {
            	e=(HashEntry<T,U>)o;
            }catch(ClassCastException f) {
            	return false;
            }
            return (e.getKey().equals(this.getKey())&&e.getValue().equals(this.getValue()));
        }

        /**
         * Relation visualization.
         */
        @Override
        public String toString() {
        	return "(" + key + "," + value + ")";
        }
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {
    	private int pos;
    	private int posB;
    	private ArrayList<HashEntry<T,U>>[] map;
        //Ejercicio 2.2
        public HashTableMapIterator(ArrayList<HashEntry<T, U>>[] map, int numElems) {
            this.map=map;
            if(numElems==0) {
            	pos=map.length;
            }else {
				pos = 0;
				this.posB = 0;
				while(this.map[pos]==null&&this.pos<this.map.length)
					pos++;
			}
		}

		private void goToNextElement() {
			if (this.map[pos].size()-1 == this.posB) {// en caso de haber recorrido ya todo el bucket
				pos++;
				while (this.pos < this.map.length&&this.map[this.pos] == null) {
					this.pos++;
				}
				this.posB = 0;
			} else {
				this.posB++;
			}
		}

		@Override
		public boolean hasNext() {
            return this.pos<this.map.length;
        }

        @Override
        public Entry<T, U> next() {
            if(this.hasNext()) {        
            	try {
            	int currentPos=this.pos;
            	int currentPosB=this.posB;
            	this.goToNextElement();
            	return this.map[currentPos].get(currentPosB);
            	}catch(Exception e) {
            		int currentPos=this.pos;
                	int currentPosB=this.posB;
                	this.goToNextElement();
                	return this.map[currentPos].get(currentPosB);
            	}
            }else {
            	throw new IllegalStateException("The map has not more elements");
            }
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {
    	private HashTableMapIterator<T,U> it;
        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
        	this.it=it;
        }

        @Override
        public T next() {
            return it.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {
    	HashTableMapIterator<T,U> it;
        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            this.it=it;
        }

        @Override
        public U next() {
            return it.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    int n;//numero de entradas
    protected int prime, capacity; // prime factor and capacity of bucket array
    protected long scale, shift; // the shift and scaling factors
    protected ArrayList<HashEntry<K,V>>[] map;
    /**
     * Creates a hash table
     */
    public HashTableMapSC() {
    	this(109345121, 1000); 
    }

    /**
     * Creates a hash table.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
    	 this(109345121, cap);
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
    	this.capacity=cap;
    	this.prime=p;
    	this.n=0;
    	this.map=(ArrayList<HashEntry<K,V>>[])new ArrayList[this.capacity];
    	Random rand=new Random();
    	this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return
     */
    protected int hashValue(K key) {
    	return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    /**
     * Returns the number of entries in the hash table.
     *
     * @return the size
     */
    @Override
    public int size() {
        return this.n;
    }

    /**
     * Returns whether or not the table is empty.
     *
     * @return true if the size is 0
     */
    @Override
    public boolean isEmpty() {
        return n==0;
    }

    /**
     * Returns the value associated with a key.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(K key) throws IllegalStateException {
        HashEntryIndex i=this.findEntry(key);
        if(!i.found) {
        	return null;
        }
        return this.map[i.indexA].get(i.indexB).getValue();
    }
    protected class HashEntryIndex{
    	
    	int indexA;
    	int indexB;
        boolean found;

        public HashEntryIndex(int indexA, int indexB, boolean f) {
            this.indexA = indexA;
            this.indexB=indexB;
            this.found = f;
        }
    }
    protected HashEntryIndex findEntry(K key) throws IllegalStateException {
    	this.checkKey(key);
    	int hashcode=this.hashValue(key);
    	int indexA=hashcode;
    	if(this.map[indexA]==null) {
    		return new HashEntryIndex(indexA,0,false);
    	}else {
    		ArrayList<HashEntry<K,V>> a=this.map[indexA];
    		for(int i=0;i<a.size();i++) {
    			if(a.get(i).getKey().equals(key)) {
    				return new HashEntryIndex(indexA,i,true);
    			}
    		}
    		return new HashEntryIndex(indexA,0,false);
    	}
    }

    /**
     * Put a key-value pair in the map, replacing previous one if it exists.
     *
     * @param key
     * @param value
     * @return value
     */
    @Override
    public V put(K key, V value) throws IllegalStateException {
        HashEntryIndex i=this.findEntry(key);
        if(!i.found) {
        	if (n >= capacity / 2) {
                rehash(); 
                i = findEntry(key); 
            }
        	if(this.map[i.indexA]==null) 
        		this.map[i.indexA]=new ArrayList<HashEntry<K,V>>();         	        		   
        	this.map[i.indexA].add(new HashEntry<K,V>(key,value));
        	n++;
        	return null;
        }else {
        	return this.map[i.indexA].get(i.indexB).setValue(value);
        }
    }
    protected void rehash() {
    	 this.capacity=this.capacity*2;
    	 ArrayList<HashEntry<K,V>>[] oldMap=this.map;
    	 this.map=new ArrayList[this.capacity];
    	 Random rand = new Random();
         scale = rand.nextInt(prime - 1) + 1;
         shift = rand.nextInt(prime);
         HashTableMapIterator<K,V> i=new HashTableMapIterator<K,V>(oldMap,this.n);
         this.n=0;
         HashEntry<K,V> e;
         while(i.hasNext()) {
        	 e=(HashEntry<K,V>)i.next();//estoy seguro ya que lo he insertado yo antes
        	 this.put(e.getKey(),e.getValue());
         }
    }

    /**
     * Removes the key-value pair with a specified key.
     *
     * @param key
     * @return
     */
    @Override
    public V remove(K key) throws IllegalStateException {
        HashEntryIndex i=this.findEntry(key);
        if(!i.found) {
        	return null;
        }
        V returnV=this.map[i.indexA].get(i.indexB).getValue();
        this.map[i.indexA].remove(i.indexB);
        n--;
        return returnV;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator<K,V>(this.map,this.n);
    }

    /**
     * Returns an iterable object containing all of the keys.
     *
     * @return
     */
    @Override
    public Iterable<K> keys() {
    	return new Iterable<K>() {
            public Iterator<K> iterator() {
                return new HashTableMapKeyIterator<K, V>(new HashTableMapIterator<>(map, n));
            }
        };
    }

    /**
     * Returns an iterable object containing all of the values.
     *
     * @return
     */
    @Override
    public Iterable<V> values() {
    	return new Iterable<V>() {
            public Iterator<V> iterator() {
                return new HashTableMapValueIterator<K, V>(new HashTableMapIterator<>(map, n));
            }
        };
    }

    /**
     * Returns an iterable object containing all of the entries.
     *
     * @return
     */
    @Override
    public Iterable<Entry<K, V>> entries() {
        return new Iterable<Entry<K,V>>() {
        	public Iterator<Entry<K,V>> iterator(){
        		return new HashTableMapIterator<K,V>(map,n);
        	}
        };
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
    	if (k == null) {
            throw new IllegalStateException("Invalid key: null.");
        }
    }

    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     */
    protected void rehash(int newCap) {
    	this.capacity=newCap;
   	 	ArrayList<HashEntry<K,V>>[] oldMap=this.map;
   	 	this.map=new ArrayList[this.capacity];
   	 	Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
        HashTableMapIterator<K,V> i=new HashTableMapIterator<K,V>(oldMap,this.n);
        this.n=0;
        HashEntry<K,V> e;
        while(i.hasNext()) {
       	 e=(HashEntry<K,V>)i.next();//estoy seguro ya que lo he insertado yo antes
       	 this.put(e.getKey(),e.getValue());
        }
    }
}
