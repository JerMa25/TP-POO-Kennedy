package com.example.myproperty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.myproperty.alertBox.showAlert;

public class createAccountController {
    public TextField userNameField;
    public TextField dateOfBirthField;
    public TextField townField;
    public TextField quarterField;
    public TextField emailField;
    public TextField phoneNumberField;
    public ComboBox<String> genderComboBox;
    public TextField passwordField;

    @FXML
    public void initialize(){
        genderComboBox.getItems().addAll("Masculin","Feminin");
        genderComboBox.setValue("Masculin");
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
        String username = userNameField.getText().trim();
        String dob = dateOfBirthField.getText().trim();
        String town = townField.getText().trim();
        String quarter = quarterField.getText().trim();
        String email = emailField.getText().trim();
        String tel = phoneNumberField.getText().trim();
        String gender = genderComboBox.getValue();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || dob.isEmpty() || town.isEmpty() || quarter.isEmpty()
                || email.isEmpty() || tel.isEmpty() || gender == null || gender.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Insertion dans la BD
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Utilisateur (nom, date_naissance, ville, quartier, email, tel_number, gender, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, username);
                stmt.setString(2, dob);
                stmt.setString(3, town);
                stmt.setString(4, quarter);
                stmt.setString(5, email);
                stmt.setString(6, tel);
                stmt.setString(7, gender);
                stmt.setString(8, password);

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    try (var generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int userId = generatedKeys.getInt(1); // récupère l'id généré
                            showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur enregistré avec succès !\nVotre ID est : " + userId);
                            HelloApplication.setRoot("loginView");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'enregistrement de l'utilisateur.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("mainView");
    }
}
