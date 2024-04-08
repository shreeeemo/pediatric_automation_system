package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main extends Application {
 
	private final Map<String, PatientDetails> patientDetailsMap = new HashMap<>();

	public class PatientDetails {
	    private String height;
	    private String weight;
	    private String bloodPressure;
	    private String temperature;
	    private String healthConcerns;
	    private String prescription;

	    // Constructor
	    public PatientDetails() {
	        // Initialize with default empty strings or any default values
	        this.height = "";
	        this.weight = "";
	        this.bloodPressure = "";
	        this.temperature = "";
	        this.healthConcerns = "";
	        this.prescription = "";
	    }

	    // Getters and setters for each vital sign and other details

	    public String getHeight() {
	        return height;
	    }

	    public void setHeight(String height) {
	        this.height = height;
	    }

	    public String getWeight() {
	        return weight;
	    }

	    public void setWeight(String weight) {
	        this.weight = weight;
	    }

	    public String getBloodPressure() {
	        return bloodPressure;
	    }

	    public void setBloodPressure(String bloodPressure) {
	        this.bloodPressure = bloodPressure;
	    }

	    public String getTemperature() {
	        return temperature;
	    }

	    public void setTemperature(String temperature) {
	        this.temperature = temperature;
	    }

	    public String getHealthConcerns() {
	        return healthConcerns;
	    }

	    public void setHealthConcerns(String healthConcerns) {
	        this.healthConcerns = healthConcerns;
	    }

	    public String getPrescription() {
	        return prescription;
	    }

	    public void setPrescription(String prescription) {
	        this.prescription = prescription;
	    }

	    public String getVitals() {
	        return "Height: " + height + ", Weight: " + weight + ", Blood Pressure: " + bloodPressure + ", Temperature: " + temperature;
	    }
	    
	    public void setVitals(String height, String weight, String bloodPressure, String temperature) {
	        this.height = height;
	        this.weight = weight;
	        this.bloodPressure = bloodPressure;
	        this.temperature = temperature;
	    }

	    @Override
	    public String toString() {
	        return "PatientDetails{" +
	               "height='" + height + '\'' +
	               ", weight='" + weight + '\'' +
	               ", bloodPressure='" + bloodPressure + '\'' +
	               ", temperature='" + temperature + '\'' +
	               ", healthConcerns='" + healthConcerns + '\'' +
	               ", prescription='" + prescription + '\'' +
	               '}';
	    }
	}

    
	// Assuming these are predefined for simplicity
    private final String[] timeSlots = {"9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM"};
    private final String[] pharmacies = {"CVS Pharmacy", "Walgreens", "Walmart Pharmacy"};
    // A map to track scheduled appointments. In a real application, this would be stored in a database.
    //private final Map<LocalDate, String> scheduledAppointments = new HashMap<>();
    private final ObservableList<String> scheduledAppointments = FXCollections.observableArrayList();
	
	@Override
    public void start(Stage primaryStage) {
		primaryStage.setTitle("Select Dashboard");

        Button btnDoctor = new Button("Doctor's View");
        Button btnNurse = new Button("Nurse's View");
        Button btnPatient = new Button("Patient View");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(btnDoctor, btnNurse, btnPatient);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event Handlers for each button
        btnDoctor.setOnAction(e -> showLoginView(primaryStage, "Doctor's View"));
        btnNurse.setOnAction(e -> showLoginView(primaryStage, "Nurse's View"));
        btnPatient.setOnAction(e -> showPatientPortal(primaryStage, "Patient's View"));
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
    
    private void showPatientPortal(Stage primaryStage, String patientName) {
    	PatientDetails patientDetails = patientDetailsMap.getOrDefault(patientName, new PatientDetails());
    	
    	VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Label vitalsLabel = new Label("Vitals: " + (patientDetails.getVitals().isEmpty() ? "Not provided" : patientDetails.getVitals()));
        Label healthConcernsLabel = new Label("Health Concerns: " + (patientDetails.getHealthConcerns().isEmpty() ? "None" : patientDetails.getHealthConcerns()));
        Label prescriptionLabel = new Label("Prescription: " + (patientDetails.getPrescription().isEmpty() ? "None" : patientDetails.getPrescription()));
        
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now().plusDays(1));  // Default to the next day
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });

        ComboBox<String> timeSlotComboBox = new ComboBox<>();
        timeSlotComboBox.getItems().addAll(timeSlots);

        TextArea healthCommentsArea = new TextArea();
        healthCommentsArea.setPromptText("Enter your health issue...");

        TextField insuranceField = new TextField();
        insuranceField.setPromptText("Enter your insurance...");

        ComboBox<String> pharmacyComboBox = new ComboBox<>();
        pharmacyComboBox.getItems().addAll(pharmacies);

        Button submitButton = new Button("Schedule Appointment");
        submitButton.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            String selectedTime = timeSlotComboBox.getValue();
            String healthIssueComment = healthCommentsArea.getText();
            String insuranceInfo = insuranceField.getText();
            String selectedPharmacy = pharmacyComboBox.getValue();

            String appointmentDetails = selectedDate + " " + selectedTime + " - Health Issue: " + healthIssueComment
                                        + " - Insurance: " + insuranceInfo + " - Pharmacy: " + selectedPharmacy;

            scheduledAppointments.add(appointmentDetails);
            System.out.println("Appointment scheduled: " + appointmentDetails);
            primaryStage.close();
        });

        layout.getChildren().addAll(
                new Label(patientName + "'s Patient Portal"),
                vitalsLabel,
                healthConcernsLabel,
                prescriptionLabel,
                new Label("Schedule Your Appointment"),
                datePicker,
                timeSlotComboBox,
                new Label("Health Issue Comment"),
                healthCommentsArea,
                new Label("Insurance Information"),
                insuranceField,
                new Label("Select Pharmacy"),
                pharmacyComboBox,
                submitButton
        );

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Patient Portal");
        primaryStage.show();
    }
    
    private void showNurseDashboard(Stage primaryStage, String nurseUsername) {
    	ListView<String> listView = new ListView<>(scheduledAppointments);
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    // Customize display for Nurse's view if needed
                    String[] parts = item.split(" - ");
                    setText(parts[1]); // Display patient's name
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                showPatientDetailsForNurse(primaryStage, newVal, nurseUsername);
            }
        });

        VBox layout = new VBox(10, new Label(nurseUsername + "'s Dashboard"), listView);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Nurse's Dashboard");
        primaryStage.show();
    }
    
    private void showPatientDetailsForNurse(Stage parentStage, String appointmentDetails, String username) {
        // Extract the patient name from appointment details
        String patientName = appointmentDetails.split(" - ")[1];

        // Get or create patient details
        PatientDetails patientDetails = patientDetailsMap.getOrDefault(patientName, new PatientDetails());

        // UI Components for vitals
        TextField heightField = new TextField(patientDetails.getHeight());
        TextField weightField = new TextField(patientDetails.getWeight());
        TextField bloodPressureField = new TextField(patientDetails.getBloodPressure());
        TextField temperatureField = new TextField(patientDetails.getTemperature());
        TextArea healthConcernsArea = new TextArea(patientDetails.getHealthConcerns());

        Button saveButton = new Button("Save Vitals and Concerns");
        saveButton.setOnAction(e -> {
            patientDetails.setVitals(
                heightField.getText(), 
                weightField.getText(), 
                bloodPressureField.getText(), 
                temperatureField.getText()
            );
            patientDetails.setHealthConcerns(healthConcernsArea.getText());
            patientDetailsMap.put(patientName, patientDetails);

            System.out.println("Saved details for " + patientName);
            parentStage.close();
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        gridPane.add(new Label("Height:"), 0, 0);
        gridPane.add(heightField, 1, 0);
        gridPane.add(new Label("Weight:"), 0, 1);
        gridPane.add(weightField, 1, 1);
        gridPane.add(new Label("Blood Pressure:"), 0, 2);
        gridPane.add(bloodPressureField, 1, 2);
        gridPane.add(new Label("Temperature:"), 0, 3);
        gridPane.add(temperatureField, 1, 3);
        gridPane.add(new Label("Health Concerns:"), 0, 4);
        gridPane.add(healthConcernsArea, 1, 4);

        // Save button at the bottom
        gridPane.add(saveButton, 1, 5);

        // Create a new scene and stage for patient details or use a pop-up
        Scene scene = new Scene(gridPane);
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Patient Details for " + patientName);
        detailsStage.setScene(scene);
        detailsStage.initOwner(parentStage);
        detailsStage.initModality(Modality.WINDOW_MODAL);
        detailsStage.showAndWait();
    
    }
    
    private void showDoctorDashboard(Stage primaryStage, String doctorUsername) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        
        // Retrieve scheduled appointments from the in-memory storage
        // In a real application, replace this with a call to a database
        for (String appointmentDetails : scheduledAppointments) {
            // Splitting the details for the demonstration purpose
            String[] details = appointmentDetails.split(" - ");
            String time = details[0].split(" ")[1];
            String patientName = details[1];
            String healthIssue = details[2];

            // Display each appointment detail with additional fields
            HBox appointmentBox = new HBox(10);
            appointmentBox.setStyle("-fx-border-color: black; -fx-padding: 5;");
            
            VBox leftColumn = new VBox(5);
            Label lblTime = new Label(time);
            Label lblPatientName = new Label("Patient: " + patientName);
            TextArea healthConcernsArea = new TextArea(healthIssue);
            healthConcernsArea.setEditable(false); // assuming the nurse has filled this
            
            leftColumn.getChildren().addAll(lblTime, lblPatientName, new Label("Health Concerns:"), healthConcernsArea);
            
            VBox rightColumn = new VBox(5);
            TextArea prescriptionArea = new TextArea();
            prescriptionArea.setPromptText("Prescription Details");
            Button sendPrescriptionButton = new Button("Send to Preferred Pharmacy");
            sendPrescriptionButton.setOnAction(e -> {
                // Logic to send prescription to pharmacy
                // This would involve updating the database or communicating with a pharmacy API
                System.out.println("Prescription sent for " + patientName);
            });

            rightColumn.getChildren().addAll(new Label("Prescription:"), prescriptionArea, sendPrescriptionButton);

            appointmentBox.getChildren().addAll(leftColumn, rightColumn);
            layout.getChildren().add(appointmentBox);
        }

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle(doctorUsername + "'s Dashboard");
        primaryStage.show();
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
