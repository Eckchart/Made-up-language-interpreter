package model;


public class RefValue implements IValue
{
    private int address;
    private IType locationType;

    public RefValue(int address, IType locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress()
    {
        return this.address;
    }

    public IType getLocationType()
    {
        return this.locationType;
    }

    @Override
    public IType getType()
    {
        return new RefType(this.locationType);
    }

    @Override
    public boolean equals(Object another)
    {
        if (!(another instanceof RefValue))
        {
            return false;
        }
        RefValue anotherCast = (RefValue)another;
        return this.getType().equals(anotherCast.getType());
    }

    @Override
    public String toString()
    {
        return "Ref value (" + Integer.toString(address) + ", " + locationType.toString() + ")";
    }
}
