package com.register.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.register.application.R;
import com.register.application.model.Clients;

import java.util.List;

/**
 * Created by ederson.js on 25/04/2016.
 */
public class ClientsAdapter extends BaseAdapter {

    List<Clients> listClients;
    LayoutInflater layoutInflater;

    public ClientsAdapter(Context context, List<Clients> listClients){
        this.listClients = listClients;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listClients != null ? listClients.size() : 0;
    }

    @Override
    public Object getItem(int position) {
       return  listClients != null ? listClients.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.clients_list_adapter, parent, false);

        TextView  txtName = (TextView) view.findViewById(R.id.tv_clients_name);

        Clients clients = listClients.get(position);

        if(clients != null){
            txtName.setText(clients.getName());
        }

        return view;
    }

    public void addItem(Clients client) {
        listClients.add(client);
        notifyDataSetChanged();
    }


    public void removeItem(Clients client){
        listClients.remove(client);
        notifyDataSetChanged();
    }

    public void updateItem(Clients client, int lastPosition){

        Clients mClient = listClients.get(lastPosition);

        if(mClient != null){
            listClients.remove(lastPosition);
            listClients.add(lastPosition, client);
            notifyDataSetChanged();
        }
    }
}
