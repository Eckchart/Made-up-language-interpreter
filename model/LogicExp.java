package model;


public class LogicExp implements IExp
{
    private IExp lhs, rhs;
    private Operation op;

    public LogicExp(IExp lhs, IExp rhs, Operation op)
    {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType lhsType = this.lhs.typeCheck(typeEnv);
        IType rhsType = this.rhs.typeCheck(typeEnv);
        if (!lhsType.equals(new BoolType()))
        {
            throw new MyException("First operand is not a boolean!");
        }
        if (!rhsType.equals(new BoolType()))
        {
            throw new MyException("Second operand is not a boolean!");
        }
        return new BoolType();
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap heap) throws MyException
    {
        IValue v1, v2;
        v1 = this.lhs.eval(tbl, heap);
        if (!v1.getType().equals(new BoolType()))
        {
            throw new MyException("First operand is not a boolean!");
        }
        v2 = this.rhs.eval(tbl, heap);
        if (!v2.getType().equals(new BoolType()))
        {
            throw new MyException("Second operand is not a boolean!");
        }
        
        BoolValue boolValCast1 = (BoolValue)v1, boolValCast2 = (BoolValue)v2;
        boolean actualVal1 = boolValCast1.getVal(), actualVal2 = boolValCast2.getVal();
        switch (this.op)
        {
        case AND:
            return new BoolValue(actualVal1 && actualVal2);
        case OR:
            return new BoolValue(actualVal1 || actualVal2);
        default:
            throw new MyException(String.format("Invalid operator (%s) between '%s' and '%s'.",
                                                this.op, this.lhs.toString(), this.rhs.toString()));
        }
    }

    @Override
    public String toString()
    {
        return "Logical expression: " + lhs.toString() + " " + op + " " + rhs.toString();
    }
}
