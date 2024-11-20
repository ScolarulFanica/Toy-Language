import controller.Controller;
import model.adt.MyDictionary;
import model.adt.MyStack;
import model.expressions.*;
import model.adt.Heap;
import model.state.PrgState;
import model.statements.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),new PrintStatement(new VariableExpression("v"))));
        PrgState crtPrgState1 = new PrgState(ex1, new MyStack<>(), new MyDictionary<String, IValue>(), new MyDictionary<StringValue, BufferedReader>(),new Heap());
        List<PrgState> list1 = new ArrayList<>();
        list1.add(crtPrgState1);
        IRepository repo1 = new Repository(list1,"log1.txt");
        Controller ctrl1 = new Controller(repo1);

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
        PrgState crtPrgState2 = new PrgState(ex2, new MyStack<>(), new MyDictionary<String, IValue>(), new MyDictionary<StringValue, BufferedReader>(), new Heap());
        List<PrgState> list2 = new ArrayList<>();
        list2.add(crtPrgState2);
        IRepository repo2 = new Repository(list2,"log2.txt");
        Controller ctrl2 = new Controller(repo2);

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
        PrgState crtPrgState3 = new PrgState(ex3, new MyStack<>(), new MyDictionary<String, IValue>(), new MyDictionary<StringValue, BufferedReader>(), new Heap());
        List<PrgState> list3 = new ArrayList<>();
        list3.add(crtPrgState3);
        IRepository repo3 = new Repository(list3,"log3.txt");
        Controller ctrl3 = new Controller(repo3);

        IStatement ex4 = new CompStatement(
                new VarDeclStatement("varf", new StringType()), new CompStatement(
                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompStatement(
                new OpenRFile(new VariableExpression("varf")), new CompStatement(
                new VarDeclStatement("varc", new IntType()), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CloseRFile(new VariableExpression("varf"))))))))));
        PrgState crtPrgState4 = new PrgState(ex4, new MyStack<>(), new MyDictionary<String, IValue>(), new MyDictionary<StringValue, BufferedReader>(), new Heap());
        List<PrgState> list4 = new ArrayList<>();
        list4.add(crtPrgState4);
        IRepository repo4 = new Repository(list4,"log4.txt");
        Controller ctrl4 = new Controller(repo4);

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
        PrgState crtPrgState5 = new PrgState(ex5, new MyStack<>(), new MyDictionary<String, IValue>(), new MyDictionary<StringValue, BufferedReader>(), new Heap());
        List<PrgState> list5 = new ArrayList<>();
        list5.add(crtPrgState5);
        IRepository repo5 = new Repository(list5, "log5.txt");
        Controller ctrl5 = new Controller(repo5);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctrl1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctrl2));
        menu.addCommand(new RunExample("3",ex3.toString(),ctrl3));
        menu.addCommand(new RunExample("4",ex4.toString(),ctrl4));
        menu.addCommand(new RunExample("5",ex5.toString(),ctrl5));
        menu.show();

    }
}