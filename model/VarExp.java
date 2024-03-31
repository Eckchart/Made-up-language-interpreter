package model;


public class VarExp implements IExp
{
    // identifier
    private String id;

    public VarExp(String id)
    {
        this.id = id;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        return typeEnv.lookup(this.id);
    }
    
    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws MyException
    {
        return symTable.lookup(id);
    }

    @Override
    public String toString()
    {
        return id;
    }
}
