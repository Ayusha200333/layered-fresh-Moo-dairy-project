package com.example.finalproject.controller;

 import com.example.finalproject.bo.BOFactory;
 import com.example.finalproject.bo.custom.SupplierBO;
 import com.example.finalproject.dao.custom.SupplierDAO;
 import com.example.finalproject.dto.SupplierDTO;
 import com.example.finalproject.dto.tm.SupplierTM;
 import com.example.finalproject.dao.custom.impl.SupplierDAOImpl;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierController implements Initializable {

    @FXML
    private AnchorPane ancSupplier1;

    @FXML
    private Button btnDeleteSupplier;

    @FXML
    private Button btnSaveSupplier;

    @FXML
    private Button btnUpdateSupplier;

    @FXML
    private Button btnLoadSupplier;

    @FXML
    private TableColumn<SupplierTM, String> colPhone;

    @FXML
    private TableColumn<SupplierTM, String> colSuppName;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierId;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TextField txtSupPhone;

    @FXML
    private TextField txtSuppName;

//    SupplierDAOImpl supplierModel = new SupplierDAOImpl();
//
//    SupplierDAO supplierDAO = new SupplierDAOImpl();

    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String supplierId = txtSupplierId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierBO.delete(supplierId);
            if (isDeleted) {

                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully").show();
                refreshPage();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier could not be deleted").show();
            }
        }
    }

    private void loadTableData() throws SQLException {
        ArrayList<SupplierDTO> supplierDTOS = supplierBO.getAll();

        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDTO supplierDTO : supplierDTOS) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplierId(),
                    supplierDTO.getSupplierName(),
                    supplierDTO.getPhoneNO()

            );
            supplierTMS.add(supplierTM);
        }

        tblSupplier.setItems(supplierTMS);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String supplierId = txtSupplierId.getText();
        String supplierName = txtSuppName.getText();
        String phoneNo = txtSupPhone.getText();

        boolean isValidPhoneNo = isValidPhoneNo(phoneNo);
        if (isValidPhoneNo) {
            SupplierDTO supplierDTO = new SupplierDTO(supplierId, supplierName, phoneNo);

            boolean isSaved = supplierBO.save(supplierDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Saved", ButtonType.OK).show();
                refreshPage();
                loadTableData();
                loadNextSupplierId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Saved", ButtonType.OK).show();

            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact Number", ButtonType.OK).show();
        }
    }

    private boolean isValidPhoneNo(String phoneNo) {
        String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }

    private void refreshPage() throws SQLException {
        loadNextSupplierId();

        txtSupplierId.setText("");
        txtSuppName.clear();
        txtSupPhone.clear();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String supplierId = txtSupplierId.getText();
        String supplierName = txtSuppName.getText();
        String phoneNo = txtSupPhone.getText();


        SupplierDTO supplierDTO = new SupplierDTO(
                supplierId,
                supplierName,
                phoneNo
        );

        boolean isUpdate = supplierBO.update(supplierDTO);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully").show();
            refreshPage();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Supplier could not be updated").show();

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSuppName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNO"));


        try {
            loadNextSupplierId();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Supplier could not be loaded").show();

        }
    }

    private void loadNextSupplierId() throws SQLException {


        String nextSupplierId = supplierBO.getNextId();
        txtSupplierId.setText(nextSupplierId);
    }

    @FXML
    void btnLoadOnAction(ActionEvent event) throws SQLException {
        loadTableData();

    }

    @FXML
    void onClickTable(MouseEvent event) {
        SupplierTM supplierTM = tblSupplier.getSelectionModel().getSelectedItem();
        if (supplierTM != null) {
            txtSupplierId.setText(supplierTM.getSupplierId());
            txtSuppName.setText(supplierTM.getSupplierName());
            txtSupPhone.setText(supplierTM.getPhoneNO());

            btnDeleteSupplier.setDisable(false);
            btnSaveSupplier.setDisable(true);
            btnUpdateSupplier.setDisable(false);

        }

    }
}

