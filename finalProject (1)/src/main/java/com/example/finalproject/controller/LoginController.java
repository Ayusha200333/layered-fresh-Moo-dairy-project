package com.example.finalproject.controller;

import com.example.finalproject.dto.LoginDTO;
import com.example.finalproject.dao.custom.impl.LoginDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {
    public Button btnLogin;
    public TextField txtEmail;
    public TextField txtPsw;
    public AnchorPane anc1;
    public Button signUp;

    LoginDAOImpl loginModel = new LoginDAOImpl();

    public void LoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        String email = txtEmail.getText();
        String password = txtPsw.getText();

        LoginDTO loginDTO  = new LoginDTO(email, password);

        boolean isSaved = loginModel.SaveLogin(loginDTO);

        if(isSaved == true){
            anc1.getChildren().clear();
            AnchorPane load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
            anc1.getChildren().add(load);
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid Email or Password").show();

        }

    }

    public void signUponAction(ActionEvent actionEvent) throws IOException {
        anc1.getChildren().clear();
        AnchorPane load = FXMLLoader.load((getClass().getResource("/view/Signup.fxml")));
        anc1.getChildren().add(load);
    }
}
