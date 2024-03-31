package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.PrgState;
import java.util.ArrayList;


public class Repository implements IRepository
{
    private List<PrgState> programs;
    private String logFilePath;

    public Repository(String logFilePath)
    {
        programs = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
    }

    @Override
    public List<PrgState> getPrgList()
    {
        return this.programs;
    }

    @Override
    public void setPrgList(List<PrgState> newList)
    {
        this.programs = new ArrayList<PrgState>(newList);
    }

    @Override
    public void addPrg(PrgState prg)
    {
        this.programs.add(prg);
    }
    
    @Override
    public void logPrgStateExec(PrgState givenProgram) throws IOException
    {
        // true means that the file is opened in append mode.
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true))))
        {
            logFile.println("Programs states:");
            logFile.println(givenProgram);
        }
    }
}
