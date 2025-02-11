package com.example.finalproject.controller;

import com.example.finalproject.dto.SignupDTO;
import com.example.finalproject.dao.custom.impl.SignupDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class SignupController {

    @FXML
    private AnchorPane ancMain;

    @FXML
    private AnchorPane ancSignUp;

    @FXML
    private Button btnAcc;

    @FXML
    private TextField txtConform;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPsw;

    @FXML
    private TextField txtUser;

    SignupDAOImpl signupModel = new SignupDAOImpl();

    @FXML
    void createAccOnAction(ActionEvent event) throws SQLException, IOException {
        String userName = txtUser.getText();
        String password = txtPsw.getText();
        String conform = txtConform.getText();
        String email = txtEmail.getText();


        SignupDTO signupDTO = new SignupDTO(userName, password, conform, email);

        boolean isSaved = signupModel.saveSignup(signupDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Account Created", ButtonType.OK).show();
            ancMain.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            ancMain.getChildren().add(load);
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Account not Created", ButtonType.OK).show();

        }
    }

    private void refreshPage() {
        txtUser.clear();
        txtPsw.clear();
        txtConform.clear();
        txtEmail.clear();
    }

}

