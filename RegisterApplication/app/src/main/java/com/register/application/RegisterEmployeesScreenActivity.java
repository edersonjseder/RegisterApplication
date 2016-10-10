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

import com.register.application.events.DateTimeCallback;
import com.register.application.model.Employees;
import com.register.application.utils.Utils;
import com.register.application.view.DatePickerFragment;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterEmployeesScreenActivity extends AppCompatActivity {

    private EditText txNameEmployeeRegister;
    private EditText txRoleEmployeeRegister;
    private EditText txCpfEmployeeRegister;
    private EditText txRgEmployeeRegister;
    private RadioGroup rgGenderEmployeeRegister;
    private RadioButton rbGenderEmployeeRegisterM;
    private RadioButton rbGenderEmployeeRegisterF;
    private EditText txBirthDateEmployeeRegister;
    private EditText txPhoneEmployeeRegister;
    private EditText txEmailEmployeeRegister;
    private EditText txSalaryEmployeeRegister;
    private EditText txAdmissionDateEmployeeRegister;
    private EditText txDismissalDateEmployeeRegister;
    private EditText txStatusEmployeeRegister;
    private Button btRegisterEmployee;

    private Employees employees;
    private char genderEmployee;

    private DateTime fieldBirthDate;
    private DateTime fieldAdmissionDate;
    private DateTime fieldDismissalDate;

    private String birthDate;
    private String admissionDate;
    private String dismissalDate;

    DatePickerFragment mDatePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employees_screen);

        fieldBirthDate = DateTime.now();
        fieldAdmissionDate = DateTime.now();
        fieldDismissalDate = DateTime.now();

        employees = new Employees();

        txNameEmployeeRegister = (EditText) findViewById(R.id.txNameEmployeeRegister);
        txRoleEmployeeRegister = (EditText) findViewById(R.id.txRoleEmployeeRegister);
        txCpfEmployeeRegister = (EditText) findViewById(R.id.txCpfEmployeeRegister);
        txRgEmployeeRegister = (EditText) findViewById(R.id.txRgEmployeeRegister);
        rgGenderEmployeeRegister = (RadioGroup) findViewById(R.id.rgGenderEmployeeRegister);
        rbGenderEmployeeRegisterM = (RadioButton) findViewById(R.id.rbGenderEmployeeRegisterM);
        rbGenderEmployeeRegisterF = (RadioButton) findViewById(R.id.rbGenderEmployeeRegisterF);
        txBirthDateEmployeeRegister = (EditText) findViewById(R.id.txBirthDateEmployeeRegister);
        txPhoneEmployeeRegister = (EditText) findViewById(R.id.txPhoneEmployeeRegister);
        txEmailEmployeeRegister = (EditText) findViewById(R.id.txEmailEmployeeRegister);
        txSalaryEmployeeRegister = (EditText) findViewById(R.id.txSalaryEmployeeRegister);
        txAdmissionDateEmployeeRegister = (EditText) findViewById(R.id.txAdmissionDateEmployeeRegister);
        txDismissalDateEmployeeRegister = (EditText) findViewById(R.id.txDismissalDateEmployeeRegister);
        txStatusEmployeeRegister = (EditText) findViewById(R.id.txStatusEmployeeRegister);

        btRegisterEmployee = (Button) findViewById(R.id.btRegisterEmployee);

    }

    public void selectBirthDate(View view){

        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
            @Override
            public void onDate(long timeMillis) {

                fieldBirthDate = new DateTime().withMillis(timeMillis);
                birthDate = fieldBirthDate.toString("dd/MM/yyyy");
                txBirthDateEmployeeRegister.setText(birthDate);
            }
        });

        mDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");

    }

    public void selectAdmissionDate(View view){

        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
            @Override
            public void onDate(long timeMillis) {

                fieldAdmissionDate = new DateTime().withMillis(timeMillis);
                admissionDate = fieldAdmissionDate.toString("dd/MM/yyyy");
                txAdmissionDateEmployeeRegister.setText(admissionDate);
            }
        });

        mDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");

    }

    public void selectDismissalDate(View view){

        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
            @Override
            public void onDate(long timeMillis) {

                fieldDismissalDate = new DateTime().withMillis(timeMillis);
                dismissalDate = fieldDismissalDate.toString("dd/MM/yyyy");
                txDismissalDateEmployeeRegister.setText(dismissalDate);
            }
        });

        mDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");

    }

    public void registerEmployee(View view){

        int selected = rgGenderEmployeeRegister.getCheckedRadioButtonId();

        if(selected == R.id.rbGenderEmployeeRegisterM){
            genderEmployee = Utils.MALE;
        } else if(selected == R.id.rbGenderEmployeeRegisterF){
            genderEmployee = Utils.FEMALE;
        }

        LocalDate birthDate = fieldBirthDate.toLocalDate();
        LocalDate admissionDate = fieldAdmissionDate.toLocalDate();
        LocalDate dismissalDate = fieldDismissalDate.toLocalDate();

        employees.setName(txNameEmployeeRegister.getText().toString());
        employees.setRole(txRoleEmployeeRegister.getText().toString());
        employees.setCpf(txCpfEmployeeRegister.getText().toString());
        employees.setRg(txRgEmployeeRegister.getText().toString());
        employees.setPhoneNumber(txPhoneEmployeeRegister.getText().toString());
        employees.setEmail(txEmailEmployeeRegister.getText().toString());
        employees.setGender(genderEmployee);
        employees.setBirthDate(birthDate);
        employees.setSalary(Double.parseDouble(txSalaryEmployeeRegister.getText().toString()));
        employees.setAdmissionDate(admissionDate);
        employees.setDismissalDate(dismissalDate);
        employees.setEmployeeStatus(txStatusEmployeeRegister.getText().toString());

        Call<Employees> call = AppInitializer.get(getApplication()).getEmployeeService().createInfo(employees);
        call.enqueue(new Callback<Employees>() {
            @Override
            public void onResponse(Call<Employees> call, Response<Employees> response) {

                Intent intent = new Intent();

                if (response.code() == 201) {

                    employees = response.body();

                    if (employees != null) {

                        intent.putExtra(Utils.EMPLOYEES, employees);

                        setResult(1, intent);

                        RegisterEmployeesScreenActivity.this.finish();

                        Toast.makeText(getApplicationContext(), "Employee Created", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Couldn't create employee", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Employees> call, Throwable t) {

            }
        });

    }
}
