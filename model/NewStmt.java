package model;


public class NewStmt implements IStmt
{
    // something like '(type(exp))* varName = new type(exp); *varName = exp;'
    // e.g. 'int* varName = new int; *varName = exp'
    private String varName;
    private IExp exp;

    public NewStmt(String varName, IExp exp)
    {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType typeVar = typeEnv.lookup(varName);
        IType typeExp = this.exp.typeCheck(typeEnv);
        if (!typeVar.equals(new RefType(typeExp)))
        {
            throw new MyException("NEW Stmt: RHS and LHS have different types!");
        }
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, IValue> symTable = state.getSymTable();  // shallow copy
        if (!symTable.isDefined(varName))
        {
            throw new MyException(String.format("Variable %s doesn't exist.", this.varName));
        }
        IValue varValue = symTable.lookup(this.varName);
        if (!(varValue.getType() instanceof RefType))
        {
            throw new MyException(String.format("Variable %s is not a reference.", this.varName));
        }
        RefValue varValueCast = (RefValue)varValue;
        IValue expVal = exp.eval(symTable, state.getHeap());
        if (!expVal.getType().equals(varValueCast.getLocationType()))
        {
            throw new MyException(String.format("Can't initialize %s with an expression of type %s",
                                                 this.varName, expVal.getType()));
        }
        MyIHeap heap = state.getHeap();  // shallow copy
        Integer resAddress = heap.add(expVal);
        symTable.add(varName, new RefValue(resAddress, expVal.getType()));
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("new(%s, %s)", varName, exp);
    }
}
