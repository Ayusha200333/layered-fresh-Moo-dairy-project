package com.example.finalproject.controller;

import com.example.finalproject.bo.BOFactory;
import com.example.finalproject.bo.custom.*;
import com.example.finalproject.dao.custom.OrdersDAO;
import com.example.finalproject.dao.custom.impl.*;
import com.example.finalproject.db.DBConnection;
import com.example.finalproject.dto.OrderDetailDTO;
import com.example.finalproject.dto.ProductDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import com.example.finalproject.dto.OrdersDTO;
import com.example.finalproject.dto.tm.OrdersTM;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    public TableColumn<OrdersTM,String> colDate;

    @FXML
    public TableColumn <OrdersTM ,Integer> colQty;

    @FXML
    public TextField txtQty;

    @FXML
    public ChoiceBox cbCusId;

    @FXML
    public ChoiceBox cbPayId;

    @FXML
    public ChoiceBox cbDeliId;

    @FXML
    public ChoiceBox cbProId;

    @FXML
    public TableColumn <OrdersTM ,Integer> colProId;

    @FXML
    private AnchorPane ancOrder;

    @FXML
    private TableColumn<OrdersTM ,String> colAmount;

    @FXML
    private TableColumn<OrdersTM ,String> colCusId;

    @FXML
    private TableColumn<OrdersTM ,String> colDeliId;

    @FXML
    private TableColumn<OrdersTM ,String> colOrderId;

    @FXML
    private TableColumn<OrdersDTO ,String> colPaymentID;

    @FXML
    private TableView<OrdersTM> tblOrder;

    @FXML
    private TextField txtAmount;

    @FXML
    private DatePicker dpOrdDate;

    @FXML
    private TextField txtOrdID;

    @FXML
    private Button btnShow;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    OrdersBO ordersBO = (OrdersBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDERS);
    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getInstance().getBO(BOFactory.BOType.DELIVERY);
    ProductBO productBO = (ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDERDETAIL);

    @FXML
    void btnDeleteOnClickTo(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        String orderId = txtOrdID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeleted = ordersBO.delete(orderId);
            if(isDeleted) {
                connection.commit();
                new Alert(Alert.AlertType.INFORMATION, "Order deleted successfully").show();
                refreshPage();
                loadTableData();

                boolean isItDelete = orderDetailBO.delete(orderId);
                if (isItDelete){
                    connection.commit();
                }else{
                    connection.rollback();
                }

            }else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Order could not be deleted").show();
            }
        }
        btnSave.setDisable(false);
        btnShow.setDisable(false);
        loadNextOrderId();
    }

    private void refreshPage() throws SQLException {
        loadNextOrderId();

        txtOrdID.clear();
        cbProId.getItems().clear();
        dpOrdDate.setValue(null);
        txtAmount.clear();
        cbCusId.getItems().clear();
        cbPayId.getItems().clear();
        cbDeliId.getItems().clear();
        txtQty.clear();

    }

    @FXML
    void btnSaveOnClickTo(ActionEvent actionEvent) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        connection.setAutoCommit(false);

        String orderId = txtOrdID.getText();
        String proId = cbProId.getValue().toString();
        String date = dpOrdDate.getValue().toString();
        String amount = txtAmount.getText();
        String cusID = cbCusId.getValue().toString();
        String payID = cbPayId.getValue().toString();
        String deliID = cbDeliId.getValue().toString();
        int qty = Integer.parseInt(txtQty.getText());


        ProductDTO productDTO = productBO.getProduct(proId);
        boolean isItSaved = productBO.update(productDTO);
        if(isItSaved){
            connection.commit();
            boolean isSaved = ordersBO.save(new OrdersDTO(orderId,proId,date,amount,cusID,payID,deliID,qty));

            if(isSaved) {
                connection.commit();
                new Alert(Alert.AlertType.INFORMATION, "Order added successfully").show();
                refreshPage();
                loadTableData();

                OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderId, proId, qty);
                boolean isAdded = orderDetailBO.save(orderDetailDTO);
                if (isAdded){
                    connection.commit();
                }else{
                    connection.rollback();
                }
            }else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Order could not be added").show();
            }
        }else{
            connection.rollback();
            new Alert(Alert.AlertType.ERROR, "Order could not be added").show();
        }

        btnSave.setDisable(false);
        loadNextOrderId();

    }

    @FXML
    void btnShowOnClickTo(ActionEvent event) throws SQLException {
        loadTableData();
        loadNextOrderId();

    }

    private void loadTableData() throws SQLException {
        ArrayList<OrdersDTO> ordersDTOS = ordersBO.getAll();

        ObservableList<OrdersTM> ordersTMS = FXCollections.observableArrayList();

        for(OrdersDTO ordersDTO : ordersDTOS) {
            OrdersTM ordersTM = new OrdersTM(
                    ordersDTO.getOrderId(),
                    ordersDTO.getProductId(),
                    ordersDTO.getDate(),
                    ordersDTO.getAmount(),
                    ordersDTO.getCusId(),
                    ordersDTO.getPaymentId(),
                    ordersDTO.getDeliveryId(),
                    ordersDTO.getQty()
            );
            ordersTMS.add(ordersTM);
        }
        tblOrder.setItems(ordersTMS);
    }



    @FXML
    void btnUpdateOnClickTo(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        String orderId = txtOrdID.getText();
        String proId = cbProId.getValue().toString();
        String date = dpOrdDate.getValue().toString();
        String amount = txtAmount.getText();
        String cusID = cbCusId.getValue().toString();
        String payID = cbPayId.getValue().toString();
        String deliID = cbDeliId.getValue().toString();
        int qty = Integer.parseInt(txtQty.getText());

        OrdersDTO ordersDTO = new OrdersDTO(
                orderId,
                proId,
                date,
                amount,
                cusID,
                payID,
                deliID,
                qty
        );
        boolean isSaved = ordersBO.update(ordersDTO);

        if(isSaved) {
            connection.commit();
            new Alert(Alert.AlertType.INFORMATION, "Order updated successfully").show();
            refreshPage();
            loadTableData();

            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderId, proId, qty);
            boolean isUpdate = orderDetailBO.update(orderDetailDTO);
            if (isUpdate) {
                connection.commit();
            }else{
                connection.rollback();
            }
        }else{
            connection.rollback();
            new Alert(Alert.AlertType.ERROR, "Order could not be updated").show();
        }
        btnSave.setDisable(false);
        btnShow.setDisable(false);
        loadNextOrderId();
    }

    public void loadNextOrderId() throws SQLException {

        String nextOrderId = ordersBO.getNextId();
        txtOrdID.setText(nextOrderId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colProId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colDeliId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        try{
            loadProductIdsIntoChoiceBox();
            loadCustomerIdsIntoChoiceBox();
            loadPaymentIdsIntoChoiceBox();
            loadDeliveryIdsIntoChoiceBox();

            loadNextOrderId();
        }catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error Loading next Order Id");
        }
    }

    private void loadDeliveryIdsIntoChoiceBox() throws SQLException {
        List<String> deliveryIds = deliveryBO.getAllIds();
        cbDeliId.getItems().addAll(deliveryIds);
        cbDeliId.setValue("Select Delivery Id");
    }

    private void loadPaymentIdsIntoChoiceBox() throws SQLException {
        List<String> paymentIds = paymentBO.getAllIds();
        cbPayId.getItems().addAll(paymentIds);
        cbPayId.setValue("Select Payment Id");
    }

    private void loadCustomerIdsIntoChoiceBox() throws SQLException {
        List<String> customerIds = customerBO.getAllIds();
        cbCusId.getItems().addAll(customerIds);
        cbCusId.setValue("Select Customer Id");
    }

    private void loadProductIdsIntoChoiceBox() throws SQLException {
        List<String> productIds = productBO.getAllIds();
        cbProId.getItems().addAll(productIds);
        cbProId.setValue("Select Product Ids");
    }

    @FXML
    void onClickTable(MouseEvent event) {
        OrdersTM ordersTM = tblOrder.getSelectionModel().getSelectedItem();

        if(ordersTM != null) {
            txtOrdID.setText(ordersTM.getOrderId());
            cbProId.setValue(ordersTM.getProductId());
            dpOrdDate.setValue(LocalDate.parse(ordersTM.getDate()));
            txtAmount.setText(ordersTM.getAmount());
            cbCusId.setValue(ordersTM.getCusId());
            cbPayId.setValue(ordersTM.getPaymentId());
            cbDeliId.setValue(ordersTM.getDeliveryId());
            txtQty.setText(String.valueOf(ordersTM.getQty()));

            btnSave.setDisable(true);
            btnShow.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }

}