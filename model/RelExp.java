package model;


public class RelExp implements IExp
{
    private IExp lhs, rhs;
    private Operation op;

    public RelExp(Operation op, IExp lhs, IExp rhs)
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
        if (!lhsType.equals(new IntType()))
        {
            throw new MyException("First operand is not an integer!");
        }
        if (!rhsType.equals(new IntType()))
        {
            throw new MyException("Second operand is not an integer!");
        }
        return new BoolType();
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws MyException
    {
        IValue v1, v2;
        v1 = this.lhs.eval(symTable, heap);
        if (!v1.getType().equals(new IntType()))
        {
            throw new MyException("First operand is not an integer!");
        }
        v2 = this.rhs.eval(symTable, heap);
        if (!v2.getType().equals(new IntType()))
        {
            throw new MyException("Second operand is not an integer!");
        }

        IntValue intValCast1 = (IntValue)v1, intValCast2 = (IntValue)v2;
        int actualVal1 = intValCast1.getVal(), actualVal2 = intValCast2.getVal();
        switch (this.op)
        {
            case LESS:
                return new BoolValue(actualVal1 < actualVal2);
            case LEQ:
                return new BoolValue(actualVal1 <= actualVal2);
            case EQUAL:
                return new BoolValue(actualVal1 == actualVal2);
            case UNEQUAL:
                return new BoolValue(actualVal1 != actualVal2);
            case GREATER:
                return new BoolValue(actualVal1 > actualVal2);
            case GEQ:
                return new BoolValue(actualVal1 >= actualVal2);
            default:
                throw new MyException(String.format("Invalid operator (%s) between '%s' and '%s'.",
                                                    this.op, this.lhs.toString(), this.rhs.toString()));
        }
    }

    @Override
    public String toString()
    {
        return lhs.toString() + " " + op + " " + rhs.toString();
    }
}
