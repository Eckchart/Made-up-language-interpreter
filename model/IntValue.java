package model;


public class IntValue implements IValue
{
    private int val;

    public IntValue(int val)
    {
        this.val = val;
    }

    public int getVal()
    {
        return this.val;
    }

    @Override
    public IType getType()
    {
        return new IntType();
    }

    @Override
    public boolean equals(Object another)
    {
        if (!(another instanceof IntValue))
        {
            return false;
        }
        IntValue anotherCast = (IntValue)another;
        return val == anotherCast.getVal();
    }

    @Override
    public String toString()
    {
        return Integer.toString(this.val);
    }
}
