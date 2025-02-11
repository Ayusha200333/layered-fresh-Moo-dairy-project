package com.example.finalproject.controller;

import com.example.finalproject.bo.BOFactory;
import com.example.finalproject.bo.custom.BatchBO;
import com.example.finalproject.bo.custom.DeliveryBO;
import com.example.finalproject.bo.custom.EmployeeBO;
import com.example.finalproject.dao.custom.DeliveryDAO;
import com.example.finalproject.dao.custom.impl.DeliveryDAOImpl;
import com.example.finalproject.dto.EmployeeDTO;
import com.example.finalproject.dto.tm.EmployeeTM;
import com.example.finalproject.dao.custom.impl.EmployeeDAOImpl;
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

public class EmployeeController implements Initializable {

    @FXML
    public AnchorPane ancEmployee;

    @FXML
    private Button btnDeleteEmp;

    @FXML
    private Button btnLoadEmp;

    @FXML
    private Button btnSaveEmp;

    @FXML
    private Button btnUpdateEmp;

    @FXML
    private TableColumn<EmployeeTM, String> colEmName;

    @FXML
    private TableColumn<EmployeeTM, String> colEmpId;

    @FXML
    private TableColumn<EmployeeTM, String> colPhone;

    @FXML
    private TableColumn<EmployeeTM, Double> colSalary;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpPhone;

    @FXML
    private TextField txtEmpSalary;
//
//     EmployeeDAOImpl employeeModel = new EmployeeDAOImpl();
//     DeliveryDAO deliveryModel = new DeliveryDAOImpl();

    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getInstance().getBO(BOFactory.BOType.DELIVERY);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);



    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String employeeId = txtEmpId.getText();

        if (employeeId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select an employee to delete.").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = employeeBO.delete(employeeId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully").show();
                refreshPage();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee could not be deleted").show();
            }
        }
    }

    private void loadTableData() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeBO.getAll();

        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployeeId(),
                    employeeDTO.getEmployeeName(),
                    employeeDTO.getEmployeeSalary(),
                    employeeDTO.getEmployeePhoneNo()
            );
            employeeTMS.add(employeeTM);
        }

        tblEmployee.setItems(employeeTMS);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String empId = txtEmpId.getText();
        String name = txtEmpName.getText();
        int salary = Integer.parseInt(txtEmpSalary.getText());
        String phoneNo = txtEmpPhone.getText();

        boolean isValidPhoneNo = isValidPhoneNo(phoneNo);

        if (isValidPhoneNo) {
            EmployeeDTO employeeDTO = new EmployeeDTO(empId, name, salary, phoneNo);

            boolean isSaved = employeeBO.save(employeeDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully.").show();
                refreshPage();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee could not be saved.").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid phone no.").show();
        }
    }


//        EmployeeDTO employeeDTO = new EmployeeDTO(empId, name, salary, phoneNo);
//
//        boolean isSaved = employeeModel.saveEmployee(employeeDTO);
//        if (isSaved) {
//            new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully.").show();
//            refreshPage();
//            loadTableData();
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Employee could not be saved.").show();
//        }
//    }

    private boolean isValidPhoneNo(String phoneNo) {
        String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }

    private void refreshPage() throws SQLException {
        loadNextEmployeeId();

        txtEmpName.clear();
        txtEmpSalary.clear();
        txtEmpPhone.clear();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String employeeId = txtEmpId.getText();
        String employeeName = txtEmpName.getText();
        int employeeSalary = Integer.parseInt(txtEmpSalary.getText());
        String employeePhoneNo = txtEmpPhone.getText();


        EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, employeeName, employeeSalary, employeePhoneNo);

        boolean isUpdate = employeeBO.update(employeeDTO);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully.").show();
            refreshPage();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Employee could not be updated.").show();
        }
        btnSaveEmp.setDisable(false);
        btnLoadEmp.setDisable(false);
    }



    @FXML
    void btnLoadOnAction(ActionEvent event) throws SQLException{
        loadTableData();
        loadNextEmployeeId();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("employeePhoneNo"));

        try {
            loadNextEmployeeId();
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading data.").show();
        }
    }

    private void loadNextEmployeeId() throws SQLException {

        String nextEmployeeId = employeeBO.getNextId();
        txtEmpId.setText(nextEmployeeId);
    }

    public void onClickTable(MouseEvent event) {
        try{
            EmployeeTM employeeTM = tblEmployee.getSelectionModel().getSelectedItem();
            System.out.println(employeeTM);
            if (employeeTM != null) {
                txtEmpId.setText(employeeTM.getEmployeeId());
                txtEmpName.setText(employeeTM.getEmployeeName());
                txtEmpSalary.setText(String.valueOf(employeeTM.getEmployeeSalary()));
                txtEmpPhone.setText(employeeTM.getEmployeePhoneNo());

                btnSaveEmp.setDisable(true);
                btnUpdateEmp.setDisable(false);
                btnDeleteEmp.setDisable(false);
                btnLoadEmp.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}






