package com.register.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.register.application.R;
import com.register.application.model.BillsToPay;

import java.util.List;

/**
 * Created by ederson.js on 25/04/2016.
 */
public class BillsToPayAdapter extends BaseAdapter {

    List<BillsToPay> listBillsToPay;
    LayoutInflater layoutInflater;

    public BillsToPayAdapter(Context context, List<BillsToPay> listBillsToPay){
        this.listBillsToPay = listBillsToPay;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listBillsToPay != null ? listBillsToPay.size() : 0;
    }

    @Override
    public Object getItem(int position) {
       return  listBillsToPay != null ? listBillsToPay.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.bills_to_pay_list_adapter, parent, false);

        TextView  txtName = (TextView) view.findViewById(R.id.tv_bills_to_pay_name);

        BillsToPay billsToPay = listBillsToPay.get(position);

        if(billsToPay != null){
            txtName.setText(String.valueOf(billsToPay.getTotalToPay()));
        }

        return view;
    }

    public void addItem(BillsToPay billsToPay) {
        listBillsToPay.add(billsToPay);
        notifyDataSetChanged();
    }


    public void removeItem(BillsToPay billsToPay){
        listBillsToPay.remove(billsToPay);
        notifyDataSetChanged();
    }

    public void updateItem(BillsToPay billsToPay, int lastPosition){

        BillsToPay mBillsToPay = listBillsToPay.get(lastPosition);

        if(mBillsToPay != null){
            listBillsToPay.remove(lastPosition);
            listBillsToPay.add(lastPosition, billsToPay);
            notifyDataSetChanged();
        }
    }
}
