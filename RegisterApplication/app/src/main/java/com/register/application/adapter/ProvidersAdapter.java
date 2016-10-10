package com.register.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.register.application.R;
import com.register.application.model.Employees;
import com.register.application.model.Providers;

import java.util.List;

/**
 * Created by ederson.js on 02/05/2016.
 */
public class ProvidersAdapter extends BaseAdapter {

    private List<Providers> listProviders;
    LayoutInflater layoutInflater;

    public ProvidersAdapter(Context context, List<Providers> listProviders){
        this.listProviders = listProviders;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listProviders != null ? listProviders.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listProviders != null ? listProviders.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.providers_list_adapter, parent, false);

        TextView txtName = (TextView) view.findViewById(R.id.tv_providers_name);

        Providers providers = listProviders.get(position);

        if (providers != null){
            txtName.setText(providers.getName());
        }

        return view;
    }


    public void removeItem(Providers providers){
        listProviders.remove(providers);
        notifyDataSetChanged();
    }

    public void updateItem(Providers providers, int lastPosition){
        Providers mProviders = listProviders.get(lastPosition);

        if(mProviders != null){
            listProviders.remove(lastPosition);
            listProviders.add(lastPosition, providers);
            notifyDataSetChanged();
        }

    }

    public void addItem(Providers providers) {
        listProviders.add(providers);
        notifyDataSetChanged();
    }
}
