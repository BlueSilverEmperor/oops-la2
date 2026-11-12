import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Program 1: Student Registration Form
 * Fields: Name, USN, Branch, Semester, Email, Phone Number
 * Controls: Label, TextField, RadioButton, ComboBox, Button
 */
public class StudentRegistrationApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("1. Student Registration Form - NMIT");

        // Header
        Label titleLabel = new Label("Student Registration Form");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web("#1E293B"));

        Label subtitleLabel = new Label("Department of Information Science & Engineering");
        subtitleLabel.setFont(Font.font("Segoe UI", 13));
        subtitleLabel.setTextFill(Color.web("#64748B"));

        VBox headerBox = new VBox(4, titleLabel, subtitleLabel);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        // Form Grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(14);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(18));
        formGrid.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1;" +
            "-fx-border-radius: 10;"
        );

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(115);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        formGrid.getColumnConstraints().addAll(col1, col2);

        // Name
        Label nameLbl = createFieldLabel("Full Name:");
        TextField nameField = new TextField();
        nameField.setId("nameField");
        nameField.setPromptText("Enter student full name");
        styleTextField(nameField);

        // USN
        Label usnLbl = createFieldLabel("USN:");
        TextField usnField = new TextField();
        usnField.setId("usnField");
        usnField.setPromptText("e.g., 1NT25IS042");
        styleTextField(usnField);

        // Branch (ComboBox)
        Label branchLbl = createFieldLabel("Branch:");
        ComboBox<String> branchCombo = new ComboBox<>();
        branchCombo.setId("branchCombo");
        branchCombo.getItems().addAll(
            "Information Science & Engineering (ISE)",
            "Computer Science & Engineering (CSE)",
            "Artificial Intelligence & ML (AIML)",
            "Electronics & Communication (ECE)",
            "Mechanical Engineering (ME)"
        );
        branchCombo.setPromptText("Select Branch");
        branchCombo.setMaxWidth(Double.MAX_VALUE);
        branchCombo.setStyle("-fx-font-size: 13px;");

        // Semester (RadioButtons)
        Label semLbl = createFieldLabel("Semester:");
        ToggleGroup semGroup = new ToggleGroup();
        FlowPane semPane = new FlowPane(10, 8);
        for (int i = 1; i <= 8; i++) {
            RadioButton rb = new RadioButton("Sem " + i);
            rb.setId("sem" + i);
            rb.setUserData(String.valueOf(i));
            rb.setToggleGroup(semGroup);
            rb.setStyle("-fx-font-size: 12.5px; -fx-text-fill: #334155;");
            semPane.getChildren().add(rb);
        }

        // Email
        Label emailLbl = createFieldLabel("Email:");
        TextField emailField = new TextField();
        emailField.setId("emailField");
        emailField.setPromptText("e.g., student@nmit.ac.in");
        styleTextField(emailField);

        // Phone Number
        Label phoneLbl = createFieldLabel("Phone Number:");
        TextField phoneField = new TextField();
        phoneField.setId("phoneField");
        phoneField.setPromptText("10-digit mobile number");
        styleTextField(phoneField);

        formGrid.addRow(0, nameLbl, nameField);
        formGrid.addRow(1, usnLbl, usnField);
        formGrid.addRow(2, branchLbl, branchCombo);
        formGrid.addRow(3, semLbl, semPane);
        formGrid.addRow(4, emailLbl, emailField);
        formGrid.addRow(5, phoneLbl, phoneField);

        // Buttons
        Button submitBtn = new Button("Submit");
        submitBtn.setId("submitBtn");
        submitBtn.setStyle(
            "-fx-background-color: #2563EB; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-padding: 9 24; -fx-background-radius: 6; -fx-cursor: hand;"
        );

        Button clearBtn = new Button("Clear");
        clearBtn.setId("clearBtn");
        clearBtn.setStyle(
            "-fx-background-color: #E2E8F0; -fx-text-fill: #334155;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-padding: 9 20; -fx-background-radius: 6; -fx-cursor: hand;"
        );

        HBox buttonBox = new HBox(12, submitBtn, clearBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Output Display Panel
        Label outputTitle = new Label("Registered Student Details");
        outputTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        outputTitle.setTextFill(Color.web("#1E293B"));

        Label outputLabel = new Label("Fill the form above and click Submit to view the entered details.");
        outputLabel.setId("outputLabel");
        outputLabel.setWrapText(true);
        outputLabel.setMinHeight(Region.USE_PREF_SIZE);
        outputLabel.setFont(Font.font("Consolas", 12.5));
        outputLabel.setTextFill(Color.web("#475569"));

        VBox outputBox = new VBox(6, outputTitle, outputLabel);
        outputBox.setMinHeight(Region.USE_PREF_SIZE);
        outputBox.setPadding(new Insets(14));
        outputBox.setStyle(
            "-fx-background-color: #F8FAFC;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1;" +
            "-fx-border-radius: 10;"
        );

        // Event Handling
        submitBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String usn = usnField.getText().trim().toUpperCase();
            String branch = branchCombo.getValue();
            Toggle selectedSem = semGroup.getSelectedToggle();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || usn.isEmpty() || branch == null || selectedSem == null || email.isEmpty() || phone.isEmpty()) {
                showError(outputLabel, outputBox, "Validation Error: Please fill in all fields and select Branch & Semester.");
                return;
            }
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
                showError(outputLabel, outputBox, "Validation Error: Please enter a valid email address.");
                return;
            }
            if (!phone.matches("\\d{10}")) {
                showError(outputLabel, outputBox, "Validation Error: Phone number must be exactly 10 digits.");
                return;
            }

            String semester = selectedSem.getUserData().toString();
            outputBox.setStyle(
                "-fx-background-color: #EFF6FF;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #3B82F6;" +
                "-fx-border-radius: 10;"
            );
            outputLabel.setTextFill(Color.web("#0F172A"));
            outputLabel.setText(
                "Registration Successful!\n" +
                "--------------------------------------------\n" +
                "Name         : " + name + "\n" +
                "USN          : " + usn + "\n" +
                "Branch       : " + branch + "\n" +
                "Semester     : Semester " + semester + "\n" +
                "Email        : " + email + "\n" +
                "Phone Number : " + phone
            );
        });

        clearBtn.setOnAction(e -> {
            nameField.clear();
            usnField.clear();
            branchCombo.setValue(null);
            if (semGroup.getSelectedToggle() != null) {
                semGroup.getSelectedToggle().setSelected(false);
            }
            emailField.clear();
            phoneField.clear();
            outputBox.setStyle(
                "-fx-background-color: #F8FAFC;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #CBD5E1;" +
                "-fx-border-radius: 10;"
            );
            outputLabel.setTextFill(Color.web("#475569"));
            outputLabel.setText("Fill the form above and click Submit to view the entered details.");
        });

        VBox root = new VBox(14, headerBox, formGrid, buttonBox, outputBox);
        root.setPadding(new Insets(22));
        root.setStyle("-fx-background-color: #F1F5F9;");

        Scene scene = new Scene(root, 580, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Label createFieldLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        lbl.setTextFill(Color.web("#334155"));
        return lbl;
    }

    private void styleTextField(TextField tf) {
        tf.setStyle(
            "-fx-font-size: 13px; -fx-padding: 7 10;" +
            "-fx-background-radius: 6; -fx-border-color: #CBD5E1; -fx-border-radius: 6;"
        );
    }

    private void showError(Label label, VBox box, String message) {
        box.setStyle(
            "-fx-background-color: #FEF2F2;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #EF4444;" +
            "-fx-border-radius: 10;"
        );
        label.setTextFill(Color.web("#B91C1C"));
        label.setText(message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
