package com.register.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.register.application.AppInitializer;
import com.register.application.R;
import com.register.application.events.DateTimeCallback;
import com.register.application.events.DetailsEmployeeCallBack;
import com.register.application.events.DetailsProviderCallBack;
import com.register.application.model.Clients;
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

/**
 * Created by ederson.js on 02/05/2016.
 */
public class ProvidersDetailsActivity extends AppCompatActivity {

    public static final String POSITION = "position";
    public static final String PROVIDERS = "providers";

    private TextView tvIdProvider;

    private EditText txNameProvider;
    private EditText txCpfCnpjProvider;
    private EditText txIeRgProvider;
    private EditText txPhoneProvider;
    private EditText txEmailProvider;
    private EditText txRegisterDateProvider;

    private Button btEdit;
    private Button btUpdate;
    private Button btDelete;

    private ImageButton ibCalendarRegisterDateDetails;

    private DateTime fieldRegisterDate;
    private String registerDate;
    private int idProvider;
    private Providers providers;

    private int position;
    private Fragment fragment;
    DatePickerFragment mDatePickerFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_providers_details);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt(POSITION);
        providers = (Providers) bundle.getSerializable(PROVIDERS);

        fieldRegisterDate = DateTime.now();

        tvIdProvider = (TextView) findViewById(R.id.tvIdProvider);

        txNameProvider = (EditText) findViewById(R.id.txNameProvider);
        txCpfCnpjProvider = (EditText) findViewById(R.id.txCpfCnpjProvider);
        txIeRgProvider = (EditText) findViewById(R.id.txIeRgProvider);
        txPhoneProvider = (EditText) findViewById(R.id.txPhoneProvider);
        txEmailProvider = (EditText) findViewById(R.id.txEmailProvider);
        txRegisterDateProvider = (EditText) findViewById(R.id.txRegisterDateProvider);

        btEdit = (Button) findViewById(R.id.btEdit);
        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btDelete);

        ibCalendarRegisterDateDetails = (ImageButton) findViewById(R.id.ibCalendarRegisterDateDetails);

        btEdit.setOnClickListener(onClickEditListener);
        btUpdate.setOnClickListener(onClickUpdateListener);
        btDelete.setOnClickListener(onClickDeleteListener);

        ibCalendarRegisterDateDetails.setOnClickListener(onClickSelectRegisterDateListener);

        changeProvider(providers);
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
                    txRegisterDateProvider.setText(registerDate);
                }
            });

            mDatePickerFragment.show(fragment.getFragmentManager(), "DatePicker");
        }
    };

    private View.OnClickListener onClickEditListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            txNameProvider.setEnabled(true);
            txCpfCnpjProvider.setEnabled(true);
            txIeRgProvider.setEnabled(true);
            txPhoneProvider.setEnabled(true);
            txEmailProvider.setEnabled(true);

            btDelete.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.INVISIBLE);
            btUpdate.setVisibility(View.VISIBLE);

            ibCalendarRegisterDateDetails.setVisibility(View.VISIBLE);

        }
    };

    private View.OnClickListener onClickUpdateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
            fieldRegisterDate = formatter.parseDateTime(txRegisterDateProvider.getText().toString());

            LocalDate registerDate = fieldRegisterDate.toLocalDate();

            providers.setName(txNameProvider.getText().toString());
            providers.setCpfOrCnpj(txCpfCnpjProvider.getText().toString());
            providers.setIeOrRg(txIeRgProvider.getText().toString());
            providers.setRegisterDate(registerDate);
            providers.setPhoneNumber(txPhoneProvider.getText().toString());
            providers.setEmail(txEmailProvider.getText().toString());

            Call<Providers> call = AppInitializer.get(getApplicationContext()).getProviderService().updateInfo(providers.getId(), providers);
            call.enqueue(new Callback<Providers>() {
                @Override
                public void onResponse(Call<Providers> call, Response<Providers> response) {
                    if (response.code() == 200) {
                        providers = response.body();

                        if (providers != null) {
                            Toast.makeText(getApplicationContext(), "Provider Updated", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.PROVIDERS, providers);

                            setResult(1, intent);

                            txNameProvider.setEnabled(false);
                            txCpfCnpjProvider.setEnabled(false);
                            txIeRgProvider.setEnabled(false);
                            txPhoneProvider.setEnabled(false);
                            txEmailProvider.setEnabled(false);

                            btDelete.setVisibility(View.INVISIBLE);
                            btEdit.setVisibility(View.VISIBLE);
                            btUpdate.setVisibility(View.INVISIBLE);

                            ibCalendarRegisterDateDetails.setVisibility(View.INVISIBLE);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't update provider", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Providers> call, Throwable t) {

                }
            });
        }
    };

    private View.OnClickListener onClickDeleteListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            Call<ResponseBody> call = AppInitializer.get(getApplicationContext()).getProviderService().deleteInfo(idProvider);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        if (providers != null) {
                            Toast.makeText(getApplicationContext(), "Provider deleted", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.PROVIDERS, providers);

                            setResult(2, intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't delete provider", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    };

    public void changeProvider(Providers providers){

        this.providers = providers;

        idProvider = providers.getId();

        txNameProvider.setText(providers.getName());
        txCpfCnpjProvider.setText(providers.getCpfOrCnpj());
        txIeRgProvider.setText(providers.getIeOrRg());
        txRegisterDateProvider.setText(providers.getRegisterDate().toString("dd/MM/yyyy"));
        txPhoneProvider.setText(providers.getPhoneNumber());
        txEmailProvider.setText(providers.getEmail());
    }
}
