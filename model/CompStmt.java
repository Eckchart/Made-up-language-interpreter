package model;


public class CompStmt implements IStmt
{
    private IStmt first, second;

    public CompStmt(IStmt first, IStmt second)
    {
        this.first = first;
        this.second = second;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        MyIDictionary<String, IType> typeEnv1 = this.first.typeCheck(typeEnv);
        MyIDictionary<String, IType> typeEnv2 = this.second.typeCheck(typeEnv1);
        return typeEnv2;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIStack<IStmt> stk = state.getExeStack();  // shallow copy
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public String toString()
    {
        return first.toString() + ";\n" + second.toString();
    }
}
