package com.example.myproperty;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class notificationController implements Initializable {
    public VBox messageContainer;
    public Button backButton;
    private String userID = userSession.getInstance().getUserId();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNotifications();
    }

    private void loadNotifications() {
        List<List<String>> messages = new ArrayList<>();
        messageContainer.getChildren().clear(); // Clear any previous messages

        String query = "SELECT df.reportDate, finder.nom, sp.modele, sp.marque, df.foundDate, " +
                "df.location, finder.tel_number, finder.email, df.additionalDesc " +
                "FROM declaredFoundPeripheral df " +
                "JOIN stolenPeripheral sp ON df.stolenObjID = sp.id " +
                "JOIN `Utilisateur` finder ON df.finderID = finder.ID " +
                "WHERE sp.utilID = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                List<String> message = new ArrayList<>();
                message.add(resultSet.getString("reportDate"));
                message.add(resultSet.getString("nom")); // Finder's name
                message.add(resultSet.getString("modele")); // Model
                message.add(resultSet.getString("marque")); // Brand
                message.add(resultSet.getString("foundDate"));
                message.add(resultSet.getString("location"));
                message.add(resultSet.getString("tel_number"));
                message.add(resultSet.getString("email"));
                message.add(resultSet.getString("additionalDesc"));

                messages.add(message);
            }

            displayMessages(messages);

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error loading notifications: " + e.getMessage());
            messageContainer.getChildren().add(errorLabel);
        }
    }

    private void displayMessages(List<List<String>> messages) {
        messageContainer.getChildren().clear();

        // Add title
        Label title = new Label("Your Notifications");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 0 0 10px 0;");
        messageContainer.getChildren().add(title);

        if (messages == null || messages.isEmpty()) {
            Label noMsg = new Label("You don't have any notifications yet.");
            noMsg.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");
            messageContainer.getChildren().add(noMsg);
            return;
        }

        for (List<String> msg : messages) {
            // Create message card
            VBox messageCard = new VBox(5);
            messageCard.setStyle("-fx-background-color: #fff; " +
                    "-fx-border-color: #ddd; " +
                    "-fx-border-radius: 5; " +
                    "-fx-padding: 10px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");

            // Add fields with proper formatting
            addFormattedField(messageCard, "Report Date:", msg.get(0));
            addFormattedField(messageCard, "Found By:", msg.get(1));
            addFormattedField(messageCard, "Item:", msg.get(3) + " " + msg.get(2)); // Brand + Model
            addFormattedField(messageCard, "Found On:", msg.get(4));
            addFormattedField(messageCard, "Location:", msg.get(5));

            // Contact information
            HBox contacts = new HBox(10);
            contacts.getChildren().addAll(
                    new Label("Phone: " + (msg.get(6) != null ? msg.get(6) : "N/A")),
                    new Label("Email: " + (msg.get(7) != null ? msg.get(7) : "N/A"))
            );
            messageCard.getChildren().add(contacts);

            // Description
            if (msg.get(8) != null && !msg.get(8).isEmpty()) {
                TextArea desc = new TextArea(msg.get(8));
                desc.setEditable(false);
                desc.setStyle("-fx-background-color: transparent; " +
                        "-fx-border-color: #eee;");
                messageCard.getChildren().add(new Label("Details:"));
                messageCard.getChildren().add(desc);
            }

            messageContainer.getChildren().add(messageCard);
        }
    }

    private void addFormattedField(VBox container, String label, String value) {
        if (value == null || value.isEmpty()) return;

        HBox field = new HBox(5);
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-weight: bold;");
        field.getChildren().addAll(lbl, new Label(value));
        container.getChildren().add(field);
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("appMainView");
    }
}

