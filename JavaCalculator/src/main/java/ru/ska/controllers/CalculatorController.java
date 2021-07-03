package ru.ska.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import ru.ska.Main;
import ru.ska.utils.EvaluateStrings;

import java.io.IOException;
import java.util.ArrayList;

public class CalculatorController {

    @FXML
    private Label expression;

    @FXML
    private Label result;

    private ArrayList<String> calculationHistory = new ArrayList<>();

    public Label getExpression() {
        return expression;
    }

    public Label getResult() {
        return result;
    }

    public void setResult(String newResult) {
        this.result.setText("= " + newResult);
    }

    public void insertNumber(String number) {
        expression.setText(expression.getText() + number);
    }

    public void insertOperator(String operator) {
        expression.setText(expression.getText() + " " + operator + " ");
    }

    public void clearExpression() {
        expression.setText("");
    }

    public void insertAnswer(String answer) {
        expression.setText(expression.getText() + answer);
    }

    public void deleteLast() {
        if (!getExpression().getText().isEmpty()) {
            StringBuilder text = new StringBuilder(getExpression().getText());
            if (text.substring(text.length() - 1).equals(" ")) {
                text.delete(text.length() - 3, text.length());
            } else {
                text.deleteCharAt(text.length() - 1);
            }
            getExpression().setText(text.toString());
        }
    }

    public void openHistoryWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/history.fxml"));
            Parent root = loader.load();

            Main.getHistoryStage().setScene(new Scene(root));

            HistoryController historyController = loader.getController();
            historyController.initializeCalculations(calculationHistory);

            Main.getHistoryStage().show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void addCalculation(String expression, String result) {
        this.calculationHistory.add(expression + " = " + result);
    }

    public void onMouseClick(MouseEvent mouseEvent) {

        Button button = (Button) mouseEvent.getSource();
        String buttonText = button.getText();

        switch (buttonText) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                insertNumber(buttonText);
                break;

            case "+":
            case "-":
            case "/":
            case "*":
                insertOperator(buttonText);
                break;

            case "Clear":
                clearExpression();
                break;

            case "=":
                double result = EvaluateStrings.evaluate(this.getExpression().getText());
                addCalculation(this.getExpression().getText(), String.valueOf(result));
                setResult(String.valueOf(result));
                break;

            case "ANS":
                insertAnswer(getResult().getText().substring(2));
                break;

            case "Delete":
                deleteLast();
                break;

            case "History":
                openHistoryWindow();
                break;
        }
    }
}
