package com.example.finalproject.controller;

import com.example.finalproject.bo.BOFactory;
import com.example.finalproject.bo.custom.BatchBO;
import com.example.finalproject.bo.custom.CustomerBO;
import com.example.finalproject.bo.custom.ProductBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.dto.tm.BatchTM;
import com.example.finalproject.dao.custom.impl.BatchDAOImpl;
import com.example.finalproject.dao.custom.impl.ProductDAOImpl;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BatchController implements Initializable {

    @FXML
    public ChoiceBox cbProId;

    @FXML
    private AnchorPane ancBatch;

    @FXML
    private Button btnDeleteBatch;

    @FXML
    private Button btnSaveBatch;

    @FXML
    private Button btnUpdateBatch;

    @FXML
    private Button btnLoadBatch;

    @FXML
    private TableColumn<BatchTM, String> colBatchId;

    @FXML
    private TableColumn<BatchTM, String> colPrice;

    @FXML
    private TableColumn<BatchTM, String> colProId;

    @FXML
    private TextField txtBatchId;

    @FXML
    private TableView<BatchTM> tblBatch;

    @FXML
    private TextField txtPrice;

//    BatchDAOImpl batchModel = new BatchDAOImpl();
//    ProductDAOImpl productModel = new ProductDAOImpl();
//    BatchDAO batchDAO = new BatchDAOImpl();

//    BatchDAO batchDAO = (BatchDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BATCH);

    BatchBO batchBO = (BatchBO) BOFactory.getInstance().getBO(BOFactory.BOType.BATCH);
    ProductBO productBO = (ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String batchId = txtBatchId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = batchBO.delete(batchId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Batch deleted successfully").show();
                refreshPage();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Batch could not be deleted").show();
            }
        }
    }

    private void loadTableData() throws SQLException {
        ArrayList<BatchDTO> batchDTOS = batchBO.getAll();

        ObservableList<BatchTM> batchTMS = FXCollections.observableArrayList();
        for (BatchDTO batchDTO : batchDTOS) {
            BatchTM batchTM = new BatchTM(
                    batchDTO.getBatchId(),
                    batchDTO.getPrice(),
                    batchDTO.getProductId()
            );
            batchTMS.add(batchTM);
        }

        tblBatch.setItems(batchTMS);

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String batchId = txtBatchId.getText();
        String price = txtPrice.getText();
        String productId = cbProId.getValue().toString();

        BatchDTO batchDTO = new BatchDTO(batchId, price, productId);

        boolean isSaved = batchBO.save(batchDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Batch is Saved", ButtonType.OK).show();
            refreshPage();
            loadTableData();
            loadNextBatchId();
        } else {
            new Alert(Alert.AlertType.ERROR, "Batch is  Not Saved", ButtonType.OK).show();

        }
    }

    private void refreshPage() throws SQLException {
        loadNextBatchId();

        txtBatchId.clear();
        txtPrice.clear();
        cbProId.getItems().clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String batchId = txtBatchId.getText();
        String price = txtPrice.getText();
        String productId = cbProId.getValue().toString();

        BatchDTO batchDTO = new BatchDTO(
                batchId,
                price,
                productId
        );

        boolean isUpdate = batchBO.update(batchDTO);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Batch updated successfully").show();
            refreshPage();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Batch could not be updated").show();

        }
    }

//    @FXML
//    void onClickTable(MouseEvent event) throws SQLException {
//        BatchTM batchTM = tblBatch.getSelectionModel().getSelectedItem();
//        if (batchTM != null) {
//            txtBatchId.setText(batchTM.getBatchId());
//            txtPrice.setText(batchTM.getPrice());
//            txtPrdId.setText(batchTM.getProductId());
//
//            btnDeleteBatch.setDisable(false);
//            btnSaveBatch.setDisable(true);
//            btnUpdateBatch.setDisable(true);
//            btnLoadBatch.setDisable(true);
//        }
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProId.setCellValueFactory(new PropertyValueFactory<>("productId"));


        try {
            loadNextBatchId();
            loadCustomerIdsIntoChoiceBox();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Batch could not be loaded").show();

        }
    }
    private void loadCustomerIdsIntoChoiceBox() throws SQLException {
        List<String> productIds = productBO.getAllIds();
        cbProId.getItems().addAll(productIds);
        cbProId.setValue("Select Product Id");
    }

    private void loadNextBatchId() throws SQLException {
        String nextCustomerId = batchBO.getNextId();
        txtBatchId.setText(nextCustomerId);
    }

    @FXML
    void btnLoadOnAction(ActionEvent event) throws SQLException {
        loadTableData();
    }

    public void onClickBatchTable(MouseEvent event) {
        BatchTM batchTM = tblBatch.getSelectionModel().getSelectedItem();
        if (batchTM != null) {
            txtBatchId.setText(batchTM.getBatchId());
            txtPrice.setText(batchTM.getPrice());
            cbProId.setValue(batchTM.getProductId());

            btnDeleteBatch.setDisable(false);
            btnSaveBatch.setDisable(true);
            btnUpdateBatch.setDisable(false);
            btnLoadBatch.setDisable(true);
        }
    }
}

