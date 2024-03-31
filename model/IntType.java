package model;


public class IntType implements IType
{
    @Override
    public boolean equals(Object another)
    {
        return another instanceof IntType;
    }

    @Override
    public IValue getDefault()
    {
        return new IntValue(0);
    }

    @Override
    public String toString()
    {
        return "int";
    }
}
