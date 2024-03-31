package model;

import java.util.Stack;
import java.util.Iterator;


public class MyStack<T> implements MyIStack<T>
{
    private Stack<T> stk;

    public MyStack()
    {
        stk = new Stack<T>();
    }

    @Override
    public T pop()
    {
        return stk.pop();
    }

    @Override
    public T peek()
    {
        return stk.peek();
    }

    @Override
    public void push(T elem)
    {
        stk.push(elem);
    }

    @Override
    public int getSize()
    {
        return stk.size();
    }

    @Override
    public Iterator<T> iterator()
    {
        return stk.iterator();
    }

    @Override
    public boolean isEmpty()
    {
        return stk.isEmpty();
    }
}
