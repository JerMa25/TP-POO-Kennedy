package com.example.myproperty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {

    public PasswordField passwordTextField;
    public TextField idTextField;

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // Pas de sous-titre
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void onConnectButtonClick(ActionEvent actionEvent) throws SQLException {
        String id = idTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if (id.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT mot_de_passe FROM Utilisateur WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String PASSWORD = resultSet.getString("mot_de_passe");
                    if (PASSWORD.equals(password)) {
                        HelloApplication.setRoot("appMainView");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Mot de passe incorrect");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun utilisateur trouvé avec cet ID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue : " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("mainView");
    }
}
