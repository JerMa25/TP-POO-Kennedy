package com.example.myproperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class selectFoundController implements Initializable{
    public TableView<stolenObjectTableViewModel> lostPeripheralsTableView;
    public TableColumn<stolenObjectTableViewModel, String> ownerName;
    public TableColumn<stolenObjectTableViewModel, String> marque;
    public TableColumn<stolenObjectTableViewModel, String> modele;
    public TableColumn<stolenObjectTableViewModel, String> adresseMac;
    private ObservableList<stolenObjectTableViewModel> stolenObjectsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurer les cellules de colonnes
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
        String query = "SELECT u.nom, s.marque, s.modele, s.adresseMac FROM stolenPeripheral s JOIN Utilisateur u ON u.id = s.utilID";

        // Vider la liste avant de la remplir
        stolenObjectsList.clear();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String owner = resultSet.getString("nom");
                String brand = resultSet.getString("marque");
                String model = resultSet.getString("modele");
                String adresseMac = resultSet.getString("adresseMac");

                stolenObjectsList.add(new stolenObjectTableViewModel(owner, brand, model, adresseMac));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement des données : " + e.getMessage());
        }
    }

    // Méthode pour rafraîchir la liste
    @FXML
    public void refreshList(ActionEvent event) {
        loadDataFromDatabase();
    }

    public void onConfirmButtonClick(ActionEvent actionEvent) {

    }
}
