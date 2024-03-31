package model;


// give new value to pointer
public class WriteHeapStmt implements IStmt
{
    // so still similar to *varName = exp, but 'varName' is already defined in this case.
    private String varName;
    private IExp exp;

    public WriteHeapStmt(String varName, IExp exp)
    {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType typeVar = typeEnv.lookup(this.varName);
        IType typeExp = this.exp.typeCheck(typeEnv);
        if (!typeVar.equals(new RefType(typeExp)))
        {
            throw new MyException("WRITE HEAP Stmt: LHS AND RHS have different types!");
        }
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, IValue> symTable = state.getSymTable();  // shallow copy
        if (!symTable.isDefined(this.varName))
        {
            throw new MyException(String.format("%s was not previously defined.", this.varName));
        }
        IValue varVal = symTable.lookup(this.varName);
        if (!(varVal.getType() instanceof RefType))
        {
            throw new MyException(String.format("%s is not a reference.", this.varName));
        }
        RefValue varCast = (RefValue)varVal;
        MyIHeap heap = state.getHeap();  // shallow copy
        if (heap.lookup(varCast.getAddress()) == null)
        {
            // We should never enter this if-block
            throw new MyException(String.format("Address of %s doesn't exist in the heap table.", this.varName));
        }
        IValue expEval = this.exp.eval(symTable, heap);
        if (!expEval.getType().equals(varCast.getLocationType()))
        {
            throw new MyException(String.format("Can't assign an expression of type %s to %s.",
                                                expEval.getType(), varCast));
        }
        heap.update(varCast.getAddress(), expEval);
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("writeHeap(%s, %s)", varName, exp);
    }
}
