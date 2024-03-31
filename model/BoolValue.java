package model;


public class BoolValue implements IValue
{
    private boolean val;

    public BoolValue(boolean val)
    {
        this.val = val;
    }

    public boolean getVal()
    {
        return this.val;
    }

    @Override
    public IType getType()
    {
        return new BoolType();
    }

    @Override
    public boolean equals(Object another)
    {
        if (!(another instanceof BoolValue))
        {
            return false;
        }
        BoolValue anotherCast = (BoolValue)another;
        return val == anotherCast.getVal();
    }

    @Override
    public String toString()
    {
        return this.val ? "true" : "false";
    }
}
