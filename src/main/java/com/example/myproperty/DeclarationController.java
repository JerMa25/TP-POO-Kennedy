package com.example.myproperty;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeclarationController {

    public TextField marqueField;
    public TextField modeleField;
    public TextField processeurField;
    public TextField osField;
    public TextField adressemacField;
    public TextField ramField;
    public TextField stockageField;
    public TextField vitesseMaxField;

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // Pas de sous-titre
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void onUndoButtonClick(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("appMainView");
    }

    public void onConfirmButtonClick(ActionEvent actionEvent) {
        String utilID = userSession.getInstance().getUserId();
        String marque = marqueField.getText().trim();
        String modele = modeleField.getText().trim();
        String processeur = processeurField.getText().trim();
        String os = osField.getText().trim();
        String adresseMac = adressemacField.getText().trim();
        String ram = ramField.getText().trim();
        String stockage = stockageField.getText().trim();
        String vitessemax = vitesseMaxField.getText().trim();

        if (marque.isEmpty() || modele.isEmpty() || adresseMac.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Insertion dans la BD
        try (Connection conn = DBConnection.getConnection()) {

            // 1. Vérifier si l'ID utilisateur existe
            String checkUserSql = "SELECT COUNT(*) FROM Utilisateur WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserSql)) {
                checkStmt.setString(1, utilID);
                var rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    showAlert(Alert.AlertType.ERROR, "Utilisateur inexistant", "L'identifiant utilisateur '" + utilID + "' n'existe pas.");
                    return;
                }
            }

            // 2. Vérifier si le péripherique entré n'existe pas déjà
            String checkAdresseMacUnicitySql = "SELECT COUNT(*) FROM stolenPeripheral WHERE adresseMac = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkAdresseMacUnicitySql)) {
                checkStmt.setString(1, adresseMac);
                var rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert(Alert.AlertType.ERROR, "Périphérique existant", "Le périphérique d'adresse MAC '" + adresseMac + "' existe déjà.");
                    return;
                }
            }

            // Insertion dans la BD
            String sql = "INSERT INTO stolenPeripheral (utilID, marque, modele, processeur, systeme_exploitation, adresseMac, ram, stockage, vitesse_max) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, utilID);
                stmt.setString(2, marque);
                stmt.setString(3, modele);
                stmt.setString(4, processeur);
                stmt.setString(5, os);
                stmt.setString(6, adresseMac);
                stmt.setString(7, ram);
                stmt.setString(8, stockage);
                stmt.setString(9, vitessemax);

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Périphérique enregistré avec succès !");
                HelloApplication.setRoot("appMainView");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }
}
