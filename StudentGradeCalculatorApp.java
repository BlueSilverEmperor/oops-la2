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
 * Program 4: Student Grade Calculator
 * Accepts student's name and marks in 5 subjects (0-100 each).
 * Computes Total Marks, Percentage, and Grade with full input validation.
 */
public class StudentGradeCalculatorApp extends Application {

    private static final int SUBJECT_COUNT = 5;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("4. Student Grade Calculator");

        Label titleLabel = new Label("Student Grade Calculator");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web("#1E293B"));

        Label subtitleLabel = new Label("Enter student name and marks (0 - 100) across 5 subjects");
        subtitleLabel.setFont(Font.font("Segoe UI", 13));
        subtitleLabel.setTextFill(Color.web("#64748B"));

        VBox header = new VBox(4, titleLabel, subtitleLabel);

        // Form Card
        GridPane formGrid = new GridPane();
        formGrid.setHgap(12);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(18));
        formGrid.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
        );

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(145);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        formGrid.getColumnConstraints().addAll(c1, c2);

        Label nameLbl = createLabel("Student Name:");
        TextField nameField = new TextField();
        nameField.setId("nameField");
        nameField.setPromptText("Enter student name");
        styleField(nameField);
        formGrid.addRow(0, nameLbl, nameField);

        String[] subjectNames = {
            "Subject 1 (OOPS):",
            "Subject 2 (DSA):",
            "Subject 3 (DBMS):",
            "Subject 4 (OS):",
            "Subject 5 (Maths):"
        };
        TextField[] markFields = new TextField[SUBJECT_COUNT];
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            Label sLbl = createLabel(subjectNames[i]);
            TextField sField = new TextField();
            sField.setId("markField" + (i + 1));
            sField.setPromptText("0 - 100");
            styleField(sField);
            markFields[i] = sField;
            formGrid.addRow(i + 1, sLbl, sField);
        }

        // Buttons
        Button calcBtn = new Button("Calculate Grade");
        calcBtn.setId("calcBtn");
        calcBtn.setStyle(
            "-fx-background-color: #2563EB; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-padding: 9 20; -fx-background-radius: 6; -fx-cursor: hand;"
        );

        Button resetBtn = new Button("Reset");
        resetBtn.setId("resetBtn");
        resetBtn.setStyle(
            "-fx-background-color: #E2E8F0; -fx-text-fill: #334155;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-padding: 9 18; -fx-background-radius: 6; -fx-cursor: hand;"
        );

        HBox btnBox = new HBox(12, calcBtn, resetBtn);
        btnBox.setAlignment(Pos.CENTER_RIGHT);

        // Result Card
        Label statusLabel = new Label("Enter marks and click Calculate Grade.");
        statusLabel.setId("statusLabel");
        statusLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13.5));
        statusLabel.setTextFill(Color.web("#475569"));
        statusLabel.setWrapText(true);

        Label totalLabel = new Label("Total Marks: -- / 500");
        totalLabel.setId("totalLabel");
        totalLabel.setMinWidth(Region.USE_PREF_SIZE);
        totalLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        totalLabel.setTextFill(Color.web("#1E293B"));

        Label percentLabel = new Label("Percentage: -- %");
        percentLabel.setId("percentLabel");
        percentLabel.setMinWidth(Region.USE_PREF_SIZE);
        percentLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        percentLabel.setTextFill(Color.web("#1E293B"));

        Label gradeLabel = new Label("Grade: --");
        gradeLabel.setId("gradeLabel");
        gradeLabel.setMinWidth(Region.USE_PREF_SIZE);
        gradeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        gradeLabel.setTextFill(Color.web("#1E293B"));

        FlowPane metricsRow = new FlowPane(24, 8, totalLabel, percentLabel, gradeLabel);
        metricsRow.setAlignment(Pos.CENTER_LEFT);

        VBox resultBox = new VBox(8, statusLabel, metricsRow);
        resultBox.setMinHeight(Region.USE_PREF_SIZE);
        resultBox.setPadding(new Insets(15));
        resultBox.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
        );

        // Event Handling
        calcBtn.setOnAction(e -> {
            String studentName = nameField.getText().trim();
            if (studentName.isEmpty()) {
                showError(statusLabel, resultBox, totalLabel, percentLabel, gradeLabel,
                    "Validation Error: Student name cannot be empty.");
                return;
            }

            double total = 0;
            for (int i = 0; i < SUBJECT_COUNT; i++) {
                String raw = markFields[i].getText().trim();
                if (raw.isEmpty()) {
                    showError(statusLabel, resultBox, totalLabel, percentLabel, gradeLabel,
                        "Validation Error: Please enter marks for Subject " + (i + 1) + ".");
                    return;
                }
                double mark;
                try {
                    mark = Double.parseDouble(raw);
                } catch (NumberFormatException ex) {
                    showError(statusLabel, resultBox, totalLabel, percentLabel, gradeLabel,
                        "Validation Error: Subject " + (i + 1) + " marks must be numeric.");
                    return;
                }
                if (mark < 0 || mark > 100) {
                    showError(statusLabel, resultBox, totalLabel, percentLabel, gradeLabel,
                        "Validation Error: Subject " + (i + 1) + " marks must be between 0 and 100.");
                    return;
                }
                total += mark;
            }

            double percentage = (total / (SUBJECT_COUNT * 100.0)) * 100.0;
            String grade = computeGrade(percentage);

            resultBox.setStyle(
                "-fx-background-color: #ECFDF5; -fx-background-radius: 10;" +
                "-fx-border-color: #10B981; -fx-border-radius: 10;"
            );
            statusLabel.setTextFill(Color.web("#065F46"));
            statusLabel.setText("Result generated for " + studentName);
            totalLabel.setText(String.format("Total Marks: %.1f / 500", total));
            percentLabel.setText(String.format("Percentage: %.2f%%", percentage));
            gradeLabel.setText("Grade: " + grade);
            gradeLabel.setTextFill(percentage >= 40 ? Color.web("#047857") : Color.web("#B91C1C"));
        });

        resetBtn.setOnAction(e -> {
            nameField.clear();
            for (TextField f : markFields) {
                f.clear();
            }
            resultBox.setStyle(
                "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
                "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
            );
            statusLabel.setTextFill(Color.web("#475569"));
            statusLabel.setText("Enter marks and click Calculate Grade.");
            totalLabel.setText("Total Marks: -- / 500");
            percentLabel.setText("Percentage: -- %");
            gradeLabel.setText("Grade: --");
            gradeLabel.setTextFill(Color.web("#1E293B"));
        });

        VBox root = new VBox(14, header, formGrid, btnBox, resultBox);
        root.setPadding(new Insets(22));
        root.setStyle("-fx-background-color: #F1F5F9;");

        Scene scene = new Scene(root, 580, 540);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private String computeGrade(double percentage) {
        if (percentage >= 90) return "A+ (Outstanding)";
        if (percentage >= 80) return "A (Excellent)";
        if (percentage >= 70) return "B+ (Very Good)";
        if (percentage >= 60) return "B (Good)";
        if (percentage >= 50) return "C (Average)";
        if (percentage >= 40) return "D (Pass)";
        return "F (Fail)";
    }

    private void showError(Label status, VBox box, Label totalLbl, Label pctLbl, Label gradeLbl, String msg) {
        box.setStyle(
            "-fx-background-color: #FEF2F2; -fx-background-radius: 10;" +
            "-fx-border-color: #EF4444; -fx-border-radius: 10;"
        );
        status.setTextFill(Color.web("#B91C1C"));
        status.setText(msg);
        totalLbl.setText("Total Marks: -- / 500");
        pctLbl.setText("Percentage: -- %");
        gradeLbl.setText("Grade: --");
        gradeLbl.setTextFill(Color.web("#1E293B"));
    }

    private Label createLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        lbl.setTextFill(Color.web("#334155"));
        return lbl;
    }

    private void styleField(TextField tf) {
        tf.setStyle(
            "-fx-font-size: 13px; -fx-padding: 6 10;" +
            "-fx-background-radius: 6; -fx-border-color: #CBD5E1; -fx-border-radius: 6;"
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
