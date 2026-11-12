import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Program 2: Simple Calculator
 * Performs Addition, Subtraction, Multiplication, and Division of two numbers.
 * Uses two TextFields for input, four Buttons for operations, and a Label for the result.
 */
public class SimpleCalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("2. Simple Calculator");

        Label titleLabel = new Label("Simple Calculator");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web("#1E293B"));

        Label subtitleLabel = new Label("Enter two numbers and choose an arithmetic operation");
        subtitleLabel.setFont(Font.font("Segoe UI", 13));
        subtitleLabel.setTextFill(Color.web("#64748B"));

        VBox header = new VBox(4, titleLabel, subtitleLabel);

        // Input card
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(12);
        inputGrid.setVgap(12);
        inputGrid.setPadding(new Insets(18));
        inputGrid.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
        );

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(120);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        inputGrid.getColumnConstraints().addAll(c1, c2);

        Label num1Lbl = new Label("First Number:");
        num1Lbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13.5));
        TextField num1Field = new TextField();
        num1Field.setId("num1Field");
        num1Field.setPromptText("Enter first number");
        num1Field.setStyle("-fx-font-size: 14px; -fx-padding: 8 10;");

        Label num2Lbl = new Label("Second Number:");
        num2Lbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13.5));
        TextField num2Field = new TextField();
        num2Field.setId("num2Field");
        num2Field.setPromptText("Enter second number");
        num2Field.setStyle("-fx-font-size: 14px; -fx-padding: 8 10;");

        inputGrid.addRow(0, num1Lbl, num1Field);
        inputGrid.addRow(1, num2Lbl, num2Field);

        // Operation Buttons
        Button addBtn = createOpButton("Add (+)", "#2563EB");
        addBtn.setId("addBtn");
        Button subBtn = createOpButton("Subtract (-)", "#0D9488");
        subBtn.setId("subBtn");
        Button mulBtn = createOpButton("Multiply (*)", "#7C3AED");
        mulBtn.setId("mulBtn");
        Button divBtn = createOpButton("Divide (/)", "#D97706");
        divBtn.setId("divBtn");

        HBox btnRow = new HBox(10, addBtn, subBtn, mulBtn, divBtn);
        btnRow.setAlignment(Pos.CENTER);

        // Result Label
        Label resultLabel = new Label("Result: --");
        resultLabel.setId("resultLabel");
        resultLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        resultLabel.setTextFill(Color.web("#0F172A"));
        resultLabel.setWrapText(true);
        resultLabel.setMaxWidth(Double.MAX_VALUE);
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setPadding(new Insets(16));
        resultLabel.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
        );

        // Operation handlers
        addBtn.setOnAction(e -> calculate(num1Field, num2Field, resultLabel, "+"));
        subBtn.setOnAction(e -> calculate(num1Field, num2Field, resultLabel, "-"));
        mulBtn.setOnAction(e -> calculate(num1Field, num2Field, resultLabel, "*"));
        divBtn.setOnAction(e -> calculate(num1Field, num2Field, resultLabel, "/"));

        VBox root = new VBox(16, header, inputGrid, btnRow, resultLabel);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: #F1F5F9;");

        Scene scene = new Scene(root, 480, 340);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Button createOpButton(String text, String hexColor) {
        Button btn = new Button(text);
        btn.setMinWidth(100);
        btn.setStyle(
            "-fx-background-color: " + hexColor + "; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-padding: 10 12; -fx-background-radius: 7; -fx-cursor: hand;"
        );
        return btn;
    }

    private void calculate(TextField f1, TextField f2, Label resultLabel, String op) {
        String s1 = f1.getText().trim();
        String s2 = f2.getText().trim();

        if (s1.isEmpty() || s2.isEmpty()) {
            setError(resultLabel, "Error: Both input fields are required.");
            return;
        }

        double a, b;
        try {
            a = Double.parseDouble(s1);
            b = Double.parseDouble(s2);
        } catch (NumberFormatException ex) {
            setError(resultLabel, "Error: Please enter valid numeric values.");
            return;
        }

        double res;
        switch (op) {
            case "+":
                res = a + b;
                break;
            case "-":
                res = a - b;
                break;
            case "*":
                res = a * b;
                break;
            case "/":
                if (b == 0) {
                    setError(resultLabel, "Error: Division by zero is not allowed.");
                    return;
                }
                res = a / b;
                break;
            default:
                return;
        }

        resultLabel.setTextFill(Color.web("#065F46"));
        resultLabel.setStyle(
            "-fx-background-color: #ECFDF5; -fx-background-radius: 10;" +
            "-fx-border-color: #10B981; -fx-border-radius: 10;"
        );
        resultLabel.setText(String.format("Result: %s %s %s = %s", formatNum(a), op, formatNum(b), formatNum(res)));
    }

    private void setError(Label resultLabel, String msg) {
        resultLabel.setTextFill(Color.web("#B91C1C"));
        resultLabel.setStyle(
            "-fx-background-color: #FEF2F2; -fx-background-radius: 10;" +
            "-fx-border-color: #EF4444; -fx-border-radius: 10;"
        );
        resultLabel.setText(msg);
    }

    private String formatNum(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        }
        return String.format("%.4f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

