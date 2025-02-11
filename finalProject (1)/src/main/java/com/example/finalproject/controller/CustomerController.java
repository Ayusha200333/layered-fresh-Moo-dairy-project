package com.example.finalproject.controller;

import com.example.finalproject.bo.BOFactory;
import com.example.finalproject.bo.custom.CustomerBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.CustomerDAO;
import com.example.finalproject.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import com.example.finalproject.dto.CustomerDTO;
import com.example.finalproject.dto.tm.CustomerTM;
import com.example.finalproject.dao.custom.impl.CustomerDAOImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerController implements Initializable {
    @FXML
    private AnchorPane ancCustomer;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnLoadCustomer;

    @FXML
    private Button btnSaveCustomer;

    @FXML
    private Button btnUpdateCustomer;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colPhone;

    @FXML
    private TextField txtCustId;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtCustAddress;

    @FXML
    private TextField txtCustEmail;

    @FXML
    private TextField txtCustName;

    @FXML
    private TextField txtCustPhone;

//    CustomerDAOImpl customerModel = new CustomerDAOImpl();
//    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String customerId = txtCustId.getText();
        String name = txtCustName.getText();
        String email = txtCustEmail.getText();
        String address = txtCustAddress.getText();
        String phoneNo = txtCustPhone.getText();

        boolean isValidPhoneno = isValidPhoneno(phoneNo);
        boolean isValidEmail = isValidEmail(email);

        if (isValidPhoneno && isValidEmail) {
            CustomerDTO customerDTO = new CustomerDTO(customerId, name,email,address, phoneNo);

            boolean isSaved = customerBO.save (customerDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved", ButtonType.OK).show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Saved", ButtonType.OK).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact Number Or Email", ButtonType.OK).show();
        }
        loadNextCustomerId();
    }


    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhoneno(String phone) {
        String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private void refreshPage() throws SQLException{
        loadNextCustomerId();

        txtCustId.clear();
        txtCustName.clear();
        txtCustEmail.clear();
        txtCustAddress.clear();
        txtCustPhone.clear();
}


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String customerId = txtCustId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){

            boolean isDeleted = customerBO.delete (customerId);
            if(isDeleted) {

                new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully").show();
                refreshPage();
                loadTableData();
            }else{
                new Alert(Alert.AlertType.ERROR, "Customer could not be deleted").show();
            }
        }
        btnSaveCustomer.setDisable(false);
        btnLoadCustomer.setDisable(false);
        loadNextCustomerId();
    }

    private void loadTableData() throws SQLException {
        ArrayList <CustomerDTO> customerDTOS = customerBO.getAll();

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getCustomerId(),
                    customerDTO.getName(),
                    customerDTO.getEmail(),
                    customerDTO.getAddress(),
                    customerDTO.getPhoneNo()
            );
            customerTMS.add(customerTM);
        }

        tblCustomer.setItems(customerTMS);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
    String customerId = txtCustId.getText();
    String customerName = txtCustName.getText();
    String customerEmail = txtCustEmail.getText();
    String customerAddress = txtCustAddress.getText();
    String customerPhone = txtCustPhone.getText();

    CustomerDTO customerDTO = new CustomerDTO(
            customerId,
            customerName,
            customerEmail,
            customerAddress,
            customerPhone
    );

    boolean isUpdate = customerBO.update(customerDTO);
    if(isUpdate){
        new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully").show();
        refreshPage();
        loadTableData();
    }else{
        new Alert(Alert.AlertType.ERROR, "Customer could not be updated").show();

    }
    btnLoadCustomer.setDisable(false);
    btnSaveCustomer.setDisable(false);
    loadNextCustomerId();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        try{
            loadNextCustomerId();
        }catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Customer could not be loaded").show();

        }
    }
    private void loadNextCustomerId() throws SQLException {
        String nextCustomerId = customerBO.getNextId();
        txtCustId.setText(nextCustomerId);
    }

    @FXML
    void loadOnAction(ActionEvent event) throws SQLException{
        loadTableData();
    }

    public void onClickTable(MouseEvent event) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();
        if(customerTM != null){
            txtCustId.setText(customerTM.getCustomerId());
            txtCustName.setText(customerTM.getName());
            txtCustEmail.setText(customerTM.getEmail());
            txtCustAddress.setText(customerTM.getAddress());
            txtCustPhone.setText(customerTM.getPhoneNo());

            btnLoadCustomer.setDisable(true);
            btnDeleteCustomer.setDisable(false);
            btnSaveCustomer.setDisable(true);
            btnUpdateCustomer.setDisable(false);
        }
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/customer_report.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }
}























