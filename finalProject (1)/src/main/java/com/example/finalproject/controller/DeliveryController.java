package com.example.finalproject.controller;

import com.example.finalproject.bo.BOFactory;
import com.example.finalproject.bo.custom.BatchBO;
import com.example.finalproject.bo.custom.DeliveryBO;
import com.example.finalproject.dao.custom.DeliveryDAO;
import com.example.finalproject.dto.DeliveryDTO;
import com.example.finalproject.dto.tm.DeliveryTM;
import com.example.finalproject.dao.custom.impl.DeliveryDAOImpl;
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

public class DeliveryController implements Initializable {

    @FXML
    public AnchorPane ancDeli;

    @FXML
    private Button btnDeleteDeli;

    @FXML
    private Button btnLoadDeli;

    @FXML
    private Button btnSaveDeli;

    @FXML
    private Button btnUpdateDeli;

    @FXML
    private TableColumn<DeliveryTM, String> colDeliDate;

    @FXML
    private TableColumn<DeliveryTM, String> colDeliveryId;

    @FXML
    private TableColumn<DeliveryTM, String> colDestination;

    @FXML
    private TableView<DeliveryTM> tblDelivery;

    @FXML
    private DatePicker dpDeliDate;

    @FXML
    private TextField txtDeliDestination;

    @FXML
    private TextField txtDeliID;

//    DeliveryDAOImpl deliveryModel = new DeliveryDAOImpl();
//    DeliveryDAO deliveryDAO = new DeliveryDAOImpl();

    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getInstance().getBO(BOFactory.BOType.DELIVERY);


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String deliveryId = txtDeliID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){

            boolean isDeleted = deliveryBO.delete(deliveryId);
            if(isDeleted) {

                new Alert(Alert.AlertType.INFORMATION, "Delivery deleted successfully").show();
                refreshPage();
                loadTableData();
            }else{
                new Alert(Alert.AlertType.ERROR, "Delivery could not be deleted").show();
            }
        }
        btnLoadDeli.setDisable(false);
        btnSaveDeli.setDisable(false);
        loadNextDeliveryId();

    }

    private void loadTableData() throws SQLException {
        ArrayList <DeliveryDTO> deliveryDTOS = deliveryBO.getAll();

        ObservableList<DeliveryTM> deliveryTMS = FXCollections.observableArrayList();

        for (DeliveryDTO deliveryDTO : deliveryDTOS) {
            DeliveryTM deliveryTM = new DeliveryTM(
                    deliveryDTO.getDeliveryId(),
                    deliveryDTO.getDeliveryDate(),
                    deliveryDTO.getDestination()
            );
            deliveryTMS.add(deliveryTM);
        }

        tblDelivery.setItems(deliveryTMS);

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String deliveryId = txtDeliID.getText();
        String deliveryDate = dpDeliDate.getValue().toString();
        String destination = txtDeliDestination.getText();

        DeliveryDTO deliveryDTO = new DeliveryDTO(deliveryId,deliveryDate,destination);

        boolean isSaved = deliveryBO.save(deliveryDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Delivery Saved", ButtonType.OK).show();
            refreshPage();
            loadTableData();
            loadNextDeliveryId();
        } else {
            new Alert(Alert.AlertType.ERROR, "Delivery Not Saved", ButtonType.OK).show();

        }
        loadNextDeliveryId();
    }
    private void refreshPage() throws SQLException{
        loadNextDeliveryId();

        txtDeliID.clear();
        dpDeliDate.setValue(null);
        txtDeliDestination.clear();
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String deliveryId = txtDeliID.getText();
        String deliveryDate = dpDeliDate.getValue().toString();
        String destination = txtDeliDestination.getText();

        DeliveryDTO deliveryDTO = new DeliveryDTO(
                deliveryId,
                deliveryDate,
                destination
        );

        boolean isUpdate = deliveryBO.update(deliveryDTO);
        if(isUpdate){
            new Alert(Alert.AlertType.INFORMATION, "Delivery updated successfully").show();
            refreshPage();
            loadTableData();
        }else{
            new Alert(Alert.AlertType.ERROR, "Delivery could not be updated").show();

        }
        btnLoadDeli.setDisable(false);
        btnSaveDeli.setDisable(false);
        loadNextDeliveryId();
    }

    @FXML
    void onClickTable(MouseEvent event) {

        DeliveryTM deliveryTM = tblDelivery.getSelectionModel().getSelectedItem();
       if(deliveryTM != null) {
           txtDeliID.setText(deliveryTM.getDeliveryId());
           dpDeliDate.setValue(LocalDate.parse(deliveryTM.getDeliveryDate()));
           txtDeliDestination.setText(deliveryTM.getDestination());

           btnLoadDeli.setDisable(true);
           btnDeleteDeli.setDisable(false);
           btnSaveDeli.setDisable(true);
           btnUpdateDeli.setDisable(false);
       }
    }
    @FXML
    void btnLoadOnAction(ActionEvent event) throws SQLException{
        loadTableData();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colDeliDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

        try{
            loadNextDeliveryId();
        }catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Delivery could not be loaded").show();

        }
    }
    private void loadNextDeliveryId() throws SQLException {


        String nextDeliveryId = deliveryBO.getNextId();
        txtDeliID.setText(nextDeliveryId);
    }
}
