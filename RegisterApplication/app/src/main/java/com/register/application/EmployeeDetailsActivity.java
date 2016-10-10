package com.register.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.register.application.events.DateTimeCallback;
import com.register.application.events.DetailsEmployeeCallBack;
import com.register.application.model.Employees;
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
 * Created by ederson on 21/04/16.
 */
public class EmployeeDetailsActivity extends AppCompatActivity {

    public static final String POSITION = "position";
    public static final String EMPLOYEES = "employees";

    private TextView tvIdEmployee;

    private EditText txNameEmployee;
    private EditText txRoleEmployee;
    private EditText txCpfEmployee;
    private EditText txRgEmployee;
    private RadioGroup rgGenderEmployee;
    private RadioButton rbGenderEmployeeM;
    private RadioButton rbGenderEmployeeF;
    private EditText txBirthDateEmployee;
    private EditText txPhoneEmployee;
    private EditText txEmailEmployee;
    private EditText txSalaryEmployee;
    private EditText txAdmissionDateEmployee;
    private EditText txDismissalDateEmployee;
    private EditText txStatusEmployee;

    private Button btEdit;
    private Button btUpdate;
    private Button btDelete;

    private ImageButton ibCalendarBirthDateDetails;
    private ImageButton ibCalendarAdmissionDateDetails;
    private ImageButton ibCalendarDismissalDateDetails;

    private DateTime fieldBirthDate;
    private DateTime fieldAdmissionDate;
    private DateTime fieldDismissalDate;

    private String birthDate;
    private String admissionDate;
    private String dismissalDate;

    private int idEmployee;
    private char genderEmployee;

    private Employees employees;
    private int position;

    DatePickerFragment mDatePickerFragment;
    private Fragment fragment;

    private DetailsEmployeeCallBack mDetailCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_employees_details);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt(POSITION);
        employees = (Employees) bundle.getSerializable(EMPLOYEES);

        fieldBirthDate = DateTime.now();
        fieldAdmissionDate = DateTime.now();
        fieldDismissalDate = DateTime.now();

        tvIdEmployee = (TextView) findViewById(R.id.tvIdEmployee);

        txNameEmployee = (EditText) findViewById(R.id.txNameEmployee);
        txRoleEmployee = (EditText) findViewById(R.id.txRoleEmployee);
        txCpfEmployee = (EditText) findViewById(R.id.txCpfEmployee);
        txRgEmployee = (EditText) findViewById(R.id.txRgEmployee);
        rgGenderEmployee = (RadioGroup) findViewById(R.id.rgGenderEmployee);
        rbGenderEmployeeM = (RadioButton) findViewById(R.id.rbGenderEmployeeM);
        rbGenderEmployeeF = (RadioButton) findViewById(R.id.rbGenderEmployeeF);
        txBirthDateEmployee = (EditText) findViewById(R.id.txBirthDateEmployee);
        txPhoneEmployee = (EditText) findViewById(R.id.txPhoneEmployee);
        txEmailEmployee = (EditText) findViewById(R.id.txEmailEmployee);
        txSalaryEmployee = (EditText) findViewById(R.id.txSalaryEmployee);
        txAdmissionDateEmployee = (EditText) findViewById(R.id.txAdmissionDateEmployee);
        txDismissalDateEmployee = (EditText) findViewById(R.id.txDismissalDateEmployee);
        txStatusEmployee = (EditText) findViewById(R.id.txStatusEmployee);

        btEdit = (Button) findViewById(R.id.btEdit);
        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btDelete);

        ibCalendarBirthDateDetails = (ImageButton) findViewById(R.id.ibCalendarBirthDateDetails);
        ibCalendarAdmissionDateDetails = (ImageButton) findViewById(R.id.ibCalendarAdmissionDateDetails);
        ibCalendarDismissalDateDetails = (ImageButton) findViewById(R.id.ibCalendarDismissalDateDetails);

        btEdit.setOnClickListener(onClickEditListener);
        btUpdate.setOnClickListener(onClickUpdateListener);
        btDelete.setOnClickListener(onClickDeleteListener);

        ibCalendarBirthDateDetails.setOnClickListener(onClickSelectBirthDateListener);
        ibCalendarAdmissionDateDetails.setOnClickListener(onClickSelectAdmissionDateListener);
        ibCalendarDismissalDateDetails.setOnClickListener(onClickSelectDismissalDateListener);

        changeEmployee(employees);
    }

    private View.OnClickListener onClickSelectBirthDateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            mDatePickerFragment = new DatePickerFragment();
            mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
                @Override
                public void onDate(long timeMillis) {

                    fieldBirthDate = new DateTime().withMillis(timeMillis);
                    birthDate = fieldBirthDate.toString("dd/MM/yyyy");
                    txBirthDateEmployee.setText(birthDate);
                }
            });

            mDatePickerFragment.show(fragment.getFragmentManager(), "DatePicker");

        }
    };

    private View.OnClickListener onClickSelectAdmissionDateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            mDatePickerFragment = new DatePickerFragment();
            mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
                @Override
                public void onDate(long timeMillis) {

                    fieldAdmissionDate = new DateTime().withMillis(timeMillis);
                    admissionDate = fieldAdmissionDate.toString("dd/MM/yyyy");
                    txAdmissionDateEmployee.setText(admissionDate);
                }
            });

            mDatePickerFragment.show(fragment.getFragmentManager(), "DatePicker");

        }
    };

    private View.OnClickListener onClickSelectDismissalDateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            mDatePickerFragment = new DatePickerFragment();
            mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
                @Override
                public void onDate(long timeMillis) {

                    fieldDismissalDate = new DateTime().withMillis(timeMillis);
                    dismissalDate = fieldDismissalDate.toString("dd/MM/yyyy");
                    txDismissalDateEmployee.setText(dismissalDate);
                }
            });

            mDatePickerFragment.show(fragment.getFragmentManager(), "DatePicker");

        }
    };

    private View.OnClickListener onClickEditListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            txNameEmployee.setEnabled(true);
            txRoleEmployee.setEnabled(true);
            txCpfEmployee.setEnabled(true);
            txRgEmployee.setEnabled(true);
            rbGenderEmployeeF.setEnabled(true);
            rbGenderEmployeeM.setEnabled(true);
            txPhoneEmployee.setEnabled(true);
            txEmailEmployee.setEnabled(true);
            txSalaryEmployee.setEnabled(true);
            txStatusEmployee.setEnabled(true);

            btDelete.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.INVISIBLE);
            btUpdate.setVisibility(View.VISIBLE);

            ibCalendarBirthDateDetails.setVisibility(View.VISIBLE);
            ibCalendarAdmissionDateDetails.setVisibility(View.VISIBLE);
            ibCalendarDismissalDateDetails.setVisibility(View.VISIBLE);

        }
    };

    private View.OnClickListener onClickUpdateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            int selected = rgGenderEmployee.getCheckedRadioButtonId();

            if(selected == R.id.rbGenderEmployeeM){
                genderEmployee = Utils.MALE;
            } else if(selected == R.id.rbGenderEmployeeF){
                genderEmployee = Utils.FEMALE;
            }

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
            fieldBirthDate = formatter.parseDateTime(txBirthDateEmployee.getText().toString());
            fieldAdmissionDate = formatter.parseDateTime(txAdmissionDateEmployee.getText().toString());
            fieldDismissalDate = formatter.parseDateTime(txDismissalDateEmployee.getText().toString());

            LocalDate birthDate = fieldBirthDate.toLocalDate();
            LocalDate admissionDate = fieldAdmissionDate.toLocalDate();
            LocalDate dismissalDate = fieldDismissalDate.toLocalDate();

            employees.setId(idEmployee);
            employees.setName(txNameEmployee.getText().toString());
            employees.setRole(txRoleEmployee.getText().toString());
            employees.setCpf(txCpfEmployee.getText().toString());
            employees.setRg(txRgEmployee.getText().toString());
            employees.setPhoneNumber(txPhoneEmployee.getText().toString());
            employees.setEmail(txEmailEmployee.getText().toString());
            employees.setGender(genderEmployee);
            employees.setBirthDate(birthDate);
            employees.setSalary(Double.parseDouble(txSalaryEmployee.getText().toString()));
            employees.setAdmissionDate(admissionDate);
            employees.setDismissalDate(dismissalDate);
            employees.setEmployeeStatus(txStatusEmployee.getText().toString());

            Call<Employees> call = AppInitializer.get(getApplicationContext()).getEmployeeService().updateInfo(employees.getId(), employees);
            call.enqueue(new Callback<Employees>() {
                @Override
                public void onResponse(Call<Employees> call, Response<Employees> response) {
                    if (response.code() == 200) {
                        employees = response.body();

                        if (employees != null) {
                            Toast.makeText(getApplicationContext(), "Employee Updated", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.EMPLOYEES, employees);

                            setResult(1, intent);

                            txNameEmployee.setEnabled(false);
                            txRoleEmployee.setEnabled(false);
                            txCpfEmployee.setEnabled(false);
                            txRgEmployee.setEnabled(false);
                            rbGenderEmployeeF.setEnabled(false);
                            rbGenderEmployeeM.setEnabled(false);
                            txBirthDateEmployee.setEnabled(false);
                            txPhoneEmployee.setEnabled(false);
                            txEmailEmployee.setEnabled(false);
                            txSalaryEmployee.setEnabled(false);
                            txAdmissionDateEmployee.setEnabled(false);
                            txDismissalDateEmployee.setEnabled(false);
                            txStatusEmployee.setEnabled(false);

                            btDelete.setVisibility(View.INVISIBLE);
                            btEdit.setVisibility(View.VISIBLE);
                            btUpdate.setVisibility(View.INVISIBLE);


                            ibCalendarBirthDateDetails.setVisibility(View.INVISIBLE);
                            ibCalendarAdmissionDateDetails.setVisibility(View.INVISIBLE);
                            ibCalendarDismissalDateDetails.setVisibility(View.INVISIBLE);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't update employee", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Employees> call, Throwable t) {

                }
            });

        }
    };

    private View.OnClickListener onClickDeleteListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Call<ResponseBody> call = AppInitializer.get(getApplicationContext()).getEmployeeService().deleteInfo(idEmployee);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        if (employees != null) {
                            Toast.makeText(getApplicationContext(), "Employee deleted", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.EMPLOYEES, employees);

                            setResult(2, intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't delete employee", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    };

    public void changeEmployee(Employees employees) {

        this.employees = employees;

        idEmployee = employees.getId();

        txNameEmployee.setText(employees.getName());
        txRoleEmployee.setText(employees.getRole());
        txCpfEmployee.setText(employees.getCpf());
        txRgEmployee.setText(employees.getRg());
        txBirthDateEmployee.setText(employees.getBirthDate().toString("dd/MM/yyyy"));
        txPhoneEmployee.setText(employees.getPhoneNumber());
        txEmailEmployee.setText(employees.getEmail());
        txSalaryEmployee.setText(String.valueOf(employees.getSalary()));
        txAdmissionDateEmployee.setText(employees.getAdmissionDate().toString("dd/MM/yyyy"));
        txDismissalDateEmployee.setText(employees.getDismissalDate().toString("dd/MM/yyyy"));
        txStatusEmployee.setText(employees.getEmployeeStatus());

        String gender = String.valueOf(employees.getGender());
        if(gender.equals("M")){
            rbGenderEmployeeM.setChecked(true);
        } else if(gender.equals("F")){
            rbGenderEmployeeF.setChecked(true);
        }

    }

}
