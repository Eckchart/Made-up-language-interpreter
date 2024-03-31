package model;


// dereferencing
public class ReadHeapExp implements IExp
{
    private IExp exp;

    public ReadHeapExp(IExp exp)
    {
        this.exp = exp;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException
    {
        IType type = this.exp.typeCheck(typeEnv);
        if (!(type instanceof RefType))
        {
            throw new MyException(String.format("%s is not a reference", this.exp));
        }
        RefType refCast = (RefType)type;
        return refCast.getInnerType();
    }
    
    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws MyException
    {
        IValue expEval = this.exp.eval(symTable, heap);
        if (!(expEval instanceof RefValue))
        {
            throw new MyException(String.format("%s is not a reference", expEval));
        }
        RefValue refCast = (RefValue)expEval;

        // returns null if the given key doesn't exist in the dictionary.
        IValue val = heap.lookup(refCast.getAddress());
        if (val == null)
        {
            throw new MyException(String.format("Address of %s not present in the heap table!", refCast));
        }
        return val;
    }

    @Override
    public String toString()
    {
        return String.format("readHeap(%s)", exp);
    }
}
