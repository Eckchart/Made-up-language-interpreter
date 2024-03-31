package model;


public class WhileStmt implements IStmt
{
    // while (exp) do stmt.
    private IExp exp;
    private IStmt stmt;

    public WhileStmt(IExp exp, IStmt stmt)
    {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType typeExp = this.exp.typeCheck(typeEnv);
        if (!typeExp.equals(new BoolType()))
        {
            throw new MyException("WHILE Stmt: condition is not of bool type!");
        }
        this.stmt.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, IValue> symTable = state.getSymTable();  // shallow copy
        IValue val = this.exp.eval(symTable, state.getHeap());
        if (!val.getType().equals(new BoolType()))
        {
            throw new MyException("WHILE Stmt: condition is not of bool type!");
        }
        BoolValue cond = (BoolValue)val;
        MyIStack<IStmt> exeStack = state.getExeStack();  // shallow copy
        if (cond.getVal())
        {
            exeStack.push(this);
            exeStack.push(this.stmt);
        }
        return null;
    }

    @Override
    public String toString()
    {
        return "while (" + exp.toString() + ")\n{\n" + stmt.toString() + "\n}";
    }
}
