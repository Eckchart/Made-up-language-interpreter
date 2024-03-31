package model;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public class MyHeap implements MyIHeap
{
    private Map<Integer, IValue> mappings;
    private Integer freeLocation;

    private void setNewFreeLocation()
    {
        Random rand = new Random();
        Integer bound = 10000;
        this.freeLocation = rand.nextInt(bound);
        while (this.freeLocation == 0 || this.mappings.containsKey(this.freeLocation))
        {
            this.freeLocation = rand.nextInt(bound);
        }
    }
    
    public MyHeap()
    {
        this.mappings = new ConcurrentHashMap<Integer, IValue>();
        synchronized (this)
        {
            this.setNewFreeLocation();
        }
    }

    public MyHeap(Map<Integer, IValue> mappings)
    {
        this.mappings = mappings;
        synchronized (this)
        {
            this.setNewFreeLocation();
        }
    }

    @Override
    public synchronized Integer getFreeLocation()
    {
        return this.freeLocation;
    }

    @Override
    public synchronized Map<Integer, IValue> getMappings()
    {
        return this.mappings;
    }

    @Override
    public synchronized void setMappings(Map<Integer, IValue> newMappings)
    {
        this.mappings = new ConcurrentHashMap<Integer, IValue>(newMappings);
    }

    @Override
    public IValue lookup(Integer address)
    {
        return this.mappings.get(address);
    }

    @Override
    public synchronized Integer add(IValue val)
    {
        this.setNewFreeLocation();
        this.mappings.put(this.freeLocation, val);
        return this.freeLocation;
    }

    @Override
    public void update(Integer address, IValue val)
    {
        this.mappings.put(address, val);
    }
}
