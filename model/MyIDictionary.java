package model;

import java.util.Set;
import java.util.Map;


public interface MyIDictionary<K, V>
{
    Map<K, V> getMappings();
    
    boolean isDefined(K id);

    V lookup(K id);

    void update(K id, V new_val);

    void add(K id, V new_val);

    Set<K> keySet();
    
    void delete(K id);

    MyIDictionary<K, V> copy();
}
