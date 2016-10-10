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

import com.register.application.ProductsDetailsActivity;
import com.register.application.ProvidersDetailsActivity;
import com.register.application.R;
import com.register.application.RegisterProductsScreenActivity;
import com.register.application.RegisterProvidersScreenActivity;
import com.register.application.adapter.ProductsAdapter;
import com.register.application.adapter.ProvidersAdapter;
import com.register.application.events.ItemClickProducts;
import com.register.application.events.ItemClickProviders;
import com.register.application.model.Products;
import com.register.application.model.Providers;
import com.register.application.service.ProductService;
import com.register.application.service.ProviderService;
import com.register.application.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ederson on 24/04/16.
 */
public class ProductsListScreenFragment extends Fragment {

    private static final String TITLE = "products";

    private ListView productsListView;
    private ProductsAdapter listProductsAdapter;
    AdapterView.OnItemClickListener itemClickProductsListener;
    ItemClickProducts mItemClickProducts;
    FloatingActionButton fab;
    private TextView tvProductsNameEmpty;
    private List<Products> listProducts;

    int lastPosition = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_products_list_screen, container, false);

        tvProductsNameEmpty = (TextView) v.findViewById(R.id.tv_products_name_empty);

        productsListView = (ListView) v.findViewById(R.id.lv_products_list);

        fab = (FloatingActionButton) v.findViewById(R.id.bt_register_products);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityProductsRegister = new Intent(getContext(), RegisterProductsScreenActivity.class);
                startActivityForResult(intentActivityProductsRegister, 1, null);
            }
        });

        return v;
    }

    public void showData() {
        if(listProducts == null){
            Call<List<Products>> call = ProductService.Factory.create().listAll();
            call.enqueue(new Callback<List<Products>>() {
                @Override
                public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                    listProducts = response.body();

                    if (listProducts != null) {
                        showList(listProducts);
                        tvProductsNameEmpty.setVisibility(View.INVISIBLE);

                    } else {
                        tvProductsNameEmpty.setVisibility(View.VISIBLE);
                        productsListView.setEmptyView(tvProductsNameEmpty);
                    }
                }

                @Override
                public void onFailure(Call<List<Products>> call, Throwable t) {
                }
            });
        }
    }

    public void showList(List<Products> body){

        listProductsAdapter = new ProductsAdapter(getActivity(), listProducts);

        productsListView.setAdapter(listProductsAdapter);

        itemClickProductsListener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Products products = (Products) ((ProductsAdapter) parent.getAdapter()).getItem(position);

                Intent intent = new Intent(getContext(), ProductsDetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt(ProductsDetailsActivity.POSITION, position);
                bundle.putSerializable(ProductsDetailsActivity.PRODUCTS, products);

                intent.putExtras(bundle);

                startActivityForResult(intent, 1);

            }
        };

        productsListView.setOnItemClickListener(itemClickProductsListener);
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
            Products products = (Products) data.getSerializableExtra(Utils.PRODUCTS);
            updateProducts(products, resultCode);

        } else if(resultCode == 2){
            Products products = (Products) data.getSerializableExtra(Utils.PRODUCTS);
            removeProduct(products);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Utils.ID, lastPosition);
    }

    public void removeProduct(Products products){
        listProductsAdapter.removeItem(products);
    }

    public void updateProducts(Products products, int lastPosition){
        listProductsAdapter.updateItem(products, lastPosition);
    }

    public void addProducts(Products products){
        listProductsAdapter.addItem(products);
    }
}
