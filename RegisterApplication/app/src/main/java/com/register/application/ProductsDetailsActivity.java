package com.register.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.register.application.events.DateTimeCallback;
import com.register.application.model.Products;
import com.register.application.model.Providers;
import com.register.application.utils.Utils;
import com.register.application.view.DatePickerFragment;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsDetailsActivity extends AppCompatActivity {

    public static final String POSITION = "position";
    public static final String PRODUCTS = "products";

    private TextView tvIdProducts;

    private EditText txNameProducts;
    private EditText txDescriptionProducts;
    private EditText txCategoryProducts;
    private EditText txQuantityProducts;
    private EditText txRegisterDateProducts;
    private EditText txValidateDateProducts;
    private EditText txPriceProducts;

    private Button btEdit;
    private Button btUpdate;
    private Button btDelete;

    private ImageButton ibCalendarRegisterDateDetails;
    private ImageButton ibCalendarValidateDateDetails;

    private DateTime fieldRegisterDate;
    private DateTime fieldValidateDate;
    private String registerDate;
    private String validateDate;
    private int idProducts;
    private Products products;

    private int position;
    private Fragment fragment;
    DatePickerFragment mDatePickerFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_products_details);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt(POSITION);
        products = (Products) bundle.getSerializable(PRODUCTS);

        fieldRegisterDate = DateTime.now();
        fieldValidateDate = DateTime.now();

        tvIdProducts = (TextView) findViewById(R.id.tvIdProducts);

        txNameProducts = (EditText) findViewById(R.id.txNameProducts);
        txDescriptionProducts = (EditText) findViewById(R.id.txDescriptionProducts);
        txCategoryProducts = (EditText) findViewById(R.id.txCategoryProducts);
        txQuantityProducts = (EditText) findViewById(R.id.txQuantityProducts);
        txRegisterDateProducts = (EditText) findViewById(R.id.txRegisterDateProducts);
        txValidateDateProducts = (EditText) findViewById(R.id.txValidateDateProducts);
        txPriceProducts = (EditText) findViewById(R.id.txPriceProducts);

        btEdit = (Button) findViewById(R.id.btEdit);
        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btDelete);

        ibCalendarRegisterDateDetails = (ImageButton) findViewById(R.id.ibCalendarRegisterDateDetails);
        ibCalendarValidateDateDetails = (ImageButton) findViewById(R.id.ibCalendarValidateDateDetails);

        btEdit.setOnClickListener(onClickEditListener);
        btUpdate.setOnClickListener(onClickUpdateListener);
        btDelete.setOnClickListener(onClickDeleteListener);

        ibCalendarRegisterDateDetails.setOnClickListener(onClickSelectRegisterDateListener);
        ibCalendarValidateDateDetails.setOnClickListener(onClickSelectValidateDateListener);

        changeProduct(products);
    }

    private View.OnClickListener onClickSelectRegisterDateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            mDatePickerFragment = new DatePickerFragment();
            mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
                @Override
                public void onDate(long timeMillis) {

                    fieldRegisterDate = new DateTime().withMillis(timeMillis);
                    registerDate = fieldRegisterDate.toString("dd/MM/yyyy");
                    txRegisterDateProducts.setText(registerDate);
                }
            });

            mDatePickerFragment.show(fragment.getFragmentManager(), "DatePicker");
        }
    };

    private View.OnClickListener onClickSelectValidateDateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            mDatePickerFragment = new DatePickerFragment();
            mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
                @Override
                public void onDate(long timeMillis) {

                    fieldValidateDate = new DateTime().withMillis(timeMillis);
                    validateDate = fieldValidateDate.toString("dd/MM/yyyy");
                    txValidateDateProducts.setText(validateDate);
                }
            });

            mDatePickerFragment.show(fragment.getFragmentManager(), "DatePicker");
        }
    };

    private View.OnClickListener onClickEditListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            txNameProducts.setEnabled(true);
            txDescriptionProducts.setEnabled(true);
            txCategoryProducts.setEnabled(true);
            txQuantityProducts.setEnabled(true);
            txPriceProducts.setEnabled(true);

            btDelete.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.INVISIBLE);
            btUpdate.setVisibility(View.VISIBLE);

            ibCalendarRegisterDateDetails.setVisibility(View.VISIBLE);
            ibCalendarValidateDateDetails.setVisibility(View.VISIBLE);

        }
    };

    private View.OnClickListener onClickUpdateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
            fieldRegisterDate = formatter.parseDateTime(txRegisterDateProducts.getText().toString());

            LocalDate registerDate = fieldRegisterDate.toLocalDate();
            LocalDate validateDate = fieldValidateDate.toLocalDate();

            products.setName(txNameProducts.getText().toString());
            products.setDescription(txDescriptionProducts.getText().toString());
            products.setCategory(txCategoryProducts.getText().toString());
            products.setQuantity(Long.parseLong(txQuantityProducts.getText().toString()));
            products.setRegisterDate(registerDate);
            products.setValidateDate(validateDate);
            products.setPrice(Double.parseDouble(txPriceProducts.getText().toString()));

            Call<Products> call = AppInitializer.get(getApplicationContext()).getProductService().updateInfo(products.getId(), products);
            call.enqueue(new Callback<Products>() {
                @Override
                public void onResponse(Call<Products> call, Response<Products> response) {
                    if (response.code() == 200) {
                        products = response.body();

                        if (products != null) {
                            Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.PRODUCTS, products);

                            setResult(1, intent);

                            txNameProducts.setEnabled(false);
                            txDescriptionProducts.setEnabled(false);
                            txCategoryProducts.setEnabled(false);
                            txQuantityProducts.setEnabled(false);
                            txPriceProducts.setEnabled(false);

                            btDelete.setVisibility(View.INVISIBLE);
                            btEdit.setVisibility(View.VISIBLE);
                            btUpdate.setVisibility(View.INVISIBLE);

                            ibCalendarRegisterDateDetails.setVisibility(View.INVISIBLE);
                            ibCalendarValidateDateDetails.setVisibility(View.INVISIBLE);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't update product", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Products> call, Throwable t) {

                }
            });
        }
    };

    private View.OnClickListener onClickDeleteListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            Call<ResponseBody> call = AppInitializer.get(getApplicationContext()).getProductService().deleteInfo(idProducts);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        if (products != null) {
                            Toast.makeText(getApplicationContext(), "Product deleted", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.PRODUCTS, products);

                            setResult(2, intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't delete product", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    };

    public void changeProduct(Products products){

        this.products = products;

        idProducts = products.getId();

        txNameProducts.setText(products.getName());
        txDescriptionProducts.setText(products.getDescription());
        txCategoryProducts.setText(products.getCategory());
        txRegisterDateProducts.setText(products.getRegisterDate().toString("dd/MM/yyyy"));
        txValidateDateProducts.setText(products.getValidateDate().toString("dd/MM/yyyy"));
        txPriceProducts.setText(String.valueOf(products.getPrice()));
    }

}
