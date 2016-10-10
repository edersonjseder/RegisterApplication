package com.register.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.register.application.model.Clients;
import com.register.application.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterClientsScreenActivity extends AppCompatActivity {


    private Clients clients;
    private EditText txtName;
    private EditText txtCpf;
    private EditText txtPhone;
    private EditText txtEmail;
    private RadioGroup rgGender;
    private RadioButton rbGenderM;
    private Button btRegister;
    private char gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_clients_screen);

        clients = new Clients();

        btRegister = (Button) findViewById(R.id.btRegister);
        txtName = (EditText) findViewById(R.id.txName);
        txtCpf = (EditText) findViewById(R.id.txCpf);
        txtPhone = (EditText) findViewById(R.id.txPhone);
        txtEmail = (EditText) findViewById(R.id.txEmail);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rbGenderM = (RadioButton) findViewById(R.id.rbGenderM);
    }

    public void register(View view){

        int selected = rgGender.getCheckedRadioButtonId();

        if(selected == R.id.rbGenderM){
            gender = Utils.MALE;
        } else if(selected == R.id.rbGenderF){
            gender = Utils.FEMALE;
        }

        clients.setName(txtName.getText().toString());
        clients.setCpf(txtCpf.getText().toString());
        clients.setPhoneNumber(txtPhone.getText().toString());
        clients.setEmail(txtEmail.getText().toString());
        clients.setGender(gender);

        Call<Clients> call = AppInitializer.get(getApplication()).getClientService().createInfo(clients);
        call.enqueue(new Callback<Clients>() {
            @Override
            public void onResponse(Call<Clients> call, Response<Clients> response) {

                if(response.code() == 201){
                    clients = response.body();

                    Intent intent = new Intent();

                    if(clients != null) {

                        txtName.setText("");
                        txtCpf.setText("");
                        txtPhone.setText("");
                        txtEmail.setText("");
                        rbGenderM.setChecked(true);

                        intent.putExtra(Utils.CLIENTS, clients);

                        // 1 - success
                        // 0 - error

                        setResult(1, intent);
                        RegisterClientsScreenActivity.this.finish();

                        Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Couldn't create user", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Clients> call, Throwable t) {

            }
        });

    }

}
