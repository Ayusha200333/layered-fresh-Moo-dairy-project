package com.example.finalproject.dao;

import com.example.finalproject.dao.custom.CustomerDAO;
import com.example.finalproject.dao.custom.impl.*;

import javax.swing.plaf.PanelUI;

import static com.example.finalproject.dao.DAOFactory.DAOType.CUSTOMER;
//import static com.sun.java.accessibility.util.EventID.ITEM;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}

    public static DAOFactory getInstance() {
         return daoFactory == null?daoFactory=new DAOFactory():daoFactory;


        }
        public enum DAOType{
        BATCH,CUSTOMER,DELIVERY,EMPLOYEE,INVENTORY,INVENTORYSUPPLIER,ORDERDETAIL,ORDERS,PAYMENT,PRODUCTCATEGORY,PRODUCT,
        RETURN,SUPPLIER,USER

    }
    public static CrudDAO getDAO(DAOType daoType) {
        switch (daoType){
            case BATCH:
                return new BatchDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case INVENTORYSUPPLIER:
                    return new InventorySupplierDAOImpl();
            case ORDERDETAIL:
                    return new OrderDetailDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PRODUCTCATEGORY:
                return new ProductCategoryDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case RETURN:
                return new ReturnDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }

    }
}
