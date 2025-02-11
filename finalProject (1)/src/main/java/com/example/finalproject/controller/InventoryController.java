package com.example.finalproject.controller;

 import com.example.finalproject.bo.BOFactory;
 import com.example.finalproject.bo.custom.BatchBO;
 import com.example.finalproject.bo.custom.InventoryBO;
 import com.example.finalproject.bo.custom.InventorySupplierBO;
 import com.example.finalproject.bo.custom.SupplierBO;
 import com.example.finalproject.dao.custom.DeliveryDAO;
 import com.example.finalproject.dao.custom.InventoryDAO;
 import com.example.finalproject.dao.custom.impl.DeliveryDAOImpl;
 import com.example.finalproject.db.DBConnection;
 import com.example.finalproject.dto.InventoryDTO;
 import com.example.finalproject.dto.InventorySupplierDTO;
 import com.example.finalproject.dto.tm.InventoryTM;
 import com.example.finalproject.dao.custom.impl.InventoryDAOImpl;
 import com.example.finalproject.dao.custom.impl.InventorySupplierDAOImpl;
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
 import java.sql.Connection;
 import java.sql.SQLException;
import java.util.ArrayList;
 import java.util.List;
 import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML
    public AnchorPane ancInventory;

    @FXML
    public TableColumn <InventoryTM, String> colSupId;

    @FXML
    public TableColumn <InventoryTM, Integer> colQty;

    @FXML
    public ChoiceBox cbSupId;

    @FXML
    private Button btnDeleteInvent;

    @FXML
    private Button btnLoadInvent;

    @FXML
    private Button btnSaveInvent;

    @FXML
    private Button btnUpdateInvent;

    @FXML
    private TableColumn<InventoryTM, String> colInventId;

    @FXML
    private TableView<InventoryTM> tblInventory;

    @FXML
    private TextField txtInventoryID;

    @FXML
    private TextField txtQuantity;

//
//    InventoryDAOImpl inventoryModel = new InventoryDAOImpl();
//    SupplierDAOImpl supplierModel = new SupplierDAOImpl();
//    InventorySupplierDAOImpl inventorySupplierModel = new InventorySupplierDAOImpl();

    InventoryBO inventoryBO = (InventoryBO) BOFactory.getInstance().getBO(BOFactory.BOType.INVENTORY);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);
    InventorySupplierBO inventorySupplierBO = (InventorySupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.INVENTORYSUPPLIER);

//    InventoryDAO inventoryDAO = new InventoryDAOImpl();

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        String invenId = txtInventoryID.getText();
        String supId = cbSupId.getValue().toString();
        int  qty = Integer.parseInt(txtQuantity.getText());


        InventoryDTO inventoryDTO = new InventoryDTO(invenId, supId, qty );

        boolean isSaved = inventoryBO.save(inventoryDTO);
        if(isSaved){
            connection.commit();
            new Alert(Alert.AlertType.INFORMATION, "Inventory Saved", ButtonType.OK).show();
            refreshPage();
            loadTableData();
            loadNextInventoryId();

            InventorySupplierDTO inventorySupplierDTO = new InventorySupplierDTO(invenId,supId,qty);
            boolean isItSaved = inventorySupplierBO.save(inventorySupplierDTO);
            if (isItSaved){
                connection.commit();
            }else{
                connection.rollback();
            }
        }else{
            connection.rollback();
            new Alert(Alert.AlertType.ERROR, "Inventory Not Saved", ButtonType.OK).show();

        }
        loadNextInventoryId();
    }
    private void refreshPage() throws SQLException{
        loadNextInventoryId();

        txtInventoryID.clear();
        cbSupId.setValue(null);
        txtQuantity.clear();

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        String inventoryId = txtInventoryID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){

            boolean isDeleted = inventoryBO.delete(inventoryId);
            if(isDeleted) {
                connection.commit();
                new Alert(Alert.AlertType.INFORMATION, "Inventory deleted successfully").show();
                refreshPage();
                loadTableData();

                boolean isItDelete = inventoryBO.delete(inventoryId);
                if (isItDelete) {
                    connection.commit();
                }else{
                    connection.rollback();
                }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Inventory could not be deleted").show();
            }
        }
        btnSaveInvent.setDisable(false);
        btnLoadInvent.setDisable(false);
        loadNextInventoryId();
    }

    private void loadTableData() throws SQLException {
        ArrayList <InventoryDTO> inventoryDTOS = inventoryBO.getAll();

        ObservableList<InventoryTM> inventoryTMS = FXCollections.observableArrayList();

        for (InventoryDTO inventoryDTO : inventoryDTOS) {
            InventoryTM inventoryTM = new InventoryTM(
                    inventoryDTO.getInventoryId(),
                    inventoryDTO.getSupId(),
                    inventoryDTO.getQty()
            );
            inventoryTMS.add(inventoryTM);
        }

        tblInventory.setItems(inventoryTMS);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        String invenId = txtInventoryID.getText();
        String supId = cbSupId.getValue().toString();
        int qty = Integer.parseInt(txtQuantity.getText());


        InventoryDTO inventoryDTO = new InventoryDTO(
                invenId,
                supId,
                qty

        );

        boolean isUpdate = inventoryBO.update(inventoryDTO);
        if(isUpdate){
            connection.commit();
            new Alert(Alert.AlertType.INFORMATION, "Inventory updated successfully").show();
            refreshPage();
            loadTableData();
            InventorySupplierDTO inventorySupplierDTO = new InventorySupplierDTO(invenId,supId,qty);
            boolean isItUpdated = inventoryBO.update(inventoryDTO);
            if (isItUpdated){
                connection.commit();
            }else{
                connection.rollback();
            }
        }else{
            connection.rollback();
            new Alert(Alert.AlertType.ERROR, "Inventory could not be updated").show();

        }
        btnSaveInvent.setDisable(false);
        btnLoadInvent.setDisable(false);
        loadNextInventoryId();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInventId.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));


        try{
            loadSupplierIdsIntoChoiceBox();
            loadNextInventoryId();
        }catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Inventory could not be loaded").show();

        }
    }
    private void loadNextInventoryId() throws SQLException {


        String nextInventoryId = inventoryBO.getNextId();
        txtInventoryID.setText(nextInventoryId);
    }

    @FXML
    void btnLoadOnAction(ActionEvent event) throws SQLException{
        loadTableData();

    }


    public void onClickTable(MouseEvent event) {
        InventoryTM inventoryTM = tblInventory.getSelectionModel().getSelectedItem();
        if(inventoryTM != null){
            txtInventoryID.setText(inventoryTM.getInventoryId());
            cbSupId.setValue(inventoryTM.getSupId());
            txtQuantity.setText(String.valueOf(inventoryTM.getQty()));


            btnLoadInvent.setDisable(true);
            btnDeleteInvent.setDisable(false);
            btnSaveInvent.setDisable(true);
            btnUpdateInvent.setDisable(false);
        }
    }
    private void loadSupplierIdsIntoChoiceBox() throws SQLException {
        List<String> supplierIds = supplierBO.getAllIds();
        cbSupId.getItems().addAll(supplierIds);
        cbSupId.setValue("Select Supplier Ids");
    }

}
