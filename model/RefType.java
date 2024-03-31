package model;


public class RefType implements IType
{
    private IType innerType;

    public RefType(IType innerType)
    {
        this.innerType = innerType;
    }

    public IType getInnerType()
    {
        return this.innerType;
    }

    @Override
    public boolean equals(Object another)
    {
        if (another instanceof RefType)
        {
            RefType anotherCast = (RefType)another;
            return this.innerType.equals(anotherCast.getInnerType());
        }
        return false;
    }

    @Override
    public IValue getDefault()
    {
        return new RefValue(0, this.innerType);
    }

    @Override
    public String toString()
    {
        return "ref " + this.innerType.toString();
    }
}
