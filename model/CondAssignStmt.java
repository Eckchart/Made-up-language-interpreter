package model;


public class CondAssignStmt implements IStmt
{
    // variable = (exp1 ? exp2 : exp3)
    private final String variable;
    private final IExp exp1;
    private final IExp exp2;
    private final IExp exp3;

    public CondAssignStmt(String variable, IExp exp1, IExp exp2, IExp exp3)
    {
        this.variable = variable;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType variableIType = (new VarExp(variable)).typeCheck(typeEnv);
        IType type1 = exp1.typeCheck(typeEnv);
        IType type2 = exp2.typeCheck(typeEnv);
        IType type3 = exp3.typeCheck(typeEnv);
        if (type1.equals(new BoolType()) && type2.equals(variableIType) && type3.equals(variableIType))
        {
            return typeEnv;
        }
        else
        {
            throw new MyException("CondAssignStmt: there are non-matching types for the given expressions!");
        }
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt toPushStmt = new IfStmt(this.exp1, new AssignStmt(this.variable, this.exp2), new AssignStmt(this.variable, this.exp3));
        exeStack.push(toPushStmt);
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("%s = (%s ? %s : %s)", variable, exp1, exp2, exp3);
    }
}
