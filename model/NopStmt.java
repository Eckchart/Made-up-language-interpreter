package model;


public class NopStmt implements IStmt
{
    @Override
    public PrgState execute(PrgState state)
    {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "Nop";
    }
};
