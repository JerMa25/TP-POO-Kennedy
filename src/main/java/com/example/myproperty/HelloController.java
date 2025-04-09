package com.example.myproperty;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onCreateAccountButtonClick() throws IOException {
        HelloApplication.setRoot("createAccountView");
    }

    @FXML
    protected void onLoginButtonClick() throws IOException {HelloApplication.setRoot("loginView");}

}