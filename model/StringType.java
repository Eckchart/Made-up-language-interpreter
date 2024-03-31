package model;


public class StringType implements IType
{
    @Override
    public boolean equals(Object another)
    {
        return another instanceof StringType;
    }

    @Override
    public IValue getDefault()
    {
        return new StringValue("");
    }

    @Override
    public String toString()
    {
        return "String";
    }
}
