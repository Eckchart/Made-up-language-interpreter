package model;


public class ForStmt implements IStmt
{
    // int v; for (v = exp1; v < exp2; v = exp3) do { stmt }.
    private final String v;
    private final IExp exp1;
    private final IExp exp2;
    private final IExp exp3;
    private final IStmt stmt;

    public ForStmt(String variable, IExp exp1, IExp exp2, IExp exp3, IStmt stmt)
    {
        this.v = variable;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType type1 = exp1.typeCheck(typeEnv);
        IType type2 = exp2.typeCheck(typeEnv);
        IType type3 = exp3.typeCheck(typeEnv);

        if (type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()))
        {
            return typeEnv;
        }
        else
        {
            throw new MyException("FOR STMT: The types of the expressions are not Int!");
        }
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt whileStmt = new WhileStmt(new RelExp(Operation.LESS, new VarExp(this.v), this.exp2),
                              new CompStmt(this.stmt, new AssignStmt(this.v, this.exp3))
                          );
        IStmt assignStmt = new AssignStmt(this.v, this.exp1);
        IStmt declStmt = new VarDeclStmt(this.v, new IntType());
        exeStack.push(whileStmt);
        exeStack.push(assignStmt);
        // exeStack.push(declStmt);
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("for (%s = %s; %s < %s; %s = %s)\n{\n%s\n}", v, exp1, v, exp2, v, exp3, stmt);
    }
}
