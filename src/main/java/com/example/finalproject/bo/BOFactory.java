package com.example.finalproject.bo;

import com.example.finalproject.bo.custom.impl.*;
import com.example.finalproject.dao.CrudDAO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.impl.CustomerDAOImpl;
import com.example.finalproject.dao.custom.impl.DeliveryDAOImpl;
import com.example.finalproject.dao.custom.impl.EmployeeDAOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getInstance() {
        return boFactory == null?boFactory=new BOFactory():boFactory;


    }
    public enum BOType {
        BATCH,CUSTOMER,DELIVERY,EMPLOYEE,INVENTORY,INVENTORYSUPPLIER,
        ORDERDETAIL,ORDERS,PAYMENT,PRODUCT,PRODUCTCATEGORY,RETURN,SUPPLIER,USER

    }
    public static SuperBO getBO(BOType boType) {
        switch (boType){
            case BATCH:
                return new BatchBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case DELIVERY:
                return new DeliveryBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case INVENTORYSUPPLIER:
                return new InventorySupplierBOImpl();
            case ORDERDETAIL:
                return new OrderDetailBOImpl();
            case ORDERS:
                return new OrdersBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case PRODUCTCATEGORY:
                return new ProductCategoryBOImpl();
            case RETURN:
                return new ReturnBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case USER:
                return new UserBOImpl();

            default:
                return null;
        }

    }
}
