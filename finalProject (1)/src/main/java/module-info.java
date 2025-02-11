module com.example.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires  lombok;
    requires java.desktop;
    requires net.sf.jasperreports.core;


    opens com.example.finalproject.controller to javafx.fxml;
    opens com.example.finalproject.dto.tm to javafx.base;
    exports com.example.finalproject;
}