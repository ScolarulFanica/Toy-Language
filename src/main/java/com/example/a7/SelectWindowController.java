package com.example.a7;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.adt.*;
import model.expressions.*;
import model.state.PrgState;
import model.statements.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectWindowController implements Initializable {
    private final List<PrgState> programs = new ArrayList<>();
    @FXML
    private ListView<String> selectProgramsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.createPrograms();
        this.populateSelectProgramsList();
    }

    private void createPrograms() {
        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new StringType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),new PrintStatement(new VariableExpression("v"))));
        try{ex1.typecheck(new MyDictionary<String, IType>());
            PrgState crtPrgState1 = new PrgState(ex1, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(),new Heap(), new SemaphoreTable());
            this.programs.add(crtPrgState1);
        } catch(Exception e){System.out.println(e);}


        IStatement ex2 = new CompStatement(
                new VarDeclStatement("a", new IntType()),
                new CompStatement(
                        new VarDeclStatement("b",new IntType()),
                        new CompStatement(
                                new AssignStatement("a", new ArithExpression(
                                        new ValueExpression(new IntValue(2)),
                                        new ArithExpression(
                                                new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)),
                                                ArithmeticalOperation.MULTIPLY
                                        ),
                                        ArithmeticalOperation.PLUS
                                )),
                                new CompStatement(
                                        new AssignStatement("b", new ArithExpression(
                                                new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)),
                                                ArithmeticalOperation.PLUS
                                        )),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        try{ex2.typecheck(new MyDictionary<String, IType>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState2 = new PrgState(ex2, new MyStack<>(), new MyDictionary<String, IValue>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(), new Heap(), new SemaphoreTable());


        IStatement ex3 = new CompStatement(
                new VarDeclStatement("a", new BoolType()),
                new CompStatement(new VarDeclStatement("v",new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression( new BoolValue(true))),
                                new CompStatement(
                                        new IfStatement(
                                                new VariableExpression("a"), new AssignStatement(
                                                "v", new ValueExpression(new IntValue(2))), new AssignStatement(
                                                "v", new ValueExpression(new IntValue(3)
                                        ))), new PrintStatement(new VariableExpression("v"))
                                ))));
        try{ex3.typecheck(new MyDictionary<String, IType>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState3 = new PrgState(ex3, new MyStack<>(), new MyDictionary<String, IValue>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(), new Heap(), new SemaphoreTable());


        IStatement ex4 = new CompStatement(
                new VarDeclStatement("varf", new StringType()), new CompStatement(
                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompStatement(
                new OpenRFile(new VariableExpression("varf")), new CompStatement(
                new VarDeclStatement("varc", new IntType()), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CloseRFile(new VariableExpression("varf"))))))))));
        try{ex4.typecheck(new MyDictionary<String, IType>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState4 = new PrgState(ex4, new MyStack<>(), new MyDictionary<String, IValue>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(), new Heap(), new SemaphoreTable());


        IStatement ex5 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );
        try{ex5.typecheck(new MyDictionary<String, IType>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState5 = new PrgState(ex5, new MyStack<>(), new MyDictionary<String, IValue>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(), new Heap(), new SemaphoreTable());


        IStatement ex6 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                new CompStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithExpression(
                                                        new ReadHeap(new VariableExpression("v")),
                                                        new ValueExpression(new IntValue(5)),
                                                        ArithmeticalOperation.PLUS
                                                )
                                        )
                                )
                        )
                )
        );
        try{ex6.typecheck(new MyDictionary<String, IType>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState6 = new PrgState(ex6, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new Heap(), new SemaphoreTable());


        IStatement ex7 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(
                                                        new ReadHeap(
                                                                new ReadHeap(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try{ex7.typecheck(new MyDictionary<String, IType>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState7 = new PrgState(ex7, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new Heap(), new SemaphoreTable());


        IStatement ex8 = new CompStatement(
                new VarDeclStatement("v", new IntType()),
                new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                RelationalOperation.GREATER
                                        ),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v",
                                                        new ArithExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntValue(1)),
                                                                ArithmeticalOperation.MINUS
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
        try{ex8.typecheck(new MyDictionary<String, IType>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState8 = new PrgState(ex8, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new Heap(), new SemaphoreTable());


        IStatement ex9 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeap(
                                                        new ReadHeap(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        try{ex9.typecheck(new MyDictionary<String, IType>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState9 = new PrgState(ex9, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new Heap(), new SemaphoreTable());


        IStatement ex10 = new CompStatement(
                new VarDeclStatement("v", new IntType()),
                new CompStatement(
                        new VarDeclStatement("a", new RefType(new IntType())),
                        new CompStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompStatement(
                                                new ForkStatement(
                                                        new CompStatement(
                                                                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompStatement(
                                                                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeap(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeap(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        try{ex10.typecheck(new MyDictionary<String, IType>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState10 = new PrgState(ex10, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new Heap(), new SemaphoreTable());

        IStatement ex11 = new CompStatement(
                new VarDeclStatement("a",new IntType()),
                new CompStatement(
                        new VarDeclStatement("b",new IntType()),
                        new CompStatement(
                                new VarDeclStatement("c",new IntType()),
                                new CompStatement(
                                        new AssignStatement("a", new ValueExpression(new IntValue(1))),
                                        new CompStatement(
                                                new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompStatement(
                                                        new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompStatement(
                                                                new SwitchStatement(
                                                                        new ArithExpression(
                                                                                new VariableExpression("a"),
                                                                                new ValueExpression(new IntValue(10)),
                                                                                ArithmeticalOperation.MULTIPLY
                                                                        ),
                                                                        new ArithExpression(
                                                                                new VariableExpression("b"),
                                                                                new VariableExpression("c"),
                                                                                ArithmeticalOperation.MULTIPLY
                                                                        ),
                                                                        new CompStatement(
                                                                                new PrintStatement(new VariableExpression("a")),
                                                                                new PrintStatement(new VariableExpression("b"))
                                                                        ),
                                                                        new ValueExpression(new IntValue(10)),
                                                                        new CompStatement(
                                                                                new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                new PrintStatement(new ValueExpression(new IntValue(200)))
                                                                        ),
                                                                        new PrintStatement(new ValueExpression(new IntValue(300)))
                                                                ),
                                                                new PrintStatement(new ValueExpression(new IntValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        try{ex11.typecheck(new MyDictionary<>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState11 = new PrgState(ex11,new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new Heap(), new SemaphoreTable());


        IStatement ex12 = new CompStatement(
                new VarDeclStatement("v1", new RefType(new IntType())),
                new CompStatement(
                        new VarDeclStatement("cnt", new IntType()),
                        new CompStatement(
                                new HeapAllocationStatement("v1", new ValueExpression(new IntValue(1))),
                                new CompStatement(
                                        new CreateSemaphoreStatement("cnt", new ReadHeap(new VariableExpression("v1"))),
                                        new CompStatement(
                                                new ForkStatement(
                                                        new CompStatement(
                                                                new AquireStatement("cnt"),
                                                                new CompStatement(
                                                                        new WriteHeapStatement("v1", new ArithExpression(
                                                                                new ReadHeap(new VariableExpression("v1")),
                                                                                new ValueExpression(new IntValue(10)),
                                                                                ArithmeticalOperation.MULTIPLY
                                                                        )),
                                                                        new CompStatement(
                                                                                new PrintStatement(new ReadHeap(new VariableExpression("v1"))),
                                                                                new ReleaseStatement("cnt")
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStatement(
                                                        new ForkStatement(
                                                                new CompStatement(
                                                                        new AquireStatement("cnt"),
                                                                        new CompStatement(
                                                                                new WriteHeapStatement("v1", new ArithExpression(
                                                                                        new ReadHeap(new VariableExpression("v1")),
                                                                                        new ValueExpression(new IntValue(10)),
                                                                                        ArithmeticalOperation.MULTIPLY
                                                                                )),
                                                                                new CompStatement(
                                                                                        new WriteHeapStatement("v1", new ArithExpression(
                                                                                                new ReadHeap(new VariableExpression("v1")),
                                                                                                new ValueExpression(new IntValue(2)),
                                                                                                ArithmeticalOperation.MULTIPLY
                                                                                        )),
                                                                                        new CompStatement(
                                                                                                new PrintStatement(new ReadHeap(new VariableExpression("v1"))),
                                                                                                new ReleaseStatement("cnt")
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStatement(
                                                                new AquireStatement("cnt"),
                                                                new CompStatement(
                                                                        new PrintStatement(new ArithExpression(
                                                                                new ReadHeap(new VariableExpression("v1")),
                                                                                new ValueExpression(new IntValue(1)),
                                                                                ArithmeticalOperation.MINUS
                                                                        )),
                                                                        new ReleaseStatement("cnt")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        try{ex12.typecheck(new MyDictionary<>());} catch(Exception e){System.out.println(e);}
        PrgState crtPrgState12 = new PrgState(ex12,new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new Heap(), new SemaphoreTable());

        this.programs.add(crtPrgState2);
        this.programs.add(crtPrgState3);
        this.programs.add(crtPrgState4);
        this.programs.add(crtPrgState5);
        this.programs.add(crtPrgState6);
        this.programs.add(crtPrgState7);
        this.programs.add(crtPrgState8);
        this.programs.add(crtPrgState9);
        this.programs.add(crtPrgState10);
        this.programs.add(crtPrgState11);
        this.programs.add(crtPrgState12);

    }

    private void populateSelectProgramsList(){
        this.programs.forEach(p->{
            this.selectProgramsList.getItems().add(p.toString());
        });
    }

    private void loadProgram(FXMLLoader fxmlLoader) {
        int selectedIndex = this.selectProgramsList.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            selectedIndex = 0;
        }
        PrgState program = this.programs.get(selectedIndex);

        List<PrgState> list = new ArrayList<>();
        list.add(program);
        IRepository repo = new Repository(list, "log.txt");
        Controller ctrl = new Controller(repo);

        MainWindowController mainWindowController = fxmlLoader.getController();
        mainWindowController.loadProgram(ctrl);
    }

    public void switchToMainWindow(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(GuiInterpreter.class.getResource("main-window.fxml"));

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(750);

        stage.setTitle("Toy Language Interpreter - Main Window");
        stage.show();

        this.loadProgram(loader);
    }
}

