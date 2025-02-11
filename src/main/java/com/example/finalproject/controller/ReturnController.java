package com.example.finalproject.controller;

import com.example.finalproject.bo.BOFactory;
import com.example.finalproject.bo.custom.ReturnBO;
import com.example.finalproject.bo.custom.SupplierBO;
import com.example.finalproject.dao.custom.ReturnDAO;
import com.example.finalproject.dto.ReturnDTO;
import com.example.finalproject.dto.tm.ReturnTM;
import com.example.finalproject.dao.custom.impl.ReturnDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReturnController implements Initializable {

    @FXML
    public DatePicker dpReturnDate;

    @FXML
    private AnchorPane ancReturn;

    @FXML
    private Button btnDeleteReturn;

    @FXML
    private Button btnLoadReturn;

    @FXML
    private Button btnSaveReturn;

    @FXML
    private Button btnUpdateReturn;

    @FXML
    private TableColumn<ReturnTM, String> colReturnDate;

    @FXML
    private TableColumn<ReturnTM, String> colReturnId;

    @FXML
    private TableView<ReturnTM> tblReturn;

    @FXML
    private TextField txtReturnId;

//    ReturnDAOImpl returnModel = new ReturnDAOImpl();
//
//    ReturnDAO returnDAO = new ReturnDAOImpl();

    ReturnBO returnBO = (ReturnBO) BOFactory.getInstance().getBO(BOFactory.BOType.RETURN);



    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String returnId = txtReturnId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = returnBO.delete(returnId);
            if (isDeleted) {

                new Alert(Alert.AlertType.INFORMATION, "Return deleted successfully").show();
                refreshPage();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Return could not be deleted").show();
            }
        }
        btnLoadReturn.setDisable(false);
        btnSaveReturn.setDisable(false);
        loadNextReturnId();
    }

    private void loadTableData() throws SQLException {
        ArrayList<ReturnDTO> returnDTOS = returnBO.getAll();

        ObservableList<ReturnTM> returnTMS = FXCollections.observableArrayList();

        for (ReturnDTO returnDTO : returnDTOS) {
            ReturnTM returnTM = new ReturnTM(
                    returnDTO.getReturnId(),
                    returnDTO.getReturnDate()
            );
            returnTMS.add(returnTM);
        }

        tblReturn.setItems(returnTMS);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String returnId = txtReturnId.getText();
        String returnDate = dpReturnDate.getValue().toString();

        ReturnDTO returnDTO = new ReturnDTO(returnId, returnDate);

        boolean isSaved = returnBO.save(returnDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Return Saved", ButtonType.OK).show();
            refreshPage();
            loadTableData();
            loadNextReturnId();
        } else {
            new Alert(Alert.AlertType.ERROR, "Return Not Saved", ButtonType.OK).show();

        }
        loadNextReturnId();
    }

    private void refreshPage() throws SQLException {
        loadNextReturnId();

        txtReturnId.clear();
        dpReturnDate.setValue(null);

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String returnId = txtReturnId.getText();
        String returnDate = dpReturnDate.getValue().toString();


        ReturnDTO returnDTO = new ReturnDTO(
                returnId,
                returnDate

        );

        boolean isUpdate = returnBO.update(returnDTO);
        if(isUpdate){
            new Alert(Alert.AlertType.INFORMATION, "Return updated successfully").show();
            refreshPage();
            loadTableData();
        }else{
            new Alert(Alert.AlertType.ERROR, "Return could not be updated").show();

        }
        btnLoadReturn.setDisable(false);
        btnSaveReturn.setDisable(false);
        loadNextReturnId();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colReturnId.setCellValueFactory(new PropertyValueFactory<>("returnId"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));


        try{
            loadNextReturnId();
        }catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Return could not be loaded").show();

        }
    }
    private void loadNextReturnId() throws SQLException {


        String nextReturnId = returnBO.getNextId();
        txtReturnId.setText(nextReturnId);
    }

    @FXML
    void btnLoadOnAction(ActionEvent event) throws SQLException {
        loadTableData();

    }


    @FXML
    void onClickTable(MouseEvent event) {
        ReturnTM returnTM = tblReturn.getSelectionModel().getSelectedItem();
        if(returnTM != null){
            txtReturnId.setText(returnTM.getReturnId());
            dpReturnDate.setValue(LocalDate.parse(returnTM.getReturnDate()));


            btnLoadReturn.setDisable(true);
            btnDeleteReturn.setDisable(false);
            btnSaveReturn.setDisable(true);
            btnUpdateReturn.setDisable(false);
        }
    }
}

