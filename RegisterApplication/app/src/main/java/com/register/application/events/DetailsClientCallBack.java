package com.register.application.events;

import com.register.application.model.Clients;

/**
 * Created by ederson.js on 25/04/2016.
 */
public interface DetailsClientCallBack {

    void onUpdate(Clients clients);
    void onDelete(Clients clients);

}
