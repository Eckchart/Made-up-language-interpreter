package model;


public interface MyIStack<T> extends Iterable<T>
{
    T pop();

    T peek();

    void push(T elem);

    int getSize();

    boolean isEmpty();
}
