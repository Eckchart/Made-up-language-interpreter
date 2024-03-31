package model;

import java.io.BufferedReader;
import java.io.IOException;


public class ReadFileStmt implements IStmt
{
    private IExp exp;
    private String varName;

    public ReadFileStmt(IExp exp, String varName)
    {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        if (!this.exp.typeCheck(typeEnv).equals(new StringType()))
        {
            throw new MyException("READ FILE Stmt: first argument is not a string!");
        }
        if (!typeEnv.lookup(this.varName).equals(new IntType()))
        {
            throw new MyException("READ FILE Stmt: second argument is not an integer!");
        }
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException
    {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (!symTable.isDefined(this.varName))
        {
            throw new MyException(String.format("%s is not defined.", this.varName));
        }
        IValue tmp = symTable.lookup(this.varName);
        if (!tmp.getType().equals(new IntType()))
        {
            throw new MyException(String.format("%s is NOT of type int.", this.varName));
        }
        IValue val = this.exp.eval(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new StringType()))
        {
            throw new MyException(String.format("%s doesn't evaluate to a String.", val.toString()));
        }
        StringValue valCast = (StringValue)val;
        String filename = valCast.getVal();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();  // shallow copy
        if (!fileTable.isDefined(filename))
        {
            throw new MyException(String.format("No file with name %s is opened.", filename));
        }
        BufferedReader br = fileTable.lookup(filename);  // shallow copy
        String curLine = br.readLine();
        Integer curFileVal = (curLine == null || curLine.equals("")) ? 0 : Integer.parseInt(curLine);
        symTable.update(this.varName, new IntValue(curFileVal));
        return null;
    }

    @Override
    public String toString()
    {
        return "read(" + exp.toString() + ", " + varName + ")";
    }
}

