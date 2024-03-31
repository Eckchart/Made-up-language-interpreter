package model;


public class BoolType implements IType
{
    @Override
    public boolean equals(Object another)
    {
        return another instanceof BoolType;
    }

    @Override
    public IValue getDefault()
    {
        return new BoolValue(false);
    }
    
    @Override
    public String toString()
    {
        return "bool";
    }
}
