package com.register.application.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.register.application.ClientsDetailsActivity;
import com.register.application.R;
import com.register.application.RegisterBillsToPayScreenActivity;
import com.register.application.RegisterClientsScreenActivity;
import com.register.application.adapter.BillsToPayAdapter;
import com.register.application.adapter.ClientsAdapter;
import com.register.application.events.ItemClickClients;
import com.register.application.model.BillsToPay;
import com.register.application.model.Clients;
import com.register.application.service.BillsToPayService;
import com.register.application.service.ClientService;
import com.register.application.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillsToPayListScreenFragment extends Fragment {

    private static final String TITLE = "Customers";

    private ListView billsToPayListView;
    private BillsToPayAdapter listBillsToPayAdapter;
    AdapterView.OnItemClickListener itemClickBillsToPayListener;
    ItemClickClients mItemClickClient;
    FloatingActionButton fab;
    private TextView tvBillsToPayTotalEmpty;
    private List<BillsToPay> listBillsToPay;

    int lastPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bills_to_pay_list_screen, container, false);

        tvBillsToPayTotalEmpty = (TextView) v.findViewById(R.id.tv_bills_to_pay_name_empty);

        billsToPayListView = (ListView) v.findViewById(R.id.lv_bills_to_pay_list);

        fab = (FloatingActionButton) v.findViewById(R.id.bt_register_bills_to_pay);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityBillsToPayRegister = new Intent(getContext(), RegisterBillsToPayScreenActivity.class);
                startActivityForResult(intentActivityBillsToPayRegister, 1, null);
            }
        });

        return v;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        Log.d("EDERSON", "Visibility" + menuVisible);
    }

    public void getListBillsToPay() {

        if(listBillsToPay == null){
            Call<List<BillsToPay>> call = BillsToPayService.Factory.create().listAll();
            call.enqueue(new Callback<List<BillsToPay>>() {
                @Override
                public void onResponse(Call<List<BillsToPay>> call, Response<List<BillsToPay>> response) {
                    Log.d("EDERSON", "SIZE = " + response.body().size());

                    listBillsToPay = response.body();

                    if (listBillsToPay != null) {
                        showList(listBillsToPay);
                        tvBillsToPayTotalEmpty.setVisibility(View.INVISIBLE);

                    } else {
                        tvBillsToPayTotalEmpty.setVisibility(View.VISIBLE);
                        billsToPayListView.setEmptyView(tvBillsToPayTotalEmpty);
                    }
                }

                @Override
                public void onFailure(Call<List<BillsToPay>> call, Throwable t) {
                    Log.d("EDERSON", "onFailure ==== " + t.getMessage());
                }
            });
        }
    }

    public void showList(List<BillsToPay> body) {

        listBillsToPayAdapter = new BillsToPayAdapter(getContext(), body);

        billsToPayListView.setAdapter(listBillsToPayAdapter);

        itemClickBillsToPayListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BillsToPay billsToPay = (BillsToPay) ((BillsToPayAdapter) parent.getAdapter()).getItem(position);

                Intent intent = new Intent(getContext(), ClientsDetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt(ClientsDetailsActivity.POSITION, position);
                bundle.putSerializable(ClientsDetailsActivity.CLIENTS, billsToPay);

                intent.putExtras(bundle);

                startActivityForResult(intent, 1);
            }
        };

        billsToPayListView.setOnItemClickListener(itemClickBillsToPayListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(TITLE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 /* receiver from RegisterBillsToPayScreenActivity  when success*/) {
            BillsToPay billsToPay = (BillsToPay) data.getSerializableExtra(Utils.BILLSTOPAY);
            updateClient(billsToPay, resultCode);

        } else if (resultCode == 2) {
            BillsToPay billsToPay = (BillsToPay) data.getSerializableExtra(Utils.BILLSTOPAY);
            removeClient(billsToPay);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Utils.ID, lastPosition);
    }

    public void removeClient(BillsToPay billsToPay) {
        listBillsToPayAdapter.removeItem(billsToPay);
    }

    public void updateClient(BillsToPay billsToPay, int lastPosition) {
        listBillsToPayAdapter.updateItem(billsToPay, lastPosition);
    }

    public void addClient(BillsToPay billsToPay) {
        listBillsToPayAdapter.addItem(billsToPay);
    }

}
