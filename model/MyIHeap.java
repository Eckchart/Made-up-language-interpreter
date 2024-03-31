package model;

import java.util.Map;


public interface MyIHeap
{
    Integer getFreeLocation();

    Map<Integer, IValue> getMappings();

    void setMappings(Map<Integer, IValue> newMappings);

    IValue lookup(Integer address);

    Integer add(IValue val);

    void update(Integer address, IValue val);
}
