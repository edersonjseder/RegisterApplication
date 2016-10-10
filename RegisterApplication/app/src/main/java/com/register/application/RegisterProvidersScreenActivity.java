package com.register.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.register.application.model.Providers;
import com.register.application.utils.Utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProvidersScreenActivity extends AppCompatActivity {

    private EditText txNameProviderRegister;
    private EditText txCpfCnpjProviderRegister;
    private EditText txIeRgProviderRegister;
    private EditText txPhoneProviderRegister;
    private EditText txEmailProviderRegister;
    private EditText txRegisterDateProviderRegister;

    private Button btRegisterProvider;

    private Providers providers;

    private DateTime dtRegisterDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_providers_screen);

        providers = new Providers();

        txNameProviderRegister = (EditText) findViewById(R.id.txNameProviderRegister);
        txCpfCnpjProviderRegister = (EditText) findViewById(R.id.txCpfCnpjProviderRegister);
        txIeRgProviderRegister = (EditText) findViewById(R.id.txIeRgProviderRegister);
        txRegisterDateProviderRegister = (EditText) findViewById(R.id.txRegisterDateProviderRegister);
        txPhoneProviderRegister = (EditText) findViewById(R.id.txPhoneProviderRegister);
        txEmailProviderRegister = (EditText) findViewById(R.id.txEmailProviderRegister);

        btRegisterProvider = (Button) findViewById(R.id.btRegisterProvider);

    }

    public void registerProvider(View view){

        String fieldRegisterDate = txRegisterDateProviderRegister.getText().toString();

        try {
            dtRegisterDate = new DateTime(Utils.converterStringToDate(fieldRegisterDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalDate registerDate = dtRegisterDate.toLocalDate();

        providers.setName(txNameProviderRegister.getText().toString());
        providers.setCpfOrCnpj(txCpfCnpjProviderRegister.getText().toString());
        providers.setIeOrRg(txIeRgProviderRegister.getText().toString());
        providers.setRegisterDate(registerDate);
        providers.setEmail(txEmailProviderRegister.getText().toString());
        providers.setPhoneNumber(txPhoneProviderRegister.getText().toString());

        Call<Providers> call = AppInitializer.get(getApplication()).getProviderService().createInfo(providers);
        call.enqueue(new Callback<Providers>() {
            @Override
            public void onResponse(Call<Providers> call, Response<Providers> response) {

                if (response.code() == 201) {
                    providers = response.body();

                    if (providers != null) {
                        Toast.makeText(getApplicationContext(), "Provider Created", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Couldn't create provider", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Providers> call, Throwable t) {

            }
        });

    }

}
