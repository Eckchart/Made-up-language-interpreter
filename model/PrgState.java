package model;

import java.io.BufferedReader;
import java.io.IOException;


public class PrgState
{
    private MyIStack<IStmt> exeStack;  // NOT SHARED AMONG THREADS
    private MyIDictionary<String, IValue> symTable;  // NOT SHARED AMONG THREADS
    private MyIList<IValue> output;  // SHARED AMONG ALL THREADS
    private MyIDictionary<String, BufferedReader> fileTable;  // SHARED AMONG ALL THREADS
    private MyIHeap heap;  // SHARED AMONG ALL THREADS
    private static int lastId = 0;
    private static Object lock = new Object();
    private int id;

    public PrgState(IStmt originalProgram)
    {
        this.exeStack = new MyStack<>();
        this.symTable = new MyDictionary<>();
        this.output = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.heap = new MyHeap();
        this.exeStack.push(originalProgram);
        id = PrgState.getNewId();
    }
    
    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, IValue> symTable,
                    MyIList<IValue> output, MyIDictionary<String, BufferedReader> fileTable,
                    MyIHeap heap, IStmt originalProgram)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.exeStack.push(originalProgram);
        id = PrgState.getNewId();
    }

    private static int getNewId()
    {
        int res;
        synchronized (lock)
        {
            ++PrgState.lastId;
            res = PrgState.lastId;
        }
        return res;
    }

    public MyIStack<IStmt> getExeStack()
    {
        return this.exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable()
    {
        return this.symTable;
    }

    public MyIList<IValue> getOutput()
    {
        return this.output;
    }

    public MyIDictionary<String, BufferedReader> getFileTable()
    {
        return this.fileTable;
    }

    public MyIHeap getHeap()
    {
        return this.heap;
    }

    public int getId()
    {
        return this.id;
    }

    public void setHeap(MyIHeap heap)
    {
        this.heap = new MyHeap(heap.getMappings());
    }

    public boolean isNotCompleted()
    {
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException, IOException
    {
        if (this.exeStack.isEmpty())
        {
            throw new MyException("Execution stack is empty.");
        }
        IStmt curStmt = exeStack.pop();
        return curStmt.execute(this);
    }


    @Override
    public String toString()
    {
        String exeStackString = "";
        for (IStmt stmt : this.exeStack)
        {
            exeStackString = "[" + stmt.toString() + "]\n" + exeStackString;
        }

        String symTableString = "";
        for (String key : this.symTable.keySet())
        {
            symTableString = symTableString.concat("[" + key + " = " + this.symTable.lookup(key).toString() + "]\n");
        }

        String outputString = "";
        for (IValue val : this.output)
        {
            outputString = outputString.concat(val.toString() + "\n");
        }

        String fileTableString = "";
        for (String filename : this.fileTable.keySet())
        {
            fileTableString = fileTableString.concat("[" + filename + "]\n");
        }

        String heapTableString = "";
        for (Integer address : this.heap.getMappings().keySet())
        {
            heapTableString = heapTableString.concat("[" + String.valueOf(address) + " -> " + this.heap.lookup(address).toString() + "]\n");
        }

        return String.format("ID: %d\n-------------\nExecution stack:\n%s-------------\nSymbol table:\n%s-------------\nOutput:\n%s-------------\nFile table:\n%s-------------\nHeap table:\n%s-------------\n",
                             id, exeStackString, symTableString, outputString, fileTableString, heapTableString);
    }
}
