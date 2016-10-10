package com.register.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.register.application.R;
import com.register.application.model.Products;
import com.register.application.model.Providers;

import java.util.List;

/**
 * Created by ederson.js on 02/05/2016.
 */
public class ProductsAdapter extends BaseAdapter {

    private List<Products> listProducts;
    LayoutInflater layoutInflater;

    public ProductsAdapter(Context context, List<Products> listProducts){
        this.listProducts = listProducts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listProducts != null ? listProducts.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listProducts != null ? listProducts.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.products_list_adapter, parent, false);

        TextView txtName = (TextView) view.findViewById(R.id.tv_products_name);

        Products products = listProducts.get(position);

        if (products != null){
            txtName.setText(products.getName());
        }

        return view;
    }


    public void removeItem(Products products){
        listProducts.remove(products);
        notifyDataSetChanged();
    }

    public void updateItem(Products products, int lastPosition){
        Products mProducts = listProducts.get(lastPosition);

        if(mProducts != null){
            listProducts.remove(lastPosition);
            listProducts.add(lastPosition, products);
            notifyDataSetChanged();
        }

    }

    public void addItem(Products products) {
        listProducts.add(products);
        notifyDataSetChanged();
    }
}
