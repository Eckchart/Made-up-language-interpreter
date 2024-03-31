package model;

import java.util.Vector;
import java.util.Iterator;


public class MyList<T> implements MyIList<T>
{
    private Vector<T> vec;

    public MyList()
    {
        vec = new Vector<T>();
    }

    @Override
    public synchronized void add(T elem)
    {
        vec.add(elem);
    }

    @Override
    public synchronized Iterator<T> iterator()
    {
        return vec.iterator();
    }

    @Override
    public synchronized Vector<T> getList()
    {
        return this.vec;
    }
}
