package com.register.application.events;

import com.register.application.model.Employees;

/**
 * Created by ederson.js on 26/04/2016.
 */
public interface DetailsEmployeeCallBack {

    void onSave(Employees employees);
    void onUpdate(Employees employees);
    void onDelete(Employees employees);

}
