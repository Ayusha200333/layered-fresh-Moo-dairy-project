package com.example.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class OrderDetailController {

    @FXML
    private AnchorPane ancOrderDetail;

    @FXML
    private Button btnDeleteOrdDetail;

    @FXML
    private Button btnLoadOrdDetail;

    @FXML
    private Button btnSaveOrdDetail;

    @FXML
    private Button btnUpdateOrdDetail;

    @FXML
    private TableColumn<?, ?> colOrdId;

    @FXML
    private TableColumn<?, ?> colPrdId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableView<?> tblOrdDetail;

    @FXML
    private TextField txtOrdId;

    @FXML
    private TextField txtPrdId;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @FXML
    void loadOnAction(ActionEvent event) {

    }

}
