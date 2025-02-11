 package com.example.finalproject.controller;

 import com.example.finalproject.bo.BOFactory;
 import com.example.finalproject.bo.custom.ProductCategoryBO;
 import com.example.finalproject.bo.custom.SupplierBO;
 import com.example.finalproject.dao.custom.ProductCategoryDAO;
 import com.example.finalproject.dto.ProductCategoryDTO;
 import com.example.finalproject.dto.tm.ProductCategoryTM;
 import com.example.finalproject.dao.custom.impl.ProductCategoryDAOImpl;
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

 public class ProductCategoryController implements Initializable {

     @FXML
     private AnchorPane ancPrdCategory;

     @FXML
     private Button btnDeletePrdCategory;

     @FXML
     private Button btnLoadPrdCategory;

     @FXML
     private Button btnSavePrdCategory;

     @FXML
     private Button btnUpdatePrdCategory;

     @FXML
     private TableColumn<ProductCategoryTM, String> colCategoryId;

     @FXML
     private TableColumn<ProductCategoryTM, String> colCategoryName;

     @FXML
     private TableView<ProductCategoryTM> tblPrdCategory;

     @FXML
     private TextField txtCategoryId;

     @FXML
     private TextField txtCategoryName;
//
//     ProductCategoryDAOImpl productCategoryModel  = new ProductCategoryDAOImpl();
//
//     ProductCategoryDAO productCategoryDAO = new ProductCategoryDAOImpl();

     ProductCategoryBO productCategoryBO = (ProductCategoryBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCTCATEGORY);

     @FXML
     void btnDeleteOnAction(ActionEvent event) throws SQLException {
         String categoryId = txtCategoryId.getText();

         Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.YES,ButtonType.NO);
         Optional<ButtonType> optionalButtonType = alert.showAndWait();

         if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
             boolean isDeleted = productCategoryBO.delete(categoryId);
             if(isDeleted) {

                 new Alert(Alert.AlertType.INFORMATION, "Product Category deleted successfully").show();
                 refreshPage();
                 loadTableData();
             }else{
                 new Alert(Alert.AlertType.ERROR, "Product Category could not be deleted").show();
             }
         }
         btnLoadPrdCategory.setDisable(false);
         btnSavePrdCategory.setDisable(false);
         loadNextCategoryId();

     }

     private void loadTableData() throws SQLException {
         ArrayList<ProductCategoryDTO> productCategoryDTOS = productCategoryBO.getAll();

         ObservableList<ProductCategoryTM> productCategoryTMS = FXCollections.observableArrayList();

         for (ProductCategoryDTO productCategoryDTO : productCategoryDTOS) {
             ProductCategoryTM productCategoryTM = new ProductCategoryTM(
                     productCategoryDTO.getCategoryId(),
                     productCategoryDTO.getCategoryName()
             );
             productCategoryTMS.add(productCategoryTM);
         }

         tblPrdCategory.setItems(productCategoryTMS);
     }



     @FXML
     void btnLoadOnAction(ActionEvent event) throws SQLException {
         loadTableData();

     }

     @FXML
     void btnSaveOnAction(ActionEvent event) throws SQLException {
            String categoryId = txtCategoryId.getText();
            String categoryName = txtCategoryName.getText();

         ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(categoryId, categoryName);

         boolean isSaved = productCategoryBO.save(productCategoryDTO);
         if(isSaved){
             new Alert(Alert.AlertType.INFORMATION, "Product Category Saved", ButtonType.OK).show();
             refreshPage();
             loadTableData();
             loadNextCategoryId();
         }else{
             new Alert(Alert.AlertType.ERROR, "Product Category Not Saved", ButtonType.OK).show();

         }
         loadNextCategoryId();
     }

     private void refreshPage() throws SQLException {
         loadNextCategoryId();

         txtCategoryId.clear();
         txtCategoryName.clear();
     }

     private void loadNextCategoryId() throws SQLException{
         String nextCategoryId = productCategoryBO.getNextId();
         txtCategoryId.setText(nextCategoryId);
     }

     @FXML
     void btnUpdateOnAction(ActionEvent event) throws SQLException {
            String categoryId = txtCategoryId.getText();
            String categoryName = txtCategoryName.getText();

            ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(categoryId, categoryName);

            boolean isUpdate = productCategoryBO.update(productCategoryDTO);
            if(isUpdate){
                new Alert(Alert.AlertType.INFORMATION, "Product Category updated successfully").show();
                 refreshPage();
                loadTableData();
             }else{
                 new Alert(Alert.AlertType.ERROR, "Product Category could not be updated").show();

             }
            btnSavePrdCategory.setDisable(false);
            btnLoadPrdCategory.setDisable(false);
            loadNextCategoryId();
     }

     @FXML
     void onClickTable(MouseEvent event) {
         ProductCategoryTM productCategoryTM = tblPrdCategory.getSelectionModel().getSelectedItem();
         if(productCategoryTM != null){
             txtCategoryId.setText(String.valueOf(productCategoryTM.getCategoryId()));
             txtCategoryName.setText(productCategoryTM.getCategoryName());

             btnDeletePrdCategory.setDisable(false);
             btnSavePrdCategory.setDisable(true);
             btnUpdatePrdCategory.setDisable(false);
             btnLoadPrdCategory.setDisable(true);
         }
     }



     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
            colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
            colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

         try {
             loadNextCategoryId();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 }
