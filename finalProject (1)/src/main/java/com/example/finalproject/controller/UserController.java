package com.example.finalproject.controller;

import com.example.finalproject.bo.BOFactory;
import com.example.finalproject.bo.custom.UserBO;
import com.example.finalproject.dto.UserDTO;
import com.example.finalproject.dto.tm.UserTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UserController implements Initializable {

    @FXML
    private Button btnDeleteUser;

    @FXML
    private Button btnLoadUser;

    @FXML
    private Button btnSaveUser;

    @FXML
    private Button btnUpdateUser;

    @FXML
    private TableColumn<UserTM, String> colEmail;

    @FXML
    private TableColumn<UserTM, String> colPassword;

    @FXML
    private TableColumn<UserTM, String> colUserId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserID;

//    UserDAOImpl userModel = new UserDAOImpl();

//    UserDAO userDAO = new com.example.finalproject.dao.custom.impl.UserDAOImpl();

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String userId = txtUserID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = userBO.delete(userId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "User deleted successfully").show();
                refreshPage();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "User could not be deleted").show();
            }
        }
        btnSaveUser.setDisable(false);
    }

    private void loadTableData() throws SQLException {
        ArrayList<UserDTO> userDTOS = userBO.getAll();

        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

        for (UserDTO userDTO : userDTOS) {
            UserTM userTM = new UserTM(
                    userDTO.getUserId(),
                    userDTO.getPassword(),
                    userDTO.getEmail()
            );
            userTMS.add(userTM);
        }

        tblUser.setItems(userTMS);

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String userId = txtUserID.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();

        boolean isValidEmail = isValidEmail(email);
        if (isValidEmail) {
            UserDTO userDTO = new UserDTO(userId, password, email);

            boolean isSaved = userBO.save(userDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User Saved", ButtonType.OK).show();
                refreshPage();
                loadNextUserId();
            } else {
                new Alert(Alert.AlertType.ERROR, "User Not Saved", ButtonType.OK).show();

            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Email", ButtonType.OK).show();
        }
    }

//        UserDTO userDTO = new UserDTO(userId, password, email);
//
//        boolean isSaved = userModel.saveUser(userDTO);
//        if(isSaved){
//            new Alert(Alert.AlertType.INFORMATION, "User Saved", ButtonType.OK).show();
//            refreshPage();
//            loadNextUserId();
//        }else{
//            new Alert(Alert.AlertType.ERROR, "User Not Saved", ButtonType.OK).show();
//
//        }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    private void refreshPage() throws SQLException{
        loadNextUserId();

        txtUserID.clear();
        txtPassword.clear();
        txtEmail. clear();


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String userId = txtUserID.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();


        UserDTO userDTO = new UserDTO(
                userId,
                password,
                email
        );

        boolean isUpdate = userBO.update(userDTO);
        if(isUpdate){
            new Alert(Alert.AlertType.INFORMATION, "User updated successfully").show();
            refreshPage();
            loadTableData();
        }else{
            new Alert(Alert.AlertType.ERROR, "User could not be updated").show();
        }
        btnSaveUser.setDisable(false);

    }  @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));


        try{
            loadNextUserId();
        }catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "User could not be loaded").show();

        }
    }
    private void loadNextUserId() throws SQLException {
        String nextUserId = userBO.getNextId();
        txtUserID.setText(nextUserId);
    }

    @FXML
    void btnLoadOnAction(ActionEvent event) throws SQLException{
        loadTableData();
        loadNextUserId();
    }

    @FXML
    void onClickTable(MouseEvent event) {
            UserTM userTM = tblUser.getSelectionModel().getSelectedItem();
            if(userTM != null){
                txtUserID.setText(userTM.getUserId());
                txtPassword.setText(userTM.getPassword());
                txtEmail.setText(userTM.getEmail());

                btnLoadUser.setDisable(false);
                btnDeleteUser.setDisable(false);
                btnSaveUser.setDisable(true);
                btnUpdateUser.setDisable(false);
            }
        }

    }



