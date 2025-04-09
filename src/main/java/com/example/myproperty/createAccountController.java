package com.example.myproperty;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class createAccountController {
    public TextField userNameField;
    public TextField dateOfBirthField;
    public TextField town;
    public TextField quarter;
    public TextField email;
    public TextField phoneNumber;
    public ComboBox<String> gender;

    @FXML
    public void initialize(){
        gender.getItems().addAll("Masculin","Feminin");
        gender.setValue("Masculin");
    }

}
