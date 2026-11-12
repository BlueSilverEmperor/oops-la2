import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Program 3: Temperature Converter
 * Converts temperature between Celsius and Fahrenheit with input validation.
 */
public class TemperatureConverterApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("3. Temperature Converter");

        Label titleLabel = new Label("Temperature Converter");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web("#1E293B"));

        Label subtitleLabel = new Label("Convert between Celsius (°C) and Fahrenheit (°F)");
        subtitleLabel.setFont(Font.font("Segoe UI", 13));
        subtitleLabel.setTextFill(Color.web("#64748B"));

        VBox header = new VBox(4, titleLabel, subtitleLabel);

        // Input Card
        Label inputLbl = new Label("Enter Temperature Value:");
        inputLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13.5));
        inputLbl.setTextFill(Color.web("#334155"));

        TextField tempField = new TextField();
        tempField.setId("tempField");
        tempField.setPromptText("e.g., 37.5 or -40");
        tempField.setStyle(
            "-fx-font-size: 15px; -fx-padding: 9 12;" +
            "-fx-background-radius: 6; -fx-border-color: #CBD5E1; -fx-border-radius: 6;"
        );

        VBox inputCard = new VBox(8, inputLbl, tempField);
        inputCard.setPadding(new Insets(18));
        inputCard.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
        );

        // Conversion Buttons
        Button cToFBtn = new Button("Celsius -> Fahrenheit");
        cToFBtn.setId("cToFBtn");
        cToFBtn.setStyle(
            "-fx-background-color: #EA580C; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-padding: 10 16; -fx-background-radius: 7; -fx-cursor: hand;"
        );

        Button fToCBtn = new Button("Fahrenheit -> Celsius");
        fToCBtn.setId("fToCBtn");
        fToCBtn.setStyle(
            "-fx-background-color: #0284C7; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-padding: 10 16; -fx-background-radius: 7; -fx-cursor: hand;"
        );

        Button clearBtn = new Button("Clear");
        clearBtn.setId("clearBtn");
        clearBtn.setStyle(
            "-fx-background-color: #E2E8F0; -fx-text-fill: #334155;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-padding: 10 16; -fx-background-radius: 7; -fx-cursor: hand;"
        );

        HBox btnBox = new HBox(10, cToFBtn, fToCBtn, clearBtn);
        btnBox.setAlignment(Pos.CENTER);

        // Result Label
        Label resultLabel = new Label("Converted temperature will appear here.");
        resultLabel.setId("resultLabel");
        resultLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        resultLabel.setTextFill(Color.web("#475569"));
        resultLabel.setWrapText(true);
        resultLabel.setMaxWidth(Double.MAX_VALUE);
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setPadding(new Insets(16));
        resultLabel.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
        );

        // Event Handlers
        cToFBtn.setOnAction(e -> {
            Double celsius = parseInput(tempField, resultLabel);
            if (celsius == null) return;
            if (celsius < -273.15) {
                showError(resultLabel, "Invalid Temperature: Below absolute zero (-273.15 °C).");
                return;
            }
            double fahrenheit = (celsius * 9.0 / 5.0) + 32.0;
            showResult(resultLabel, String.format("%.2f °C  =  %.2f °F", celsius, fahrenheit));
        });

        fToCBtn.setOnAction(e -> {
            Double fahrenheit = parseInput(tempField, resultLabel);
            if (fahrenheit == null) return;
            if (fahrenheit < -459.67) {
                showError(resultLabel, "Invalid Temperature: Below absolute zero (-459.67 °F).");
                return;
            }
            double celsius = (fahrenheit - 32.0) * 5.0 / 9.0;
            showResult(resultLabel, String.format("%.2f °F  =  %.2f °C", fahrenheit, celsius));
        });

        clearBtn.setOnAction(e -> {
            tempField.clear();
            resultLabel.setText("Converted temperature will appear here.");
            resultLabel.setTextFill(Color.web("#475569"));
            resultLabel.setStyle(
                "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
                "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
            );
        });

        VBox root = new VBox(16, header, inputCard, btnBox, resultLabel);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: #F1F5F9;");

        Scene scene = new Scene(root, 470, 320);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Double parseInput(TextField field, Label resultLabel) {
        String text = field.getText().trim();
        if (text.isEmpty()) {
            showError(resultLabel, "Invalid Input: Please enter a temperature value.");
            return null;
        }
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            showError(resultLabel, "Invalid Input: \"" + text + "\" is not a valid number.");
            return null;
        }
    }

    private void showResult(Label label, String msg) {
        label.setTextFill(Color.web("#1E3A8A"));
        label.setStyle(
            "-fx-background-color: #EFF6FF; -fx-background-radius: 10;" +
            "-fx-border-color: #3B82F6; -fx-border-radius: 10;"
        );
        label.setText(msg);
    }

    private void showError(Label label, String msg) {
        label.setTextFill(Color.web("#B91C1C"));
        label.setStyle(
            "-fx-background-color: #FEF2F2; -fx-background-radius: 10;" +
            "-fx-border-color: #EF4444; -fx-border-radius: 10;"
        );
        label.setText(msg);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

