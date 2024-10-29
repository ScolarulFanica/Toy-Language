package view;

import controller.Controller;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expressions.ArithExpression;
import model.expressions.ArithmeticalOperation;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.state.PrgState;
import model.statements.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    private Controller controller;
    PrgState initialState;
    int var;

    public View(Controller controller) {
        this.controller = controller;
    }
    //at this stage, implement a text interface to:
    //input a program
    //for the input program you may allow the user to select a program from your programs already implemented in the main method
    public void inputProgram() {
        System.out.println("Select a program from the following list:");
        System.out.println("1. int v; v=2; Print(v)");
        System.out.println("2. int a; int b; a=2+3*5; b=a+1; Print(b)");
        System.out.println("3. bool a; int v; a=true;(If a Then v=2 Else v=3); Print(v)");
        this.read();
        try {
            this.completeExecution();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //complete execution of a program
    public void completeExecution() throws StatementException, ExpressionException {
        if (this.var == 2) {
            IStatement ex2 = new CompStatement(
                    new VarDeclStatement("b", new IntType()),
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
            );
            System.out.println(ex2.execute(initialState));
        } else if(this.var == 1){
            IStatement ex1 = new CompStatement(
                    new VarDeclStatement("v", new IntType()),
                    new CompStatement(
                            new AssignStatement("v", new ValueExpression(new IntValue(2))),new PrintStatement(new VariableExpression("v"))
                    )
            );
            System.out.println(ex1.execute(initialState));
        } else if(this.var == 3){
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
            System.out.println(ex3.execute(initialState));
        }
    }

    public void read(){
        System.out.println("Choose a program:");
        Boolean ok = true;
        while(ok) {
            Scanner scanner = new Scanner(System.in);
            try {
                this.var = scanner.nextInt();
                ok = false;
            } catch (InputMismatchException ex) {
                System.out.println("Please give an int");
            }
        }
    }
}
