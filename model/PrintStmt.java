package model;


public class PrintStmt implements IStmt
{
    private IExp exp;
    
    public PrintStmt(IExp exp)
    {
        this.exp = exp;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        this.exp.typeCheck(typeEnv);
        return typeEnv;
    }
    
    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        state.getOutput().add(this.exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public String toString()
    {
        return "print(" + exp.toString() + ")";
    }
}
