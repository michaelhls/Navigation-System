package NavigationSystem;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class HashTable<K, V> {
  public static class Entry<K, V> {
    private K key;
    private V value;
  
    public Entry(final K key, final V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey() {
      return this.key;
    }

    public V getValue() {
      return this.value;
    }

    public static <K2 extends Comparable<? super K2>, V2> Comparator<Entry<K2, V2>> comparingByKey() {
      return (entry1, entry2) -> entry1.getKey().compareTo(entry2.getKey());
    }
    
    public static <K2, V2 extends Comparable<? super V2>> Comparator<Entry<K2, V2>> comparingByValue() {
      return (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue());
    }
  }

  private ArrayList<Entry<K, V>>[] table;

  @SuppressWarnings("unchecked")
  public HashTable(final int size) {
    this.table = new ArrayList[size];

    for (int i = 0; i < size; i++) {
      this.table[i] = new ArrayList<>();
    }
  }

  public void set(final K key, final V value) {
    final ArrayList<Entry<K, V>> entries = this.table[this.getIndex(key)];

    for (final Entry<K, V> entry: entries) {
      if (entry.key == key) {
        entry.value = value;
        return;
      }
    }

    entries.add(new Entry<>(key, value));
  }

  public V get(final K key) {
    for (final Entry<K, V> entry: this.table[this.getIndex(key)]) {
      if (entry.key == key) {
        return entry.value;
      }
    }

    return null;
  }

  public void remove(final K key) throws IllegalArgumentException {
    this.table[this.getIndex(key)].removeIf(entry -> entry.key == key);
  }

  private int getIndex(final K key) throws IllegalArgumentException {
    final int index = this.hashFunction(key);

    if (index < 0 || index >= this.table.length) {
      throw new IllegalArgumentException("Error: Telah mendapatkan argumen key dengan hash index yang tidak valid: " + index);
    }

    return index;
  }

  protected abstract int hashFunction(final K key);
}