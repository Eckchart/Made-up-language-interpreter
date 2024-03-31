package model;


public class AssignStmt implements IStmt
{
    private String id;  // identifier
    private IExp exp;

    public AssignStmt(String id, IExp exp)
    {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType typeVar = typeEnv.lookup(this.id);
        IType typeExp = this.exp.typeCheck(typeEnv);
        if (!typeVar.equals(typeExp))
        {
            throw new MyException("Assignment: right hand side and left hand side have different types!");
        }
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, IValue> symTable = state.getSymTable();  // shallow copy
        if (!symTable.isDefined(this.id))
        {
            throw new MyException("The used variable " + id + " was not declared before!");
        }

        IValue val = this.exp.eval(symTable, state.getHeap());
        IType typId = (symTable.lookup(this.id)).getType();
        if (!val.getType().equals(typId))
        {
            throw new MyException("Declared type of variable " + id + " and type of the assigned expression do not match!");
        }

        symTable.update(this.id, val);
        return null;
    }

    @Override
    public String toString()
    {
        return this.id + " = " + this.exp.toString();
    }
}
