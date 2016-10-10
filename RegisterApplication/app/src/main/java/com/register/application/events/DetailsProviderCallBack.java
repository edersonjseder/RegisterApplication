package com.register.application.events;

import com.register.application.model.Employees;
import com.register.application.model.Providers;

/**
 * Created by ederson.js on 02/05/2016.
 */
public interface DetailsProviderCallBack {

    void onSave(Providers providers);
    void onUpdate(Providers providers);
    void onDelete(Providers providers);

}
