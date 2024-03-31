package view.gui;

import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import controller.*;
import model.*;


class Pair<T1, T2>
{
    T1 first;
    T2 second;

    public Pair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }
}


public class ProgramController
{
    private Controller controller;

    public void setController(Controller controller)
    {
        this.controller = controller;
        this.populate();
    }

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> heapTableAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> heapTableValueColumn;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTableView;

    @FXML
    private TextField nrPrgStatesTextField;

    @FXML
    private Button oneStepButton;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<Integer> prgStateIdsListView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symTableValueColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symTableVariableColumn;

    @FXML
    private TableView<Pair<String, IValue>> symbolTableView;

    private PrgState getSelectedPrgState()
    {
        if (this.controller.getPrgList().size() == 0)
        {
            return null;
        }
        int idx = this.prgStateIdsListView.getSelectionModel().getSelectedIndex();
        if (idx == -1)
        {
            // Have the first program state id selected by default.
            return this.controller.getPrgList().get(0);
        }
        return this.controller.getPrgList().get(idx);
    }
    
    @FXML
    public void initialize()
    {
        this.heapTableAddressColumn.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().first).asObject());
        this.heapTableValueColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().second.toString()));
        this.symTableVariableColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().first));
        this.symTableValueColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().second.toString()));
        this.oneStepButton.setOnAction(actionEvent ->
        {
            if (this.controller == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No example was selected.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if (this.getSelectedPrgState() != null && this.getSelectedPrgState().getExeStack().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            try
            {
                this.controller.oneStepAll();
                this.populate();
            }
            catch (MyException | InterruptedException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        this.prgStateIdsListView.setOnMouseClicked(mouseEvent -> this.populate());
    }

    private void populate()
    {
        this.populateHeap();
        this.populatePrgStateIdentifiers();
        this.populateFileTable();
        this.populateOutput();
        this.populateSymbolTable();
        this.populateExecutionStack();
    }

    private void populateHeap()
    {
        MyIHeap heap;
        if (this.controller.getPrgList().size() > 0)
        {
            heap = controller.getPrgList().get(0).getHeap();
        }
        else
        {
            heap = new MyHeap();
        }
        List<Pair<Integer, IValue>> heapTableList = new ArrayList<>();
        for (Map.Entry<Integer, IValue> entry : heap.getMappings().entrySet())
        {
            heapTableList.add(new Pair<Integer, IValue>(entry.getKey(), entry.getValue()));
        }
        this.heapTableView.setItems(FXCollections.observableList(heapTableList));
        this.heapTableView.refresh();
    }

    private void populatePrgStateIdentifiers()
    {
        List<PrgState> programStates = controller.getPrgList();
        var idList = programStates.stream().map(prgState -> prgState.getId()).collect(Collectors.toList());
        this.prgStateIdsListView.setItems(FXCollections.observableList(idList));
        this.nrPrgStatesTextField.setText(Integer.toString(programStates.size()));
    }

    private void populateFileTable()
    {
        ArrayList<String> files;
        if (controller.getPrgList().size() > 0)
        {
            files = new ArrayList<>(controller.getPrgList().get(0).getFileTable().keySet());
        }
        else
        {
            files = new ArrayList<>();
        }
        this.fileListView.setItems(FXCollections.observableArrayList(files));
    }

    private void populateOutput()
    {
        MyIList<String> output = new MyList<String>();
        if (controller.getPrgList().size() > 0)
        {
            MyIList<IValue> outputIValue = this.controller.getPrgList().get(0).getOutput();
            for (IValue val : outputIValue)
            {
                output.add(val.toString());
            }
        }

        this.outputListView.setItems(FXCollections.observableList(output.getList()));
        this.outputListView.refresh();
    }

    private void populateSymbolTable()
    {
        PrgState state = this.getSelectedPrgState();
        List<Pair<String, IValue>> symbolTableList = new ArrayList<>();
        if (state != null)
        {
            for (Map.Entry<String, IValue> entry : state.getSymTable().getMappings().entrySet())
            {
                symbolTableList.add(new Pair<String, IValue>(entry.getKey(), entry.getValue()));
            }
        }
        this.symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        this.symbolTableView.refresh();
    }

    private void populateExecutionStack()
    {
        PrgState state = this.getSelectedPrgState();
        List<String> exeStackListAsString = new ArrayList<>();
        if (state != null)
        {
            List<IStmt> stmts = new ArrayList<IStmt>();
            for (IStmt stmt : state.getExeStack())
            {
                stmts.add(stmt);
            }
            Collections.reverse(stmts);
            for (IStmt stmt : stmts)
            {
                exeStackListAsString.add(stmt.toString());
            }
        }
        this.executionStackListView.setItems(FXCollections.observableList(exeStackListAsString));
        this.executionStackListView.refresh();
    }
}
