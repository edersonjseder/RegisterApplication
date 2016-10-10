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
import com.register.application.RegisterClientsScreenActivity;
import com.register.application.adapter.ClientsAdapter;
import com.register.application.events.ItemClickClients;
import com.register.application.model.Clients;
import com.register.application.service.ClientService;
import com.register.application.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsListScreenFragment extends Fragment {

    private static final String TITLE = "Customers";

    private ListView clientsListView;
    private ClientsAdapter listClientsAdapter;
    AdapterView.OnItemClickListener itemClickClientsListener;
    ItemClickClients mItemClickClient;
    FloatingActionButton fab;
    private TextView tvClientsNameEmpty;
    private List<Clients> listClients;

    int lastPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_clients_list_screen, container, false);

        tvClientsNameEmpty = (TextView) v.findViewById(R.id.tv_clients_name_empty);

        clientsListView = (ListView) v.findViewById(R.id.lv_clients_list);

        fab = (FloatingActionButton) v.findViewById(R.id.bt_register_clients);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityClientsRegister = new Intent(getContext(), RegisterClientsScreenActivity.class);
                startActivityForResult(intentActivityClientsRegister, 1, null);
            }
        });

        return v;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        Log.d("EDERSON", "Visibility" + menuVisible);
    }

    public void getListClients() {

        if(listClients == null){
            Call<List<Clients>> call = ClientService.Factory.create().listAll();
            call.enqueue(new Callback<List<Clients>>() {
                @Override
                public void onResponse(Call<List<Clients>> call, Response<List<Clients>> response) {
                    Log.d("EDERSON", "SIZE = " + response.body().size());

                    listClients = response.body();

                    if (listClients != null) {
                        showList(listClients);
                        tvClientsNameEmpty.setVisibility(View.INVISIBLE);

                    } else {
                        tvClientsNameEmpty.setVisibility(View.VISIBLE);
                        clientsListView.setEmptyView(tvClientsNameEmpty);
                    }
                }

                @Override
                public void onFailure(Call<List<Clients>> call, Throwable t) {
                    Log.d("EDERSON", "onFailure ==== " + t.getMessage());
                }
            });
        }
    }

    public void showList(List<Clients> body) {

        listClientsAdapter = new ClientsAdapter(getContext(), body);

        clientsListView.setAdapter(listClientsAdapter);

        itemClickClientsListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Clients clients = (Clients) ((ClientsAdapter) parent.getAdapter()).getItem(position);

                Intent intent = new Intent(getContext(), ClientsDetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt(ClientsDetailsActivity.POSITION, position);
                bundle.putSerializable(ClientsDetailsActivity.CLIENTS, clients);

                intent.putExtras(bundle);

                startActivityForResult(intent, 1);
            }
        };

        clientsListView.setOnItemClickListener(itemClickClientsListener);
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
            Clients client = (Clients) data.getSerializableExtra(Utils.CLIENTS);
            updateClient(client, resultCode);

        } else if (resultCode == 2) {
            Clients client = (Clients) data.getSerializableExtra(Utils.CLIENTS);
            removeClient(client);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Utils.ID, lastPosition);
    }

    public void removeClient(Clients clients) {
        listClientsAdapter.removeItem(clients);
    }

    public void updateClient(Clients clients, int lastPosition) {
        listClientsAdapter.updateItem(clients, lastPosition);
    }

    public void addClient(Clients client) {
        listClientsAdapter.addItem(client);
    }

}
