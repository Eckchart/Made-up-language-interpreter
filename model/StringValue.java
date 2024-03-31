package model;


public class StringValue implements IValue
{
    private String val;

    public StringValue(String val)
    {
        this.val = val;
    }

    public String getVal()
    {
        return this.val;
    }

    @Override
    public IType getType()
    {
        return new StringType();
    }

    @Override
    public boolean equals(Object another)
    {
        if (!(another instanceof StringValue))
        {
            return false;
        }
        StringValue anotherCast = (StringValue)another;
        return val.equals(anotherCast.getVal());
    }

    @Override
    public String toString()
    {
        return this.val;
    }
}
