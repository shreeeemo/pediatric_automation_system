package application;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    private Stage primaryStage;
    private String userType;
    private Map<String, String> accounts;
    private Scene mainScene; // Store the main scene as a class variable

    // Appointment scheduler fields
    private TextField nameField;
    private TextField ageField;
    private DatePicker datePicker;
    private TextField healthIssuesField;
    private TextField insuranceField;
    private ComboBox<String> pharmacyComboBox;

    // Temporary variables to store appointment information
    private String name;
    private int age;
    private String selectedDate;
    private String healthIssues;
    private String insurance;
    private String pharmacy;

    // Nurse view fields
    private List<PatientAppointment> appointments;

    public class PatientAppointment {
        private String name;
        private int age;
        private String date;
        private String healthIssues;
        private String insurance;
        private String pharmacy;
        private double height;
        private double weight;
        private String bloodPressure;
        private double temperature;
        private String healthConcerns;

        // Doctor-specific fields
        private String heartRate;
        private String skinIrregularities;
        private String reflexes;
        private String other;
        private String currentMedication;
        private String UpdateReport;

        public PatientAppointment(String name, int age, String date, String healthIssues, String insurance, String pharmacy) {
            this.name = name;
            this.age = age;
            this.date = date;
            this.healthIssues = healthIssues;
            this.insurance = insurance;
            this.pharmacy = pharmacy;
            this.height = 0.0; // Default value
            this.weight = 0.0; // Default value
            this.temperature = 0.0; // Default value
            this.bloodPressure = ""; // Default value
            this.healthConcerns = ""; // Default value
            // Initialize doctor-specific fields
            this.heartRate = "";
            this.skinIrregularities = "";
            this.reflexes = "";
            this.other = "";
            this.currentMedication = "";
            this.UpdateReport= "";
        }

        // Getters and setters for doctor-specific fields
        public String getHeartRate() {
            return heartRate;
        }

        public void setHeartRate(String heartRate) {
            this.heartRate = heartRate;
        }

        public String getSkinIrregularities() {
            return skinIrregularities;
        }

        public void setSkinIrregularities(String skinIrregularities) {
            this.skinIrregularities = skinIrregularities;
        }

        public String getReflexes() {
            return reflexes;
        }

        public void setReflexes(String reflexes) {
            this.reflexes = reflexes;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getCurrentMedication() {
            return currentMedication;
        }

        public void setCurrentMedication(String currentMedication) {
            this.currentMedication = currentMedication;
        }
        
        public String getUpdateReport() {
        	return UpdateReport;
        }
        
        public void setUpdateReport(String UpdateReport) {
        	this.UpdateReport = UpdateReport;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHealthIssues() {
            return healthIssues;
        }

        public void setHealthIssues(String healthIssues) {
            this.healthIssues = healthIssues;
        }

        public String getInsurance() {
            return insurance;
        }

        public void setInsurance(String insurance) {
            this.insurance = insurance;
        }

        public String getPharmacy() {
            return pharmacy;
        }

        public void setPharmacy(String pharmacy) {
            this.pharmacy = pharmacy;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getBloodPressure() {
            return bloodPressure;
        }

        public void setBloodPressure(String bloodPressure) {
            this.bloodPressure = bloodPressure;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public String getHealthConcerns() {
            return healthConcerns;
        }

        public void setHealthConcerns(String healthConcerns) {
            this.healthConcerns = healthConcerns;
        }
    }
    
    // Directory to store patient files
    private static final String PATIENT_FILES_DIRECTORY = "";

    // Map to store patient file writers
    private Map<String, BufferedWriter> patientFileWriters;


    @Override
    public void start(Stage primaryStage) {
    	// Initialize the map to store patient file writers
        patientFileWriters = new HashMap<>();
        this.primaryStage = primaryStage;
        this.accounts = new HashMap<>();
        this.appointments = new ArrayList<>();

        // Creating buttons
        Button doctorsViewBtn = new Button("Doctor's View");
        Button nursesViewBtn = new Button("Nurse's View");
        Button patientsViewBtn = new Button("Patient's View");

        // Setting action for each button
        doctorsViewBtn.setOnAction(e -> {
        	// Check if there are appointments available
            if (!appointments.isEmpty()) {
                showDoctorView(); 
            } else {
                // Show an alert if there are no appointments
                showAlert("No Appointments", "There are no appointments scheduled.");
            }
        });
        // Update the nursesViewBtn.setOnAction() method to pass the PatientAppointment object
        nursesViewBtn.setOnAction(e -> {
            // Check if there are appointments available
            if (!appointments.isEmpty()) {
                // For simplicity, just display the nurse view for the first appointment
                showNurseView(appointments.get(0)); // Pass the first appointment to showNurseView()
            } else {
                // Show an alert if there are no appointments
                showAlert("No Appointments", "There are no appointments scheduled.");
            }
        });
        patientsViewBtn.setOnAction(e -> {
            userType = "Patient";
            showLoginPage();
        });

        // Adding buttons to a VBox layout
        VBox root = new VBox(10);
        root.getChildren().addAll(doctorsViewBtn, nursesViewBtn, patientsViewBtn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));

        // Creating main scene
        mainScene = new Scene(root, 300, 200);

        // Setting the stage
        primaryStage.setTitle("View Selector");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    
    // Method to create a text file for a patient account
    private void createPatientFile(String name) {
        String fileName = PATIENT_FILES_DIRECTORY + name + ".txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            patientFileWriters.put(name, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update patient file with all information
    private void updatePatientFile(PatientAppointment appointment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("patient_" + appointment.getName() + ".txt", true))) {
            writer.write("Name: " + appointment.getName());
            writer.newLine();
            writer.write("Age: " + appointment.getAge());
            writer.newLine();
            writer.write("Date: " + appointment.getDate());
            writer.newLine();
            writer.write("Health Issues: " + appointment.getHealthIssues());
            writer.newLine();
            writer.write("Insurance: " + appointment.getInsurance());
            writer.newLine();
            writer.write("Pharmacy: " + appointment.getPharmacy());
            writer.newLine();
            writer.write("Height: " + appointment.getHeight());
            writer.newLine();
            writer.write("Weight: " + appointment.getWeight());
            writer.newLine();
            writer.write("Blood Pressure: " + appointment.getBloodPressure());
            writer.newLine();
            writer.write("Temperature: " + appointment.getTemperature());
            writer.newLine();
            writer.write("Health Concerns: " + appointment.getHealthConcerns());
            writer.newLine();
            writer.write("Heart Rate: " + appointment.getHeartRate());
            writer.newLine();
            writer.write("Skin Irregularities: " + appointment.getSkinIrregularities());
            writer.newLine();
            writer.write("Reflexes: " + appointment.getReflexes());
            writer.newLine();
            writer.write("Other: " + appointment.getOther());
            writer.newLine();
            writer.write("Update Report: " + appointment.getUpdateReport());
            writer.newLine();
            writer.write("Prescribed Medication: " + appointment.getCurrentMedication());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to close the patient file writer
    private void closePatientFile(String username) {
        BufferedWriter writer = patientFileWriters.get(username);
        try {
            if (writer != null) {  // Check if writer is not null
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to show login page
    private void showLoginPage() {
        // Creating login page elements
        Label titleLabel = new Label("Login");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");
        Button backToMainButton = new Button("Back to Main");
        VBox loginBox = new VBox(10);
        loginBox.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, createAccountButton, backToMainButton);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(20));

        // Setting action for login button
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please enter both username and password.");
            } else {
                // For simplicity, check if the account exists
                if (accounts.containsKey(username) && accounts.get(username).equals(userType)) {
                    // Perform login verification
                    // For simplicity, just show a message for now
                    showAlert("Login Successful", "Welcome " + userType + "!");
                    if (userType.equals("Patient")) {
                        showAppointmentScheduler();
                    } 
                } else {
                    showAlert("Error", "The account doesn't exist or the user type doesn't match.");
                }
            }
        });
        // Wrap the doctor view layout in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(loginBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Setting action for create account button
        createAccountButton.setOnAction(e -> showCreateAccountPage());

        // Setting action for back to main button
        backToMainButton.setOnAction(e -> primaryStage.setScene(mainScene)); // Go back to the main scene

        // Creating scene
        Scene loginScene = new Scene(scrollPane, 300, 200);

        // Setting the stage
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login - " + userType);
    }
    
    // Method to show create account page
    private void showCreateAccountPage() {
        // Creating create account page elements
        Label titleLabel = new Label("Create Account");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button createButton = new Button("Create");
        Button backToMainButton = new Button("Back to Main");
        VBox createAccountBox = new VBox(10);
        createAccountBox.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, createButton, backToMainButton);
        createAccountBox.setAlignment(Pos.CENTER);
        createAccountBox.setPadding(new Insets(20));

        // Setting action for create button
        createButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please enter both username and password.");
            } else {
                // For simplicity, just show a message for now
                showAlert("Account Created", "Your " + userType + " account has been created successfully!");
                accounts.put(username, userType); // Keep track of the created account
                createPatientFile(username); // Create a text file for the patient account
                primaryStage.setScene(mainScene); // Go back to the main scene
            }
        });
        
        // Wrap the doctor view layout in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(createAccountBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Setting action for back to main button
        backToMainButton.setOnAction(e -> primaryStage.setScene(mainScene)); // Go back to the main scene

        // Creating scene
        Scene createAccountScene = new Scene(scrollPane, 300, 200);

        // Setting the stage
        primaryStage.setScene(createAccountScene);
        primaryStage.setTitle("Create Account - " + userType);
    }

    // Method to show the appointment scheduler
    private void showAppointmentScheduler() {
        // Appointment scheduler elements
        Label titleLabel = new Label("Appointment Scheduler");
        nameField = new TextField();
        nameField.setPromptText("Enter your name");
        ageField = new TextField();
        ageField.setPromptText("Enter your age");
        datePicker = new DatePicker();
        healthIssuesField = new TextField();
        healthIssuesField.setPromptText("Enter health issues");
        insuranceField = new TextField();
        insuranceField.setPromptText("Enter insurance details");
        Label pharmacyLabel = new Label("Pharmacy:");
        pharmacyComboBox = new ComboBox<>();
        pharmacyComboBox.setItems(FXCollections.observableArrayList("CVS", "Walgreens", "Walmart"));
        Button scheduleButton = new Button("Schedule Appointment");
        Button backToMainButton = new Button("Back to Main");

        // Back button action
        backToMainButton.setOnAction(e -> primaryStage.setScene(mainScene));

        // Schedule button action
        scheduleButton.setOnAction(e -> {
            // Save the entered information
            name = nameField.getText();
            age = Integer.parseInt(ageField.getText());
            selectedDate = datePicker.getValue().toString();
            healthIssues = healthIssuesField.getText();
            insurance = insuranceField.getText();
            pharmacy = pharmacyComboBox.getValue();

            // Create a new PatientAppointment object with the entered information
            PatientAppointment appointment = new PatientAppointment(name, age, selectedDate, healthIssues, insurance, pharmacy);

            // Add the appointment to the list of appointments
            appointments.add(appointment);

            // For now, just print the information
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Date: " + selectedDate);
            System.out.println("Health Issues: " + healthIssues);
            System.out.println("Insurance: " + insurance);
            System.out.println("Pharmacy: " + pharmacy);

            // Call showNurseView() with the created PatientAppointment object
            showNurseView(appointment);
            
            primaryStage.setScene(mainScene);
        });
        // Layout for appointment scheduler
        VBox schedulerLayout = new VBox(10);
        schedulerLayout.getChildren().addAll(
                titleLabel, nameField, ageField, datePicker, healthIssuesField, insuranceField,
                pharmacyLabel, pharmacyComboBox, scheduleButton, backToMainButton
        );
        schedulerLayout.setAlignment(Pos.CENTER);
        schedulerLayout.setPadding(new Insets(20));

        // Creating scene
        Scene schedulerScene = new Scene(schedulerLayout, 400, 350);

        // Setting the stage
        primaryStage.setScene(schedulerScene);
        primaryStage.setTitle("Appointment Scheduler");
    }
    
    // Method to show the nurse view
    private void showNurseView(PatientAppointment appointment) {
        // Creating nurse view elements
        VBox nurseViewLayout = new VBox(10);
        nurseViewLayout.setPadding(new Insets(20));

        	// Displaying patient appointments
            VBox appointmentBox = new VBox(10);
            appointmentBox.setPadding(new Insets(10));
            appointmentBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-border-color: black;");

            Label nameLabel = new Label("Name: " + appointment.getName());
            Label ageLabel = new Label("Age: " + appointment.getAge());
            Label dateLabel = new Label("Appointment Date: " + appointment.getDate());
            Label healthIssuesLabel = new Label("Health Issues: " + appointment.getHealthIssues());
            Label insuranceLabel = new Label("Insurance: " + appointment.getInsurance());
            Label pharmacyLabel = new Label("Pharmacy: " + appointment.getPharmacy());

            // Vitals section
            Label vitalsLabel = new Label("Vitals:");
            TextField heightField = new TextField();
            heightField.setPromptText("Height");
            TextField weightField = new TextField();
            weightField.setPromptText("Weight");
            TextField bloodPressureField = new TextField();
            bloodPressureField.setPromptText("Blood Pressure");
            TextField temperatureField = new TextField();
            temperatureField.setPromptText("Temperature");
            TextField healthConcernsField = new TextField();
            healthConcernsField.setPromptText("Health Concerns");

            Button saveButton = new Button("Save");
            // Save button action
            saveButton.setOnAction(e -> {
                // Save the entered vital information
                appointment.setHeight(Double.parseDouble(heightField.getText()));
                appointment.setWeight(Double.parseDouble(weightField.getText()));
                appointment.setBloodPressure(bloodPressureField.getText());
                appointment.setTemperature(Double.parseDouble(temperatureField.getText()));
                appointment.setHealthConcerns(healthConcernsField.getText());
                updatePatientFile(appointment);
                closePatientFile(appointment.getName()); // Close the patient file
                primaryStage.setScene(mainScene);
            });

            appointmentBox.getChildren().addAll(nameLabel, ageLabel, dateLabel, healthIssuesLabel,
                    insuranceLabel, pharmacyLabel, vitalsLabel, heightField, weightField, bloodPressureField,
                    temperatureField, healthConcernsField, saveButton);
            nurseViewLayout.getChildren().add(appointmentBox);

        // Back button to return to main page
        Button backToMainButton = new Button("Back to Main");
        backToMainButton.setOnAction(e -> primaryStage.setScene(mainScene));
        nurseViewLayout.getChildren().add(backToMainButton);

        // Wrap the nurse view layout in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(nurseViewLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Set focus on the back button
        backToMainButton.requestFocus();

        // Creating scene
        Scene nurseViewScene = new Scene(scrollPane, 600, 400);

        // Setting the stage
        primaryStage.setScene(nurseViewScene);
        primaryStage.setTitle("Nurse View");
    }

    // Method to show doctor view
    private void showDoctorView() {
        // Doctor view elements
        VBox doctorViewLayout = new VBox(10);
        doctorViewLayout.setPadding(new Insets(20));

        // Displaying patient appointments
        for (PatientAppointment appointment : appointments) {
            VBox appointmentBox = new VBox(10);
            appointmentBox.setPadding(new Insets(10));
            appointmentBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-border-color: black;");

            Label nameLabel = new Label("Name: " + appointment.getName());
            Label ageLabel = new Label("Age: " + appointment.getAge());
            Label dateLabel = new Label("Date: " + appointment.getDate());
            Label healthIssuesLabel = new Label("Health Issues: " + appointment.getHealthIssues());
            Label insuranceLabel = new Label("Insurance: " + appointment.getInsurance());
            Label pharmacyLabel = new Label("Pharmacy: " + appointment.getPharmacy());

            // Vitals section
            Label vitalsLabel = new Label("Vitals:");
            TextField heightField = new TextField(appointment.getHeight() + "");
            heightField.setPromptText("Height");
            TextField weightField = new TextField(appointment.getWeight() + "");
            weightField.setPromptText("Weight");
            TextField bloodPressureField = new TextField(appointment.getBloodPressure());
            bloodPressureField.setPromptText("Blood Pressure");
            TextField temperatureField = new TextField(appointment.getTemperature() + "");
            temperatureField.setPromptText("Temperature");
            TextField healthConcernsField = new TextField(appointment.getHealthConcerns());
            healthConcernsField.setPromptText("Health Concerns");

            // Physical checkup section
            Label physicalCheckupLabel = new Label("Physical Checkup:");
            TextField heartRateField = new TextField(appointment.getHeartRate());
            heartRateField.setPromptText("Heart Rate");
            TextField skinIrregularitiesField = new TextField(appointment.getSkinIrregularities());
            skinIrregularitiesField.setPromptText("Skin Irregularities");
            TextField reflexesField = new TextField(appointment.getReflexes());
            reflexesField.setPromptText("Reflexes");
            TextField otherField = new TextField(appointment.getOther());
            otherField.setPromptText("Other");

            // Update Report and Prescribe Medication section
            Label updateReportLabel = new Label("Update Report:");
            TextField updateReportField = new TextField(appointment.getUpdateReport());
            updateReportField.setPromptText("Update Report");
            Label prescribeMedicationLabel = new Label("Prescribe Medication:");
            TextField medicationField = new TextField();
            Button submitButton = new Button("Submit");

            // Submit button action
            submitButton.setOnAction(e -> {
                // Save the entered doctor-specific information
                appointment.setHeight(Double.parseDouble(heightField.getText()));
                appointment.setWeight(Double.parseDouble(weightField.getText()));
                appointment.setBloodPressure(bloodPressureField.getText());
                appointment.setTemperature(Double.parseDouble(temperatureField.getText()));
                appointment.setHealthConcerns(healthConcernsField.getText());
                appointment.setHeartRate(heartRateField.getText());
                appointment.setSkinIrregularities(skinIrregularitiesField.getText());
                appointment.setReflexes(reflexesField.getText());
                appointment.setOther(otherField.getText());
                appointment.setUpdateReport(updateReportField.getText());
                appointment.setCurrentMedication(medicationField.getText());
                updatePatientFile(appointment);
                closePatientFile(appointment.getName()); // Close the patient file
                primaryStage.setScene(mainScene);
            });

            // Adding elements to the appointment box
            appointmentBox.getChildren().addAll(nameLabel, ageLabel, dateLabel, healthIssuesLabel,
                    insuranceLabel, pharmacyLabel, vitalsLabel, heightField, weightField, bloodPressureField,
                    temperatureField, healthConcernsField, physicalCheckupLabel, heartRateField, skinIrregularitiesField,
                    reflexesField, otherField, updateReportLabel, updateReportField, prescribeMedicationLabel,
                    medicationField, submitButton);

            doctorViewLayout.getChildren().add(appointmentBox);
        }

        // Back button to return to main page
        Button backToMainButton = new Button("Back to Main");
        backToMainButton.setOnAction(e -> primaryStage.setScene(mainScene));
        doctorViewLayout.getChildren().add(backToMainButton);

        // Wrap the doctor view layout in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(doctorViewLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Set focus on the back button
        backToMainButton.requestFocus();

        // Creating scene
        Scene doctorViewScene = new Scene(scrollPane, 600, 600);

        // Setting the stage
        primaryStage.setScene(doctorViewScene);
        primaryStage.setTitle("Doctor View");
    }

    // Method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
