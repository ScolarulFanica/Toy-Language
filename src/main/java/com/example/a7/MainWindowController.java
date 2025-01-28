package com.example.a7;

import com.example.a7.tableentries.HeapTableEntry;
import com.example.a7.tableentries.SymTableEntry;
import controller.Controller;
import exceptions.ExpressionException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.adt.ISempahoreTable;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.StringValue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private Controller program;
    private String selectedThread;
    @FXML
    private ListView<String> threadsList;
    @FXML
    private ListView<String> execStackList;
    @FXML
    private TableView<SymTableEntry> symTable;
    @FXML
    private TableColumn<SymTableEntry, String> symTableIdColumn;
    @FXML
    private TableColumn<SymTableEntry, String> symTableValColumn;
    @FXML
    private ListView<String> outputList;
    @FXML
    private ListView<String> fileTableList;
    @FXML
    private TableView<HeapTableEntry> heapTable;
    @FXML
    private TableColumn<HeapTableEntry, String> addressHeapTableColumn;
    @FXML
    private TableColumn<HeapTableEntry, String> valueHeapTableColumn;
    @FXML
    private TextField threadCountText;
    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer,List<Integer>>>> semaphoreTable;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> semaphoreIndexColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> semaphoreValueColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> semaphoreThreadsColumn;

    public void loadProgram(Controller programController) {
        this.program = programController;
        this.updateWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.symTableIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.symTableValColumn.setCellValueFactory(cellData -> cellData.getValue().valProperty());
        this.addressHeapTableColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        this.valueHeapTableColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        semaphoreIndexColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        semaphoreValueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getKey()));
        semaphoreThreadsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getValue().toString()));
    }

    private boolean isProgramCompleted(){
        return this.program.getRepo().getPrgList().isEmpty();
    }

    public void updateWindow(){
        if(this.isProgramCompleted()){
            return;
        }
        this.populateThreadCountText();
        this.populateThreadsList();
        this.populateExecutionStackList();
        this.populateSymbolTable();
        this.populateOutputList();
        this.populateFileTable();
        this.populateHeapTable();
        this.populateSemaphoreTable();
    }

    private void removeJustCompletedPrograms(){
        this.populateThreadsList();
        this.populateExecutionStackList();
        this.populateThreadCountText();
    }

    private void populateThreadsList(){
        if(this.isProgramCompleted()){
            this.threadsList.getItems().clear();
            return;
        }
        int selectedIndex = this.threadsList.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0){
            selectedIndex = 0;
        }
        this.threadsList.getItems().clear();
        this.program.getRepo().getPrgList().forEach(p->{this.threadsList.getItems().add(String.valueOf(p.getId()));});
        if(selectedIndex >= this.threadsList.getItems().size()){
            selectedIndex = 0;
        }
        this.threadsList.getSelectionModel().select(selectedIndex);
        this.selectedThread = this.threadsList.getItems().get(selectedIndex);
    }

    private void populateExecutionStackList() {
        this.execStackList.getItems().clear();
        for (PrgState programState : this.program.getRepo().getPrgList()) {
            if (String.valueOf(programState.getId()).equals(this.selectedThread)) {
                for (IStatement statement : programState.getExecStack().reversed()) {
                    this.execStackList.getItems().add(statement.toString());
                }
                break;
            }
        }
    }

    private void populateSymbolTable() {
        this.symTable.getItems().clear();
        for (PrgState programState : this.program.getRepo().getPrgList()) {
            if (String.valueOf(programState.getId()).equals(this.selectedThread)) {
                for (String key : programState.getSymTable().keys()) {
                    try {
                        SymTableEntry entry = new SymTableEntry(key, programState.getSymTable().get(key));
                        this.symTable.getItems().add(entry);
                    } catch (ExpressionException e){
                        System.out.println(e);
                    }
                }
                break;
            }
        }
    }

    private void populateOutputList() {
        this.outputList.getItems().clear();
        PrgState programState = this.program.getRepo().getPrgList().get(0);
        for (String output : programState.getOutputList().values()) {
            this.outputList.getItems().add(output);
        }
    }

    private void populateFileTable() {
        this.fileTableList.getItems().clear();
        PrgState programState = this.program.getRepo().getPrgList().get(0);
        for (StringValue file : programState.getFileTable().keys()) {
            this.fileTableList.getItems().add(file.toString());
        }
    }

    private void populateHeapTable() {
        this.heapTable.getItems().clear();
        for (PrgState programState : this.program.getRepo().getPrgList()) {
            for (Integer address : programState.getHeap().keys()) {
                try {
                    HeapTableEntry entry = new HeapTableEntry(address, programState.getHeap().get(address));
                    this.heapTable.getItems().add(entry);
                } catch (ExpressionException e)
                {
                    System.out.println(e);
                }
            }
            break;
        }
    }

    private void populateSemaphoreTable(){
        this.semaphoreTable.getItems().clear();
        for(PrgState programState : this.program.getRepo().getPrgList()){
            ISempahoreTable semaphoreTable = programState.getSempahoreTable();
            synchronized (semaphoreTable) {
                for (Map.Entry<Integer,Pair<Integer,List<Integer>>>entry: semaphoreTable.getContent().entrySet()) {
                    this.semaphoreTable.getItems().add(entry);
                }
            }
            break;
        }
    }

    private void populateThreadCountText() {
        String threadCountText = "Thread Count: ";
        threadCountText += this.program.getRepo().getPrgList().size();
        this.threadCountText.setText(threadCountText);
    }

    public void oneStep() {
        List<PrgState> programStates = this.program.removeCompletedPrg(
                this.program.getRepo().getPrgList());

        if (programStates.isEmpty()) {
            this.program.getExecutor().shutdownNow();
            return;
        }

        this.program.getRepo().getHeap().setContent(
                this.program.garbageCollector(
                        this.program.getAddressesFromSymbolTable(this.program.getRepo().getSymbolTable().values()),
                        this.program.getRepo().getHeap().getContent()
                )
        );

        try {
            this.program.oneStepForAll(programStates);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.updateWindow();

        programStates = this.program.removeCompletedPrg(
                this.program.getRepo().getPrgList());
        this.program.getRepo().setPrgList(programStates);

        this.removeJustCompletedPrograms();
    }

    public void allSteps() {
        while (!this.program.getRepo().getPrgList().isEmpty()) {
            this.oneStep();
        }
    }


    public void switchToSelectWindow(javafx.event.ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(GuiInterpreter.class.getResource("select-window.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setTitle("Toy Language Interpreter - Execution Window");
        stage.show();
    }
}
