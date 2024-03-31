package model;

import java.util.Vector;

public interface MyIList<T> extends Iterable<T>
{
    void add(T elem);

    Vector<T> getList();
}
