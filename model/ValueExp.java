package model;


public class ValueExp implements IExp
{
    private IValue val;

    public ValueExp(IValue val)
    {
        this.val = val;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        return this.val.getType();
    }
    
    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap heap) throws MyException
    {
        return val;
    }

    @Override
    public String toString()
    {
        return val.toString();
    }
}
