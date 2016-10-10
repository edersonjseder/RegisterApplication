package com.register.application;

import android.app.Application;
import android.content.Context;

import com.register.application.service.BillsToPayService;
import com.register.application.service.BillsToReceiveService;
import com.register.application.service.ClientService;
import com.register.application.service.EmployeeService;
import com.register.application.service.ProductService;
import com.register.application.service.ProviderService;



/**
 * Created by ederson.js on 29/03/2016.
 */
public class AppInitializer extends Application {

    private ClientService clientService;
    private EmployeeService employeeService;
    private ProviderService providerService;
    private ProductService productService;
    private BillsToPayService billsToPayService;
    private BillsToReceiveService billsToReceiveService;

    @Override
    public void onCreate() {
        super.onCreate();
       // JodaTimeAndroid.init(this);
    }

    public static AppInitializer get(Context context) {
        return (AppInitializer) context.getApplicationContext();
    }

    public ClientService getClientService() {
        if (clientService == null) {
            clientService = ClientService.Factory.create();
        }
        return clientService;
    }

    public EmployeeService getEmployeeService() {
        if (employeeService == null) {
            employeeService = EmployeeService.Factory.create();
        }
        return employeeService;
    }
    public ProviderService getProviderService() {
        if (providerService == null) {
            providerService = ProviderService.Factory.create();
        }
        return providerService;
    }
    public ProductService getProductService() {
        if (productService == null) {
            productService = ProductService.Factory.create();
        }
        return productService;
    }
    public BillsToPayService getBillsToPayService() {
        if (billsToPayService == null) {
            billsToPayService = BillsToPayService.Factory.create();
        }
        return billsToPayService;
    }
    public BillsToReceiveService getBillsToReceiveService() {
        if (billsToReceiveService == null) {
            billsToReceiveService = BillsToReceiveService.Factory.create();
        }
        return billsToReceiveService;
    }
}
