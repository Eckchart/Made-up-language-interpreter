package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class OpenRFileStmt implements IStmt
{
    private IExp exp;

    public OpenRFileStmt(IExp exp)
    {
        this.exp = exp;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        if (!this.exp.typeCheck(typeEnv).equals(new StringType()))
        {
            throw new MyException("OPEN READ FILE Stmt: argument is not a string!");
        }
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException
    {
        IValue val = this.exp.eval(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new StringType()))
        {
            throw new MyException(String.format("%s doesn't evaluate to a String.", val.toString()));
        }
        StringValue valCast = (StringValue)val;
        String filename = valCast.getVal();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();  // shallow copy
        if (fileTable.isDefined(filename))
        {
            throw new MyException(String.format("%s is already opened.", filename));
        }
        BufferedReader curBr = new BufferedReader(new FileReader(filename));
        fileTable.add(filename, curBr);
        return null;
    }

    @Override
    public String toString()
    {
        return "open(" + exp.toString() + ")";
    }
}
