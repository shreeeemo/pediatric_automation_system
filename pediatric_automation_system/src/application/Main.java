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

    public static void main(String[] args) {
        launch(args);
    }
}
