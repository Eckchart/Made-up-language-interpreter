package repository;

import model.PrgState;
import java.io.IOException;
import java.util.List;


public interface IRepository
{
    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> newList);

    void addPrg(PrgState prg);

    void logPrgStateExec(PrgState givenProgram) throws IOException;
}
