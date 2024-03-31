package model;


public class NotExp implements IExp
{
    private final IExp exp;

    public NotExp(IExp exp)
    {
        this.exp = exp;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        return this.exp.typeCheck(typeEnv);
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException
    {
        BoolValue value = (BoolValue)this.exp.eval(table, heap);
        return value.getVal() ? new BoolValue(false) : new BoolValue(true);
    }

    @Override
    public String toString()
    {
        return String.format("!(%s)", exp);
    }
}
