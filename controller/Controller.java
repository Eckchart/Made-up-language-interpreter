package controller;

import model.*;
import java.io.IOException;
import java.util.stream.Collectors;
import repository.IRepository;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


public class Controller
{
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo)
    {
        this.repo = repo;
    }

    public List<PrgState> getPrgList()
    {
        return this.repo.getPrgList();
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddresses, Map<Integer, IValue> heap)
    {
        Map<Integer, IValue> newHeap = new HashMap<Integer, IValue>();
        for (Integer address : symTableAddresses)
        {
            Integer curAddress = address;
            while (true)
            {
                if (!heap.containsKey(curAddress))
                {
                    break;
                }
                newHeap.put(curAddress, heap.get(curAddress));
                if (heap.get(curAddress).getType().equals(new IntType()))
                {
                    break;
                }
                RefValue cast = (RefValue)heap.get(curAddress);
                curAddress = cast.getAddress();
            }
        }
        return newHeap;
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues)
    {
        return symTableValues.stream()
                             .filter(v -> v instanceof RefValue)
                             .map(v -> { RefValue v1 = (RefValue)v; return v1.getAddress(); })
                             .collect(Collectors.toList());
    }


    public List<PrgState> removeCompletedPrgs(List<PrgState> inPrgList)
    {
        List<PrgState> toRet = inPrgList.stream()
                                        .filter(p -> p.isNotCompleted())
                                        .collect(Collectors.toList());
        if (toRet.isEmpty() && !inPrgList.isEmpty())
        {
            toRet.add(inPrgList.get(0));
        }
        return toRet;
    }


    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException, InterruptedException
    {
        prgList.forEach(prg ->
        {
            try
            {
                this.repo.logPrgStateExec(prg);
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                                            .map(prg -> (Callable<PrgState>)(() -> { return prg.oneStep(); }))
                                            .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList)
                                            .stream()
                                            .map(future ->
                                            {
                                                try
                                                {
                                                    return future.get();
                                                }
                                                catch (ExecutionException | InterruptedException e)
                                                {
                                                    System.out.println(e.getMessage());
                                                    System.exit(1);
                                                    return null;
                                                }
                                            })
                                            .filter(p -> p != null)
                                            .collect(Collectors.toList());

        prgList.addAll(newPrgList);

        this.repo.setPrgList(prgList);

        prgList.forEach(prg ->
        {
            try
            {
                this.repo.logPrgStateExec(prg);
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });
    }

    public void oneStepAll() throws MyException, InterruptedException
    {
        this.executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = this.removeCompletedPrgs(this.repo.getPrgList());
        this.oneStepForAllPrg(prgList);
        List<Integer> allSymTableValues = prgList.stream()
                                                 .map(prg -> this.getAddrFromSymTable(prg.getSymTable().getMappings().values()))
                                                 .flatMap(lists -> lists.stream())
                                                 .toList();
        MyIHeap heap = prgList.get(0).getHeap();
        heap.setMappings(this.safeGarbageCollector(allSymTableValues, heap.getMappings()));

        prgList = this.removeCompletedPrgs(this.repo.getPrgList());
        executor.shutdownNow();
        this.repo.setPrgList(prgList);
    }
}
