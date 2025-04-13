package com.example.myproperty;

import javafx.event.ActionEvent;

import java.io.IOException;

public class appMainViewController {
    public void onTheftReportButtonClick(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("declarationView");
    }

    public void onFoundButtonClick(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("selectFoundView");
    }
}
