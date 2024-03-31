package model;


public class VarDeclStmt implements IStmt
{
    private String name;
    private IType type;
    
    public VarDeclStmt(String name, IType type)
    {
        this.name = name;
        this.type = type;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        typeEnv.add(this.name, this.type);
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (symTable.isDefined(this.name))
        {
            throw new MyException("Variable " + name + " is already defined.");
        }

        // Variable 'name' was not given a value yet, so we just keep it
        // in the symbol table with the default value for that type.
        symTable.add(this.name, this.type.getDefault());
        return null;
    }

    @Override
    public String toString()
    {
        return type.toString() + " " + name;
    }
}
