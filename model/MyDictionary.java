package model;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class MyDictionary<K, V> implements MyIDictionary<K, V>
{
    private Map<K, V> dict;

    public MyDictionary() {
        dict = new ConcurrentHashMap<K, V>();
    }

    @Override
    public Map<K, V> getMappings() {
        return this.dict;
    }

    @Override
    public boolean isDefined(K id) {
        return dict.containsKey(id);
    }

    @Override
    public V lookup(K id) {
        return dict.get(id);
    }

    @Override
    public void update(K id, V new_val) {
        dict.put(id, new_val);
    }

    @Override
    public void add(K id, V new_val) {
        dict.put(id, new_val);
    }

    @Override
    public synchronized Set<K> keySet() {
        return dict.keySet();
    }

    @Override
    public void delete(K id) {
        dict.remove(id);
    }

    @Override
    public MyIDictionary<K, V> copy() {
        MyIDictionary<K, V> copiedDict = new MyDictionary<K, V>();
        for (K key : this.keySet()) {
            copiedDict.add(key, this.lookup(key));
        }
        return copiedDict;
    }
}
