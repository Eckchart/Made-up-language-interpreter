package model;


public interface IExp
{
    IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws MyException;

    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}
