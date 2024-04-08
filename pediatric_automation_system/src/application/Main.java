package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Pos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main extends Application {

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select Dashboard");

        Button btnReceptionist = new Button("Receptionist View");
        Button btnDoctor = new Button("Doctor's View");
        Button btnNurse = new Button("Nurse's View");
        Button btnPatient = new Button("Patient View");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(btnReceptionist, btnDoctor, btnNurse, btnPatient);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event Handlers for each button
        btnReceptionist.setOnAction(e -> showLoginView(primaryStage, "Receptionist View"));
        btnDoctor.setOnAction(e -> showLoginView(primaryStage, "Doctor's View"));
        btnNurse.setOnAction(e -> showLoginView(primaryStage, "Nurse's View"));
        btnPatient.setOnAction(e -> showLoginView(primaryStage, "Patient View"));
    }

    private void showLoginView(Stage parentStage, String viewTitle) {
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.initOwner(parentStage);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();
        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        
        Button btnCreateAccount = new Button("Create Account");
        btnCreateAccount.setOnAction(e -> showAccountCreationView(loginStage, viewTitle));
        layout.getChildren().add(btnCreateAccount);

        Button btnLogin = new Button("Log In");
        btnLogin.setOnAction(e -> {
            // For now, any login will grant access
            loginStage.close();
            showDashboard(viewTitle);
        });

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> loginStage.close());

        layout.getChildren().addAll(lblUsername, txtUsername, lblPassword, txtPassword, btnLogin, btnBack);

        Scene scene = new Scene(layout, 300, 200);
        loginStage.setScene(scene);
        loginStage.setTitle("Login - " + viewTitle);
        loginStage.showAndWait();
    }

    private void showDashboard(String viewTitle) {
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle(viewTitle);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        
        Label lblPlaceholder = new Label("Placeholder for " + viewTitle);
        layout.getChildren().add(lblPlaceholder);

        Scene scene = new Scene(layout, 400, 300);
        dashboardStage.setScene(scene);
        dashboardStage.show();
    }
    
    private void showAccountCreationView(Stage parentStage, String role) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parentStage);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        // Common fields
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");

        // Patient-specific fields
        TextField txtFirstName = new TextField();
        txtFirstName.setPromptText("First Name");
        TextField txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        TextField txtPhone = new TextField();
        txtPhone.setPromptText("Phone Number");

        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> {
            if ("Patient View".equals(role)) {
                // Save patient information and generate an ID
                String patientId = savePatientInfo(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhone.getText());
                if (patientId != null) {
                    showAlert("Account Created", "Patient ID: " + patientId);
                    stage.close(); // Close the account creation window
                }
            } else {
                // For doctors, nurses, and receptionists just print out the username for now
                System.out.println("Account created for " + role + ": " + txtUsername.getText());
                stage.close(); // Close the account creation window
            }
        });

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> stage.close());

        layout.getChildren().addAll(new Label("Create Account for " + role), txtUsername, txtPassword);
        if ("Patient View".equals(role)) {
            layout.getChildren().addAll(txtFirstName, txtLastName, txtEmail, txtPhone);
        }
        layout.getChildren().addAll(btnSave, btnBack);

        Scene scene = new Scene(layout, 300, role.equals("Patient View") ? 300 : 200);
        stage.setScene(scene);
        stage.setTitle("Account Creation - " + role);
        stage.showAndWait();
    }

    // Save patient information to a file and generate an ID
    private String savePatientInfo(String firstName, String lastName, String email, String phone) {
        File file = new File("patients.txt");
        try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
            // For simplicity, generate an ID based on the hash code of the patient's details
            String patientId = String.valueOf((firstName + lastName + email + phone).hashCode());
            out.println(patientId + "," + firstName + "," + lastName + "," + email + "," + phone);
            return patientId;
        } catch (IOException e) {
            showAlert("Error", "Failed to save patient information.");
            return null;
        }
    }

    // Show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
