package com.register.application.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.register.application.EmployeeDetailsActivity;
import com.register.application.R;
import com.register.application.RegisterEmployeesScreenActivity;
import com.register.application.adapter.EmployeesAdapter;
import com.register.application.events.ItemClickEmployees;
import com.register.application.model.Employees;
import com.register.application.service.EmployeeService;
import com.register.application.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ederson on 21/04/16.
 */
public class EmployeesListScreenFragment extends Fragment {

    private static final String TITLE = "Employees";

    private ListView employeesListView;
    private EmployeesAdapter listEmployeesAdapter;
    AdapterView.OnItemClickListener itemClickEmployeesListener;
    ItemClickEmployees mItemClickEmployees;
    FloatingActionButton fab;
    private TextView tvEmployeesNameEmpty;
    private List<Employees> listEmployees;

    int lastPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_employees_list_screen, container, false);

        tvEmployeesNameEmpty = (TextView) v.findViewById(R.id.tv_employees_name_empty);

        employeesListView = (ListView) v.findViewById(R.id.lv_employees_list);

        fab = (FloatingActionButton) v.findViewById(R.id.bt_register_employees);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityEmployeesRegister = new Intent(getContext(), RegisterEmployeesScreenActivity.class);
                startActivityForResult(intentActivityEmployeesRegister, 1, null);
            }
        });

        return v;
    }

    public void showData() {
        if(listEmployees == null){
            Call<List<Employees>> call = EmployeeService.Factory.create().listAll();
            call.enqueue(new Callback<List<Employees>>() {
                @Override
                public void onResponse(Call<List<Employees>> call, Response<List<Employees>> response) {
                    listEmployees = response.body();

                    if (listEmployees != null) {
                        showList(listEmployees);
                        tvEmployeesNameEmpty.setVisibility(View.INVISIBLE);

                    } else {
                        tvEmployeesNameEmpty.setVisibility(View.VISIBLE);
                        employeesListView.setEmptyView(tvEmployeesNameEmpty);
                    }
                }

                @Override
                public void onFailure(Call<List<Employees>> call, Throwable t) {

                }
            });
        }
    }

    public void showList(List<Employees> body){

        listEmployeesAdapter = new EmployeesAdapter(getContext(), body);

        employeesListView.setAdapter(listEmployeesAdapter);

        itemClickEmployeesListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employees employees = (Employees) ((EmployeesAdapter)parent.getAdapter()).getItem(position);

                Intent intent = new Intent(getContext(), EmployeeDetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt(EmployeeDetailsActivity.POSITION, position);
                bundle.putSerializable(EmployeeDetailsActivity.EMPLOYEES, employees);

                intent.putExtras(bundle);

                startActivityForResult(intent, 1);
            }
        };

        employeesListView.setOnItemClickListener(itemClickEmployeesListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(TITLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 /* receiver from RegisterClientsScreenActivity  when success*/) {
            Employees employees = (Employees) data.getSerializableExtra(Utils.EMPLOYEES);
            updateEmployee(employees, resultCode);

        } else if(resultCode == 2){
            Employees employees = (Employees) data.getSerializableExtra(Utils.EMPLOYEES);
            removeEmployee(employees);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Utils.ID, lastPosition);
    }

    public void removeEmployee(Employees employees){
        listEmployeesAdapter.removeItem(employees);
    }

    public void updateEmployee(Employees employees, int lastPosition){
        listEmployeesAdapter.updateItem(employees, lastPosition);
    }

    public void addEmployee(Employees employees) {
        listEmployeesAdapter.addItem(employees);
    }

}
