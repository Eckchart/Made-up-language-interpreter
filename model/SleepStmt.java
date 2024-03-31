package model;

public class SleepStmt implements IStmt
{
    private final int number;

    public SleepStmt(int number)
    {
        this.number = number;
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
            exeStack.push(new SleepStmt(this.number - 1));
        }
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("sleep(%s)", number);
    }
}
