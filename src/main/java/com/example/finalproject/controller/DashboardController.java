package com.example.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController {

    public Button userBtn;
    public Button employeeBtn;
    public Button customerBtn;
    public Button deliveryBtn;
    public Button prdCategeryBtn;
    public Button inventBtn;
    public Button returnBtn;
    public Button productBtn;
    public Button orderBtn;
    public Button batchBtn;
    public Button orderDetailBtn;
    public Button supplierBtn;
    public Button InventSuppBtn;
    public Button paymentBtn;
    public AnchorPane abcd;
    public AnchorPane anc2;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTO("/view/Customer.fxml");
    }

    public void navigateToCustomerPage(ActionEvent actionEvent) {
        navigateTO("/view/Customer.fxml");
    }


    public void navigateToDeliveryPage(ActionEvent actionEvent) {
        navigateTO("/view/Delivery.fxml");
    }

    public void navigateToPrdCategoryPage(ActionEvent actionEvent) {
        navigateTO("/view/ProductCategory.fxml");
    }

    public void navigateToInventoryPage(ActionEvent actionEvent) {
        navigateTO("/view/Inventory.fxml");
    }

    public void navigateToReturnDetailPage(ActionEvent actionEvent) {
        navigateTO("/view/Return.fxml");
    }

    public void productOnAction(ActionEvent actionEvent) {
        navigateTO("/view/Product.fxml");
    }

    public void navigateToIOrdersPage(ActionEvent actionEvent) {
        navigateTO("/view/Orders.fxml");
    }

    public void navigateToBatchPage(ActionEvent actionEvent) {
        navigateTO("/view/Batch.fxml");
    }

    public void navigateToOrderDetailPage(ActionEvent actionEvent) {
        navigateTO("/view/OrderDetail.fxml");
    }

    public void navigateToSupplierPage(ActionEvent actionEvent) {
        navigateTO("/view/Supplier.fxml");
    }

    public void navigateToInvenSupplierPage(ActionEvent actionEvent) {
        navigateTO("/view/InventorySupplier.fxml");
    }

    public void navigateToPaymentPage(ActionEvent actionEvent) {
        navigateTO("/view/Payment.fxml");
    }

    public void navigateToUserPage(ActionEvent actionEvent) {
        navigateTO("/view/User.fxml");
    }

    public void navigateToEmployeePage(ActionEvent actionEvent) {
        navigateTO("/view/Employee.fxml");
    }


    private void navigateTO(String fxmlPath) {
        try {
            abcd.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            abcd.getChildren().add(load);
        } catch (Exception e) {
            e.printStackTrace();
            //new Alert(Alert.AlertType.ERROR,"Fail to load ui").show();
        }
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        anc2.getChildren().clear();
        anc2.getChildren().add(load);
    }
}
