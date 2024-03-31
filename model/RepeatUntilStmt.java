package model;


public class RepeatUntilStmt implements IStmt
{
    // repeat { stmt } until (exp)
    private final IStmt stmt;
    private final IExp exp;

    public RepeatUntilStmt(IStmt stmt, IExp exp)
    {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType type = exp.typeCheck(typeEnv);
        if (type.equals(new BoolType()))
        {
            stmt.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else
        {
            throw new MyException("RepeatUntilSTMT: the given expression is not of boolean type!");
        }
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt toPushStmt = new CompStmt(stmt, new WhileStmt(new NotExp(exp), stmt));
        exeStack.push(toPushStmt);
        return null;
    }


    @Override
    public String toString()
    {
        return String.format("repeat\n{\n%s\n} until (%s)", stmt, exp);
    }
}
