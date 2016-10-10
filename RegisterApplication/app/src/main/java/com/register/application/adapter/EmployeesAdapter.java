package com.register.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.register.application.R;
import com.register.application.model.Employees;

import java.util.List;

/**
 * Created by ederson.js on 26/04/2016.
 */
public class EmployeesAdapter extends BaseAdapter {

    private List<Employees> listEmployees;
    LayoutInflater layoutInflater;

    public EmployeesAdapter(Context context, List<Employees> listEmployees){
        this.listEmployees = listEmployees;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listEmployees != null ? listEmployees.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listEmployees != null ? listEmployees.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.employees_list_adapter, parent, false);

        TextView txtName = (TextView) view.findViewById(R.id.tv_employees_name);

        Employees employees = listEmployees.get(position);

        if (employees != null){
            txtName.setText(employees.getName());
        }

        return view;
    }

    public void removeItem(Employees employees){
        listEmployees.remove(employees);
        notifyDataSetChanged();
    }

    public void updateItem(Employees employees, int lastPosition){
        Employees mEmployees = listEmployees.get(lastPosition);

        if(mEmployees != null){
            listEmployees.remove(lastPosition);
            listEmployees.add(lastPosition, employees);
            notifyDataSetChanged();
        }

    }

    public void addItem(Employees employees) {
        listEmployees.add(employees);
        notifyDataSetChanged();
    }
}
