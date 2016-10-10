package com.register.application.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.register.application.ProvidersDetailsActivity;
import com.register.application.R;
import com.register.application.RegisterProvidersScreenActivity;
import com.register.application.adapter.ProvidersAdapter;
import com.register.application.events.ItemClickProviders;
import com.register.application.model.Providers;
import com.register.application.service.ProviderService;
import com.register.application.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ederson on 24/04/16.
 */
public class ProvidersListScreenFragment extends Fragment {

    private static final String TITLE = "Providers";

    private ListView providersListView;
    private ProvidersAdapter listProvidersAdapter;
    AdapterView.OnItemClickListener itemClickProvidersListener;
    ItemClickProviders mItemClickProviders;
    FloatingActionButton fab;
    private TextView tvProvidersNameEmpty;
    private List<Providers> listProviders;

    int lastPosition = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_providers_list_screen, container, false);

        tvProvidersNameEmpty = (TextView) v.findViewById(R.id.tv_providers_name_empty);

        providersListView = (ListView) v.findViewById(R.id.lv_providers_list);

        fab = (FloatingActionButton) v.findViewById(R.id.bt_register_providers);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityProvidersRegister = new Intent(getContext(), RegisterProvidersScreenActivity.class);
                startActivityForResult(intentActivityProvidersRegister, 1, null);
            }
        });

        return v;
    }

    public void showData() {
        if(listProviders == null){
            Call<List<Providers>> call = ProviderService.Factory.create().listAll();
            call.enqueue(new Callback<List<Providers>>() {
                @Override
                public void onResponse(Call<List<Providers>> call, Response<List<Providers>> response) {
                    listProviders = response.body();

                    if (listProviders != null) {
                        showList(listProviders);
                        tvProvidersNameEmpty.setVisibility(View.INVISIBLE);

                    } else {
                        tvProvidersNameEmpty.setVisibility(View.VISIBLE);
                        providersListView.setEmptyView(tvProvidersNameEmpty);
                    }
                }

                @Override
                public void onFailure(Call<List<Providers>> call, Throwable t) {
                }
            });
        }
    }

    public void showList(List<Providers> body){

        listProvidersAdapter = new ProvidersAdapter(getActivity(), listProviders);

        providersListView.setAdapter(listProvidersAdapter);

        itemClickProvidersListener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Providers providers = (Providers) ((ProvidersAdapter) parent.getAdapter()).getItem(position);

                Intent intent = new Intent(getContext(), ProvidersDetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt(ProvidersDetailsActivity.POSITION, position);
                bundle.putSerializable(ProvidersDetailsActivity.PROVIDERS, providers);

                intent.putExtras(bundle);

                startActivityForResult(intent, 1);

            }
        };

        providersListView.setOnItemClickListener(itemClickProvidersListener);
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
            Providers providers = (Providers) data.getSerializableExtra(Utils.PROVIDERS);
            updateProvider(providers, resultCode);

        } else if(resultCode == 2){
            Providers providers = (Providers) data.getSerializableExtra(Utils.PROVIDERS);
            removeProvider(providers);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Utils.ID, lastPosition);
    }

    public void removeProvider(Providers providers){
        listProvidersAdapter.removeItem(providers);
    }

    public void updateProvider(Providers providers, int lastPosition){
        listProvidersAdapter.updateItem(providers, lastPosition);
    }

    public void addProvider(Providers providers){
        listProvidersAdapter.addItem(providers);
    }
}
