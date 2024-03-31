package view.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.*;
import repository.*;
import controller.*;
import view.Examples;

public class ExamplesController
{
    private ProgramController programController;

    public void setProgramController(ProgramController programController)
    {
        this.programController = programController;
    }
    
    @FXML
    private ListView<IStmt> examplesListView;

    @FXML
    private Button executeButton;

    @FXML
    public void initialize()
    {
        this.examplesListView.setItems(FXCollections.observableArrayList(Examples.getExamples()));
        this.executeButton.setOnAction(actionEvent ->
        {
            int idx = examplesListView.getSelectionModel().getSelectedIndex();
            if (idx < 0)
            {
                return;
            }
            IStmt selectedExample = Examples.getExamples()[idx];
            MyIDictionary<String, IType> curTypeEnv = new MyDictionary<>();
            try
            {
                selectedExample.typeCheck(curTypeEnv);
            }
            catch (MyException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
            PrgState prgState = new PrgState(selectedExample);
            IRepository repo = new Repository("other/logfile.txt");
            repo.addPrg(prgState);
            Controller controller = new Controller(repo);
            this.programController.setController(controller);
        });
    }
}
