package com.example.finalproject.controller;

import com.example.finalproject.bo.BOFactory;
import com.example.finalproject.bo.custom.PaymentBO;
import com.example.finalproject.bo.custom.SupplierBO;
import com.example.finalproject.dao.custom.PaymentDAO;
import com.example.finalproject.dao.custom.ProductDAO;
import com.example.finalproject.dto.PaymentDTO;
import com.example.finalproject.dto.tm.PaymentTM;
import com.example.finalproject.dao.custom.impl.PaymentDAOImpl;
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

public class PaymentController implements Initializable {

     @FXML
    public TextField txtPayMethod;
    @FXML
    private AnchorPane ancPayment;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PaymentTM, String> colPayDate;

    @FXML
    private TableColumn<PaymentTM, String> colPayMethod;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentID;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private DatePicker dpPaymentDate;

    @FXML
    private TextField txtPayID;


//    PaymentDAOImpl paymentModel = new PaymentDAOImpl();
//
//    PaymentDAO paymentDAO = new PaymentDAOImpl();

    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);


    @FXML
    void btnDeleteOnClickTo(ActionEvent event) throws SQLException {
        String paymentId = txtPayID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = paymentBO.delete(paymentId);
            if (isDeleted) {

                new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully").show();
                refreshPage();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment could not be deleted").show();
            }
        }
        btnSave.setDisable(false);
        btnLoad.setDisable(false);
        loadNextPaymentId();
    }

    private void loadTableData() throws SQLException {
        ArrayList<PaymentDTO> paymentDTOS = paymentBO.getAll();

        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getPaymentId(),
                    paymentDTO.getPaymentMethod(),
                    paymentDTO.getPaymentDate()

            );
            paymentTMS.add(paymentTM);
        }

        tblPayment.setItems(paymentTMS);
    }

    @FXML
    void btnLoadOnClickTo(ActionEvent event) throws SQLException{
        loadTableData();

    }
    public void onClickTable(MouseEvent event) {
        PaymentTM paymentTM = tblPayment.getSelectionModel().getSelectedItem();
        if (paymentTM != null) {
            txtPayID.setText(paymentTM.getPaymentId());
            txtPayMethod.setText(paymentTM.getPaymentMethod());
            dpPaymentDate.setValue(LocalDate.parse(paymentTM.getPaymentDate()));

            btnLoad.setDisable(true);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void btnSaveOnClickTo(ActionEvent event) throws SQLException {
        String payId = txtPayID.getText();
        String payMethod = txtPayMethod.getText();
        String payDate = dpPaymentDate.getValue().toString();

        PaymentDTO paymentDTO = new PaymentDTO(payId, payMethod, payDate);

        boolean isSaved = paymentBO.save(paymentDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Payment Saved", ButtonType.OK).show();
            refreshPage();
            loadTableData();
            loadNextPaymentId();
        } else {
            new Alert(Alert.AlertType.ERROR, "Payment Not Saved", ButtonType.OK).show();

        }
        loadNextPaymentId();
    }

    private void refreshPage() throws SQLException {
        loadNextPaymentId();

        txtPayID.clear();
        txtPayMethod.clear();
        dpPaymentDate.setValue(null);

    }


    @FXML
    void btnUpdateOnClickTo(ActionEvent event) throws SQLException {
        String paymentId = txtPayID.getText();
        String paymentMethod = txtPayMethod.getText();
        String paymentDate = dpPaymentDate.getValue().toString();

        PaymentDTO paymentDTO = new PaymentDTO(
                paymentId,
                paymentMethod,
                paymentDate

        );

        boolean isUpdate = paymentBO.update(paymentDTO);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Payment updated successfully").show();
            refreshPage();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Payment could not be updated").show();

        }
        btnSave.setDisable(false);
        btnLoad.setDisable(false);
        loadNextPaymentId();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPayMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colPayDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));


        try {
            loadNextPaymentId();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Payment could not be loaded").show();

        }
    }


    private void loadNextPaymentId() throws SQLException {


        String nextPaymentId = paymentBO.getNextId();
        txtPayID.setText(nextPaymentId);
    }
}


