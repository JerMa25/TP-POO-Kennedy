package com.example.myproperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.myproperty.alertBox.showAlert;

public class selectFoundController implements Initializable{
    // Class-level variable to store the selected stolenObjID
    private String selectedStolenObjID = null;

    public Button confirmButton;

    public TableView<stolenObjectTableViewModel> lostPeripheralsTableView;
    public TableColumn<stolenObjectTableViewModel, String> stolenObjID;
    public TableColumn<stolenObjectTableViewModel, String> ownerName;
    public TableColumn<stolenObjectTableViewModel, String> marque;
    public TableColumn<stolenObjectTableViewModel, String> modele;
    public TableColumn<stolenObjectTableViewModel, String> adresseMac;
    private final ObservableList<stolenObjectTableViewModel> stolenObjectsList = FXCollections.observableArrayList();

    public TextField additionalDescField;
    public TextField foundDateField;
    public TextField foundLocationField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurer les cellules de colonnes
        stolenObjID.setCellValueFactory(new PropertyValueFactory<>("objID"));
        ownerName.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        modele.setCellValueFactory(new PropertyValueFactory<>("modele"));
        adresseMac.setCellValueFactory(new PropertyValueFactory<>("adresseMac"));

        // Charger les données
        loadDataFromDatabase();

        // Lier la liste au TableView
        lostPeripheralsTableView.setItems(stolenObjectsList);
    }
    private void loadDataFromDatabase() {
        String query = "SELECT s.id, u.nom, s.marque, s.modele, s.adresseMac FROM stolenPeripheral s JOIN Utilisateur u ON u.id = s.utilID";

        // Vider la liste avant de la remplir
        stolenObjectsList.clear();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String objID = resultSet.getString("id");
                String owner = resultSet.getString("nom");
                String brand = resultSet.getString("marque");
                String model = resultSet.getString("modele");
                String adresseMac = resultSet.getString("adresseMac");

                stolenObjectsList.add(new stolenObjectTableViewModel(objID, owner, brand, model, adresseMac));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement des données : " + e.getMessage());
        }

        // Add listener to ListView selection model
        lostPeripheralsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Enable button if an item is selected, disable otherwise
            confirmButton.setDisable(newValue == null);
            if (newValue != null) {
                selectedStolenObjID = newValue.getObjID(); // Update the selected ID
            } else {
                selectedStolenObjID = null; // Clear when no row is selected
            }
        });
    }

    // Méthode pour rafraîchir la liste
    @FXML
    public void refreshList(ActionEvent event) {
        loadDataFromDatabase();
    }

    public void onConfirmButtonClick(ActionEvent actionEvent) {
        String finderID = userSession.getInstance().getUserId();
        String objID = selectedStolenObjID;
        String foundDate = foundDateField.getText().trim();
        String foundLocation = foundLocationField.getText().trim();
        String additionalDesc = additionalDescField.getText().trim();

        if (foundDate.isEmpty() || foundLocation.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs obligatoire.");
            return;
        }

        // Insertion dans la BD
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO declaredFoundPeripheral (finderID, stolenObjID, foundDate, location, additionalDesc) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, finderID);
                stmt.setString(2, objID);
                stmt.setString(3, foundDate);
                stmt.setString(4, foundLocation);
                stmt.setString(5, additionalDesc);

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Déclaration enregistré avec succès !");
                }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("appMainView");
    }
}
