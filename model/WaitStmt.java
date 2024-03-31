package model;


public class WaitStmt implements IStmt
{
    private final int number;

    public WaitStmt(int value)
    {
        this.number = value;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        if (this.number > 0)
        {
            MyIStack<IStmt> exeStack = state.getExeStack();
            exeStack.push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(this.number))), new WaitStmt(this.number - 1)));
        }
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("wait (%s)", number);
    }
}
