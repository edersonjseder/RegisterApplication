package com.register.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.register.application.events.DateTimeCallback;
import com.register.application.model.Products;
import com.register.application.utils.Utils;
import com.register.application.view.DatePickerFragment;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProductsScreenActivity extends AppCompatActivity {

    private EditText txNameProductsRegister;
    private EditText txDescriptionProductsRegister;
    private EditText txCategoryProductsRegister;
    private EditText txQuantityProductsRegister;
    private EditText txRegisterDateProductsRegister;
    private EditText txValidateDateProductsRegister;
    private EditText txPriceProductsRegister;

    private Button btRegisterProduct;

    private Products products;

    private DateTime fieldRegisterDate;
    private DateTime fieldValidateDate;

    private String registerDate;
    private String validateDate;

    DatePickerFragment mDatePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products_screen);

        fieldRegisterDate = DateTime.now();
        fieldValidateDate = DateTime.now();

        products = new Products();

        txNameProductsRegister = (EditText) findViewById(R.id.txNameProductsRegister);
        txDescriptionProductsRegister = (EditText) findViewById(R.id.txDescriptionProductsRegister);
        txCategoryProductsRegister = (EditText) findViewById(R.id.txCategoryProductsRegister);
        txRegisterDateProductsRegister = (EditText) findViewById(R.id.txRegisterDateProductsRegister);
        txValidateDateProductsRegister = (EditText) findViewById(R.id.txValidateDateProductsRegister);
        txQuantityProductsRegister = (EditText) findViewById(R.id.txQuantityProductsRegister);
        txPriceProductsRegister = (EditText) findViewById(R.id.txPriceProductsRegister);

        btRegisterProduct = (Button) findViewById(R.id.btRegisterProduct);

    }

    public void selectRegisterDate(View view){

        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
            @Override
            public void onDate(long timeMillis) {

                fieldRegisterDate = new DateTime().withMillis(timeMillis);
                registerDate = fieldRegisterDate.toString("dd/MM/yyyy");
                txRegisterDateProductsRegister.setText(registerDate);
            }
        });

        mDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");

    }

    public void selectValidateDate(View view){

        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
            @Override
            public void onDate(long timeMillis) {

                fieldValidateDate = new DateTime().withMillis(timeMillis);
                validateDate = fieldValidateDate.toString("dd/MM/yyyy");
                txValidateDateProductsRegister.setText(validateDate);
            }
        });

        mDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");

    }

    public void registerProvider(View view){

        String dtRegisterDate = txRegisterDateProductsRegister.getText().toString();
        String dtValidateDate = txValidateDateProductsRegister.getText().toString();

        try {
            fieldRegisterDate = new DateTime(Utils.converterStringToDate(dtRegisterDate));
            fieldRegisterDate = new DateTime(Utils.converterStringToDate(dtValidateDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalDate registerDate = fieldRegisterDate.toLocalDate();
        LocalDate validateDate = fieldValidateDate.toLocalDate();

        products.setName(txNameProductsRegister.getText().toString());
        products.setDescription(txDescriptionProductsRegister.getText().toString());
        products.setCategory(txCategoryProductsRegister.getText().toString());
        products.setRegisterDate(registerDate);
        products.setValidateDate(validateDate);
        products.setQuantity(Long.parseLong(txQuantityProductsRegister.getText().toString()));
        products.setPrice(Double.parseDouble(txPriceProductsRegister.getText().toString()));

        Call<Products> call = AppInitializer.get(getApplication()).getProductService().createInfo(products);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {

                if (response.code() == 201) {
                    products = response.body();

                    if (products != null) {
                        Toast.makeText(getApplicationContext(), "Products Created", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Couldn't create Products", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {

            }
        });

    }

}
