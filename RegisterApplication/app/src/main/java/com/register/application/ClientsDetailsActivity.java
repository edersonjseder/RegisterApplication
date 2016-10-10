package com.register.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.register.application.model.Clients;
import com.register.application.utils.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ederson.js on 20/04/2016.
 */
public class ClientsDetailsActivity extends AppCompatActivity {

    public static final String POSITION = "position";
    public static final String CLIENTS = "clients";

    private TextView tvIdClient;

    private EditText txNameClient;
    private EditText txCpfClient;
    private RadioGroup rgGenderClient;
    private RadioButton rbGenderClientM;
    private RadioButton rbGenderClientF;
    private EditText txPhoneClient;
    private EditText txEmailClient;

    private Button btEdit;
    private Button btUpdate;
    private Button btDelete;

    private int idClient;
    private char genderClient;

    private Clients clients;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_clients_details);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt(POSITION);
        clients = (Clients) bundle.getSerializable(CLIENTS);

        tvIdClient = (TextView) findViewById(R.id.tvIdClient);

        txNameClient = (EditText) findViewById(R.id.txNameClient);
        txCpfClient = (EditText) findViewById(R.id.txCpfClient);
        rgGenderClient = (RadioGroup) findViewById(R.id.rgGenderClient);
        rbGenderClientM = (RadioButton) findViewById(R.id.rbGenderClientM);
        rbGenderClientF = (RadioButton) findViewById(R.id.rbGenderClientF);
        txPhoneClient = (EditText) findViewById(R.id.txPhoneClient);
        txEmailClient = (EditText) findViewById(R.id.txEmailClient);

        btEdit = (Button) findViewById(R.id.btEdit);
        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btDelete);

        btEdit.setOnClickListener(onClickEditListener);
        btUpdate.setOnClickListener(onClickUpdateListener);
        btDelete.setOnClickListener(onClickDeleteListener);

        changeClient(clients);

    }

    private View.OnClickListener onClickEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            txNameClient.setEnabled(true);
            txCpfClient.setEnabled(true);
            rbGenderClientF.setEnabled(true);
            rbGenderClientM.setEnabled(true);
            txPhoneClient.setEnabled(true);
            txEmailClient.setEnabled(true);

            btDelete.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.INVISIBLE);
            btUpdate.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener onClickUpdateListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            int selected = rgGenderClient.getCheckedRadioButtonId();

            if(selected == R.id.rbGenderClientM){
                genderClient = Utils.MALE;
            } else if(selected == R.id.rbGenderClientF){
                genderClient = Utils.FEMALE;
            }

            clients.setId(idClient);
            clients.setName(txNameClient.getText().toString());
            clients.setCpf(txCpfClient.getText().toString());
            clients.setPhoneNumber(txPhoneClient.getText().toString());
            clients.setEmail(txEmailClient.getText().toString());
            clients.setGender(genderClient);

            Call<Clients> call = AppInitializer.get(getApplicationContext()).getClientService().updateInfo(clients.getId(), clients);
            call.enqueue(new Callback<Clients>() {
                @Override
                public void onResponse(Call<Clients> call, Response<Clients> response) {
                    if (response.code() == 200) {
                        clients = response.body();

                        if (clients != null) {

                            Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.CLIENTS, clients);

                            setResult(1, intent);

                            txNameClient.setEnabled(false);
                            txCpfClient.setEnabled(false);
                            rbGenderClientF.setEnabled(false);
                            rbGenderClientM.setEnabled(false);
                            txPhoneClient.setEnabled(false);
                            txEmailClient.setEnabled(false);

                            btDelete.setVisibility(View.INVISIBLE);
                            btEdit.setVisibility(View.VISIBLE);
                            btUpdate.setVisibility(View.INVISIBLE);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't update user", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Clients> call, Throwable t) {
                }
            });
        }
    };

    private View.OnClickListener onClickDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Call<ResponseBody> call = AppInitializer.get(getApplicationContext()).getClientService().deleteInfo(idClient);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        if (clients != null) {
                            Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.CLIENTS, clients);

                            setResult(2, intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't delete user", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    };

    public void changeClient(Clients clients) {

        this.clients = clients;

        idClient = clients.getId();

        tvIdClient.setText(String.valueOf(clients.getId()));
        txNameClient.setText(clients.getName());
        txCpfClient.setText(clients.getCpf());
        txPhoneClient.setText(clients.getPhoneNumber());
        txEmailClient.setText(clients.getEmail());

        String gender = String.valueOf(clients.getGender());
        if(gender.equals("M")){
            rbGenderClientM.setChecked(true);
        } else if(gender.equals("F")){
            rbGenderClientF.setChecked(true);
        }

    }
}
