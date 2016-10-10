package com.register.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.register.application.adapter.ClientsAdapter;
import com.register.application.events.ItemClickClients;
import com.register.application.model.Clients;
import com.register.application.model.Employees;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ederson.js on 20/04/2016.
 */
public class ListClientsActivity extends AppCompatActivity {

    private ListView clientsListView;
    private ClientsAdapter listClientsAdapter;
    AdapterView.OnItemClickListener itemClickClientsListener = null;
    ItemClickClients mItemClickClient;
    int lastPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_clients_list);

        clientsListView = (ListView) findViewById(R.id.lv_clients_list);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        Call<List<Clients>> call =  AppInitializer.get(getApplicationContext()).getClientService().listAll();
        call.enqueue(new Callback<List<Clients>>() {
            @Override
            public void onResponse(Call<List<Clients>> call, Response<List<Clients>> response) {
                Log.d("EDERSON", "SIZE = " + response.body().size());
                showList(response.body());
            }

            @Override
            public void onFailure(Call<List<Clients>> call, Throwable t) {
                Log.d("EDERSON", "onFailure ==== " + t.getMessage());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void showList(List<Clients> body){

        listClientsAdapter = new ClientsAdapter(getApplicationContext(), body);

        clientsListView.setAdapter(listClientsAdapter);

        itemClickClientsListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Clients clients = (Clients) ((ClientsAdapter)parent.getAdapter()).getItem(position);
                mItemClickClient.onClick(position, clients);
            }
        };

        clientsListView.setOnItemClickListener(itemClickClientsListener);
    }

    public void onClick(int position, Clients clients) {
        lastPosition = position;

        Intent intentActivityClientsDetails = new Intent(getApplicationContext(), ClientsDetailsActivity.class);
        startActivityForResult(intentActivityClientsDetails, 1, null);

    }

    public void removeClient(Clients clients){
        listClientsAdapter.removeItem(clients);
    }

    public void updateClient(Clients clients, int lastPosition) {
        listClientsAdapter.updateItem(clients, lastPosition);
    }

    public void addClient(Clients client) {
        listClientsAdapter.addItem(client);
    }
}
