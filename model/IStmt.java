package model;

import java.io.IOException;


public interface IStmt
{
    PrgState execute(PrgState state) throws MyException, IOException;

    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}
