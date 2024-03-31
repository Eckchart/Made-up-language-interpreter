package model;


public class IfStmt implements IStmt
{
    // if 'exp' then 'thenStmt' else 'elseStmt'
    private IExp exp;
    private IStmt thenStmt;
    private IStmt elseStmt;

    public IfStmt(IExp exp, IStmt thenStmt, IStmt elseStmt)
    {
        this.exp = exp;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType typeExp = this.exp.typeCheck(typeEnv);
        if (!typeExp.equals(new BoolType()))
        {
            throw new MyException("The condition of IF is not a boolean type!");
        }
        this.thenStmt.typeCheck(typeEnv.copy());
        this.elseStmt.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        IValue val = this.exp.eval(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new BoolType()))
        {
            throw new MyException("Expression is NOT a boolean type.");
        }

        BoolValue cond = (BoolValue)val;
        if (cond.getVal())
        {
            state.getExeStack().push(this.thenStmt);
        }
        else
        {
            state.getExeStack().push(this.elseStmt);
        }
        return null;
    }

    @Override
    public String toString()
    {
        return "if (" + exp.toString() + ")\n(" + thenStmt.toString()
               + ")\nelse\n(" + elseStmt.toString() + ")";
    }
}
