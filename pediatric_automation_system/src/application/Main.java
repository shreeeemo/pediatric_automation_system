package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Login Page
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Log In");

        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(25, 25, 25, 25));
        loginGrid.add(usernameLabel, 0, 0);
        loginGrid.add(usernameField, 1, 0);
        loginGrid.add(passwordLabel, 0, 1);
        loginGrid.add(passwordField, 1, 1);
        loginGrid.add(loginButton, 1, 2);

        Scene loginScene = new Scene(loginGrid, 300, 200);

        // Information Page
        Label infoLabel = new Label("Enter Information");
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label ageLabel = new Label("Age:");
        TextField ageField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label extraInfoLabel = new Label("Extra Information:");
        TextArea extraInfoArea = new TextArea();

        VBox infoVBox = new VBox(10);
        infoVBox.setAlignment(Pos.CENTER);
        infoVBox.setPadding(new Insets(25, 25, 25, 25));
        infoVBox.getChildren().addAll(infoLabel, nameLabel, nameField, ageLabel, ageField,
                emailLabel, emailField, extraInfoLabel, extraInfoArea);

        Scene infoScene = new Scene(infoVBox, 400, 300);

        // Button Actions
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Check if both username and password are at least 7 characters long
            if (username.length() >= 7 && password.length() >= 7) {
                primaryStage.setScene(infoScene);
            } else {
                // Show an alert if either username or password is not long enough
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Username or Password");
                alert.setContentText("Invalid Username or Password.");
                alert.showAndWait();
            }
        });

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
