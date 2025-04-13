package com.example.myproperty;

import javafx.scene.control.Alert;

public class alertBox {
    static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // Pas de sous-titre
        alert.setContentText(content);
        alert.showAndWait();
    }
}
