import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Program 5: Simple Banking Application
 * Bank Account Management System supporting Initial Balance setup,
 * Deposit, Withdraw (with overdraft prevention), and Check Balance.
 */
public class SimpleBankingApp extends Application {

    private double balance = 0.0;
    private boolean accountInitialized = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("5. Simple Banking Application");

        Label titleLabel = new Label("Bank Account Management System");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web("#1E293B"));

        Label subtitleLabel = new Label("Initialize account balance and perform Deposit, Withdraw & Balance Check");
        subtitleLabel.setFont(Font.font("Segoe UI", 13));
        subtitleLabel.setTextFill(Color.web("#64748B"));

        VBox header = new VBox(4, titleLabel, subtitleLabel);

        // Balance Display Card
        Label balanceTitle = new Label("Current Available Balance");
        balanceTitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12.5));
        balanceTitle.setTextFill(Color.web("#93C5FD"));

        Label balanceLabel = new Label("Not Initialized");
        balanceLabel.setId("balanceLabel");
        balanceLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        balanceLabel.setTextFill(Color.WHITE);

        VBox balanceCard = new VBox(4, balanceTitle, balanceLabel);
        balanceCard.setPadding(new Insets(16));
        balanceCard.setStyle("-fx-background-color: #1E3A8A; -fx-background-radius: 10;");

        // Account Initialization Row
        Label initLbl = new Label("Initial Balance (Rs.):");
        initLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        TextField initField = new TextField();
        initField.setId("initField");
        initField.setPromptText("Enter initial balance");
        initField.setStyle("-fx-font-size: 13px; -fx-padding: 7 10;");
        HBox.setHgrow(initField, Priority.ALWAYS);

        Button initBtn = new Button("Set Initial Balance");
        initBtn.setId("initBtn");
        initBtn.setStyle(
            "-fx-background-color: #0F766E; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-font-size: 12.5px;" +
            "-fx-padding: 8 14; -fx-background-radius: 6; -fx-cursor: hand;"
        );

        HBox initBox = new HBox(10, initLbl, initField, initBtn);
        initBox.setAlignment(Pos.CENTER_LEFT);
        initBox.setPadding(new Insets(14));
        initBox.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
        );

        // Transaction Panel
        Label amountLbl = new Label("Transaction Amount (Rs.):");
        amountLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        TextField amountField = new TextField();
        amountField.setId("amountField");
        amountField.setPromptText("Enter amount to deposit or withdraw");
        amountField.setStyle("-fx-font-size: 13px; -fx-padding: 7 10;");
        HBox.setHgrow(amountField, Priority.ALWAYS);

        HBox amountRow = new HBox(10, amountLbl, amountField);
        amountRow.setAlignment(Pos.CENTER_LEFT);

        Button depositBtn = createBtn("Deposit", "#2563EB");
        depositBtn.setId("depositBtn");
        Button withdrawBtn = createBtn("Withdraw", "#DC2626");
        withdrawBtn.setId("withdrawBtn");
        Button checkBtn = createBtn("Check Balance", "#475569");
        checkBtn.setId("checkBtn");

        HBox txButtons = new HBox(10, depositBtn, withdrawBtn, checkBtn);
        txButtons.setAlignment(Pos.CENTER);

        VBox txCard = new VBox(12, amountRow, txButtons);
        txCard.setPadding(new Insets(14));
        txCard.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 10;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 10;"
        );

        // Status Message & Transaction Log
        Label statusLabel = new Label("Please enter an initial balance to begin.");
        statusLabel.setId("statusLabel");
        statusLabel.setWrapText(true);
        statusLabel.setMaxWidth(Double.MAX_VALUE);
        statusLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        statusLabel.setTextFill(Color.web("#334155"));
        statusLabel.setPadding(new Insets(12));
        statusLabel.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 8;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 8;"
        );

        Label logTitle = new Label("Transaction History");
        logTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        logTitle.setTextFill(Color.web("#334155"));

        ListView<String> logList = new ListView<>();
        logList.setId("logList");
        logList.setPrefHeight(130);
        logList.setStyle("-fx-font-family: Consolas; -fx-font-size: 12px;");

        // Event Handling
        initBtn.setOnAction(e -> {
            Double val = parsePositiveOrZero(initField.getText(), statusLabel, true);
            if (val == null) return;
            balance = val;
            accountInitialized = true;
            balanceLabel.setText(String.format("Rs. %.2f", balance));
            setSuccess(statusLabel, String.format("Account initialized with balance: Rs. %.2f", balance));
            logList.getItems().add(0, String.format("[INIT]     Opening Balance: Rs. %.2f", balance));
            initField.clear();
        });

        depositBtn.setOnAction(e -> {
            if (!ensureInitialized(statusLabel)) return;
            Double amount = parsePositiveOrZero(amountField.getText(), statusLabel, false);
            if (amount == null) return;
            balance += amount;
            balanceLabel.setText(String.format("Rs. %.2f", balance));
            setSuccess(statusLabel, String.format("Deposit Successful: Rs. %.2f credited. New Balance: Rs. %.2f", amount, balance));
            logList.getItems().add(0, String.format("[DEPOSIT]  +Rs. %.2f  |  Balance: Rs. %.2f", amount, balance));
            amountField.clear();
        });

        withdrawBtn.setOnAction(e -> {
            if (!ensureInitialized(statusLabel)) return;
            Double amount = parsePositiveOrZero(amountField.getText(), statusLabel, false);
            if (amount == null) return;
            if (amount > balance) {
                setError(statusLabel, String.format(
                    "Withdrawal Denied: Requested Rs. %.2f exceeds available balance (Rs. %.2f).",
                    amount, balance
                ));
                logList.getItems().add(0, String.format("[DECLINED] -Rs. %.2f  |  Insufficient Balance", amount));
                return;
            }
            balance -= amount;
            balanceLabel.setText(String.format("Rs. %.2f", balance));
            setSuccess(statusLabel, String.format("Withdrawal Successful: Rs. %.2f debited. Remaining Balance: Rs. %.2f", amount, balance));
            logList.getItems().add(0, String.format("[WITHDRAW] -Rs. %.2f  |  Balance: Rs. %.2f", amount, balance));
            amountField.clear();
        });

        checkBtn.setOnAction(e -> {
            if (!ensureInitialized(statusLabel)) return;
            setInfo(statusLabel, String.format("Balance Inquiry: Your current available balance is Rs. %.2f", balance));
            logList.getItems().add(0, String.format("[INQUIRY]  Available Balance: Rs. %.2f", balance));
        });

        VBox root = new VBox(12, header, balanceCard, initBox, txCard, statusLabel, logTitle, logList);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #F1F5F9;");

        Scene scene = new Scene(root, 540, 560);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private boolean ensureInitialized(Label statusLabel) {
        if (!accountInitialized) {
            setError(statusLabel, "Account Not Initialized: Please set an initial balance first.");
            return false;
        }
        return true;
    }

    private Double parsePositiveOrZero(String text, Label statusLabel, boolean allowZero) {
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            setError(statusLabel, "Invalid Input: Please enter an amount.");
            return null;
        }
        try {
            double val = Double.parseDouble(trimmed);
            if (allowZero ? val < 0 : val <= 0) {
                setError(statusLabel, allowZero
                    ? "Invalid Input: Initial balance cannot be negative."
                    : "Invalid Input: Transaction amount must be greater than zero.");
                return null;
            }
            return val;
        } catch (NumberFormatException ex) {
            setError(statusLabel, "Invalid Input: Please enter a valid numeric amount.");
            return null;
        }
    }

    private Button createBtn(String text, String color) {
        Button b = new Button(text);
        b.setMinWidth(140);
        b.setStyle(
            "-fx-background-color: " + color + "; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-padding: 9 14; -fx-background-radius: 6; -fx-cursor: hand;"
        );
        return b;
    }

    private void setSuccess(Label label, String msg) {
        label.setTextFill(Color.web("#065F46"));
        label.setStyle(
            "-fx-background-color: #ECFDF5; -fx-background-radius: 8;" +
            "-fx-border-color: #10B981; -fx-border-radius: 8;"
        );
        label.setText(msg);
    }

    private void setInfo(Label label, String msg) {
        label.setTextFill(Color.web("#1E3A8A"));
        label.setStyle(
            "-fx-background-color: #EFF6FF; -fx-background-radius: 8;" +
            "-fx-border-color: #3B82F6; -fx-border-radius: 8;"
        );
        label.setText(msg);
    }

    private void setError(Label label, String msg) {
        label.setTextFill(Color.web("#B91C1C"));
        label.setStyle(
            "-fx-background-color: #FEF2F2; -fx-background-radius: 8;" +
            "-fx-border-color: #EF4444; -fx-border-radius: 8;"
        );
        label.setText(msg);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

