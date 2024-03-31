package model;


public class SwitchStmt implements IStmt
{
    // switch (mainExp) (case exp1: stmt1) (case exp2: stmt2) (default: stmt3)
    private final IExp mainExp;
    private final IExp exp1;
    private final IStmt stmt1;
    private final IExp exp2;
    private final IStmt stmt2;
    private final IStmt stmt3;

    public SwitchStmt(IExp mainExp, IExp exp1, IStmt stmt1, IExp exp2, IStmt stmt2, IStmt stmt3)
    {
        this.mainExp = mainExp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType mainType = mainExp.typeCheck(typeEnv);
        IType type1 = exp1.typeCheck(typeEnv);
        IType type2 = exp2.typeCheck(typeEnv);
        if (mainType.equals(type1) && mainType.equals(type2))
        {
            stmt1.typeCheck(typeEnv.copy());
            stmt2.typeCheck(typeEnv.copy());
            stmt3.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else
        {
            throw new MyException("The expressions of the cases don't match with the switch's expression!");
        }
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt toPushStmt = new IfStmt(new RelExp(Operation.EQUAL, this.mainExp, this.exp1), this.stmt1,
                               new IfStmt(new RelExp(Operation.EQUAL, this.mainExp, this.exp2), this.stmt2, this.stmt3)
                           );
        exeStack.push(toPushStmt);
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("switch (%s)\n{\ncase (%s):\n%s\ncase (%s):\n%s\ndefault:\n%s\n}", mainExp, exp1, stmt1, exp2, stmt2, stmt3);
    }
}
