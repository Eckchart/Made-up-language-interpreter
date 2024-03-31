package model;


public class ForkStmt implements IStmt
{
    private IStmt stmt;

    public ForkStmt(IStmt stmt)
    {
        this.stmt = stmt;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        this.stmt.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    // basically make a new thread, so a new PrgState.
    @Override
    public PrgState execute(PrgState state)
    {
        return new PrgState(new MyStack<IStmt>(), state.getSymTable().copy(),
                state.getOutput(), state.getFileTable(), state.getHeap(), this.stmt);
    }

    @Override
    public String toString()
    {
        return String.format("fork (\n%s\n)", stmt);
    }
}
