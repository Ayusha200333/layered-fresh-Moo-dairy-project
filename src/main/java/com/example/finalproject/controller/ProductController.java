package com.example.finalproject.controller;

import com.example.finalproject.bo.BOFactory;
import com.example.finalproject.bo.custom.ProductBO;
import com.example.finalproject.bo.custom.SupplierBO;
import com.example.finalproject.dao.custom.ProductDAO;
import com.example.finalproject.dto.ProductDTO;
import com.example.finalproject.dto.tm.ProductTM;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    public AnchorPane ancProduct;

    @FXML
    public TableColumn colQty;

    @FXML
    public TextField txtQty;

    @FXML
    private Button btnDeleteProduct;

    @FXML
    private Button btnLoadProduct;

    @FXML
    private Button btnSaveProduct;

    @FXML
    private Button btnUpdateProduct;

    @FXML
    private TableColumn<ProductTM,String> colProductId;

    @FXML
    private TableColumn<ProductTM,String> colName;

    @FXML
    private TableColumn<ProductTM,Double> colPrice;


    @FXML
    private TextField txtProductId;

    @FXML
    private TableView<ProductTM> tblProduct;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtProductPrice;

//    ProductDAOImpl productModel = new ProductDAOImpl();
//    ProductDAO productDAO = new ProductDAOImpl();

    ProductBO productBO = (ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String proId = txtProductId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){

            boolean isDeleted = productBO.delete(proId);
            if(isDeleted) {

                new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully").show();
                refreshPage();
                loadTableData();
            }else{
                new Alert(Alert.AlertType.ERROR, "Product could not be deleted").show();
            }
        }
        btnSaveProduct.setDisable(false);
        btnLoadProduct.setDisable(false);
        loadNextProductId();
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String proId = txtProductId.getText();
        String proName = txtProductName.getText();
        double price = Double.parseDouble(txtProductPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        ProductDTO productDto = new ProductDTO(proId, proName, price, qty);

        boolean isSaved = productBO.save(productDto);
        if(isSaved){
            new Alert(Alert.AlertType.INFORMATION, "Product Saved", ButtonType.OK).show();
            refreshPage();
            loadTableData();
            loadNextProductId();
        }else{
            new Alert(Alert.AlertType.ERROR, "Product Not Saved", ButtonType.OK).show();

        }
        loadNextProductId();
    }

    private void loadTableData() throws SQLException {
        ArrayList <ProductDTO> productDTOS = productBO.getAll();

        ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();

        for (ProductDTO productDTO : productDTOS) {
            ProductTM productTM = new ProductTM(
                    productDTO.getProId(),
                    productDTO.getProName(),
                    productDTO.getPrice(),
                    productDTO.getQty()
            );
            productTMS.add(productTM);
        }

        tblProduct.setItems(productTMS);
    }

    private void refreshPage() throws SQLException {
        loadNextProductId();

        txtProductId.clear();
        txtProductName.clear();
        txtProductPrice.clear();
        txtQty.clear();

    }

    private void loadNextProductId() throws SQLException {
        String nextProductId = productBO.getNextId();
        txtProductId.setText(nextProductId);
    }

    @FXML
    void btnLoadOnAction(ActionEvent event) throws SQLException {
        loadTableData();
        loadNextProductId();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String proId = txtProductId.getText();
        String proName = txtProductName.getText();
        double price = Double.parseDouble(txtProductPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        ProductDTO productDto = new ProductDTO(proId, proName, price, qty);


        boolean isUpdate = productBO.update(productDto);
        if(isUpdate){
            new Alert(Alert.AlertType.INFORMATION, "Product updated successfully").show();
            refreshPage();
            loadTableData();
        }else{
            new Alert(Alert.AlertType.ERROR, "Product could not be updated").show();

        }
        btnSaveProduct.setDisable(false);
        btnLoadProduct.setDisable(false);
        loadNextProductId();
    }

    public void onClickTable(MouseEvent event) {
        ProductTM productTM = tblProduct.getSelectionModel().getSelectedItem();
        if(productTM != null){
            txtProductId.setText(productTM.getProId());
            txtProductName.setText(productTM.getProName());
            txtProductPrice.setText(String.valueOf(productTM.getPrice()));
            txtQty.setText(String.valueOf(productTM.getQty()));

            //btnLoadCustomer.setDisable(false);
            btnDeleteProduct.setDisable(false);
            btnSaveProduct.setDisable(true);
            btnUpdateProduct.setDisable(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("proId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("proName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        try {
            loadNextProductId();
        } catch (Exception e) {
            System.out.println(e);
        }
    }



}

