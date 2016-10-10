package com.register.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.register.application.events.DateTimeCallback;
import com.register.application.model.BillsToPay;
import com.register.application.utils.Utils;
import com.register.application.view.DatePickerFragment;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterBillsToPayScreenActivity extends AppCompatActivity {

    private EditText txCreditorBillsToPayRegister;
    private EditText txNumberParcelsBillsToPayRegister;
    private EditText txNumberPaidParcelsBillsToPayRegister;
    private EditText txNumberParcelsToPayBillsToPayRegister;
    private EditText txStatusBillsToPayRegister;
    private EditText txDocumentBillsToPayRegister;
    private EditText txAmountToPayBillsToPayRegister;
    private EditText txParcelAmountBillsToPayRegister;
    private EditText txAmountPaidBillsToPayRegister;
    private EditText txTotalToPayBillsToPayRegister;
    private EditText txDueDateBillsToPayRegister;
    private EditText txPaymentDateBillsToPayRegister;
    private Button btRegisterBillsToPay;

    private BillsToPay billsToPay;

    private DateTime fieldDueDate;
    private DateTime fieldPaymentDate;

    private String dueDate;
    private String paymentDate;

    DatePickerFragment mDatePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bills_to_pay_screen);

        fieldDueDate = DateTime.now();
        fieldPaymentDate = DateTime.now();

        billsToPay = new BillsToPay();

        txCreditorBillsToPayRegister = (EditText) findViewById(R.id.txCreditorBillsToPayRegister);
        txNumberParcelsBillsToPayRegister = (EditText) findViewById(R.id.txNumberParcelsBillsToPayRegister);
        txNumberPaidParcelsBillsToPayRegister = (EditText) findViewById(R.id.txNumberPaidParcelsBillsToPayRegister);
        txNumberParcelsToPayBillsToPayRegister = (EditText) findViewById(R.id.txNumberParcelsToPayBillsToPayRegister);
        txStatusBillsToPayRegister = (EditText) findViewById(R.id.txStatusBillsToPayRegister);
        txDocumentBillsToPayRegister = (EditText) findViewById(R.id.txDocumentBillsToPayRegister);
        txAmountToPayBillsToPayRegister = (EditText) findViewById(R.id.txAmountToPayBillsToPayRegister);
        txParcelAmountBillsToPayRegister = (EditText) findViewById(R.id.txParcelAmountBillsToPayRegister);
        txAmountPaidBillsToPayRegister = (EditText) findViewById(R.id.txAmountPaidBillsToPayRegister);
        txTotalToPayBillsToPayRegister = (EditText) findViewById(R.id.txTotalToPayBillsToPayRegister);
        txDueDateBillsToPayRegister = (EditText) findViewById(R.id.txDueDateBillsToPayRegister);
        txPaymentDateBillsToPayRegister = (EditText) findViewById(R.id.txPaymentDateBillsToPayRegister);

        btRegisterBillsToPay = (Button) findViewById(R.id.btRegisterBillsToPay);

    }

    public void selectDueDate(View view){

        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
            @Override
            public void onDate(long timeMillis) {

                fieldDueDate = new DateTime().withMillis(timeMillis);
                dueDate = fieldDueDate.toString("dd/MM/yyyy");
                txDueDateBillsToPayRegister.setText(dueDate);
            }
        });

        mDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");

    }

    public void selectPaymentDate(View view){

        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
            @Override
            public void onDate(long timeMillis) {

                fieldPaymentDate = new DateTime().withMillis(timeMillis);
                paymentDate = fieldPaymentDate.toString("dd/MM/yyyy");
                txPaymentDateBillsToPayRegister.setText(paymentDate);
            }
        });

        mDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");

    }

    public void registerBillsToPay(View view){

        LocalDate dueDate = fieldDueDate.toLocalDate();
        LocalDate paymentDate = fieldPaymentDate.toLocalDate();

        billsToPay.setCreditor(txCreditorBillsToPayRegister.getText().toString());
        billsToPay.setNumberParcels(Integer.parseInt(txNumberParcelsBillsToPayRegister.getText().toString()));
        billsToPay.setNumberPaidParcels(Integer.parseInt(txNumberPaidParcelsBillsToPayRegister.getText().toString()));
        billsToPay.setNumberParcelsToPay(Integer.parseInt(txNumberParcelsToPayBillsToPayRegister.getText().toString()));
        billsToPay.setDocument(txDocumentBillsToPayRegister.getText().toString());
        billsToPay.setTotalToPay(Double.parseDouble(txTotalToPayBillsToPayRegister.getText().toString()));
        billsToPay.setAmountPaid(Double.parseDouble(txAmountPaidBillsToPayRegister.getText().toString()));
        billsToPay.setParcelAmount(Double.parseDouble(txParcelAmountBillsToPayRegister.getText().toString()));
        billsToPay.setBillsToPayStatus(txStatusBillsToPayRegister.getText().toString());
        billsToPay.setAmountToPay(Double.parseDouble(txAmountToPayBillsToPayRegister.getText().toString()));
        billsToPay.setDueDate(dueDate);
        billsToPay.setPaymentDate(paymentDate);

        Call<BillsToPay> call = AppInitializer.get(getApplication()).getBillsToPayService().createInfo(billsToPay);
        call.enqueue(new Callback<BillsToPay>() {
            @Override
            public void onResponse(Call<BillsToPay> call, Response<BillsToPay> response) {

                Intent intent = new Intent();

                if (response.code() == 201) {

                    billsToPay = response.body();

                    if (billsToPay != null) {

                        intent.putExtra(Utils.BILLSTOPAY, billsToPay);

                        setResult(1, intent);

                        RegisterBillsToPayScreenActivity.this.finish();

                        Toast.makeText(getApplicationContext(), "Bills to Pay Created", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Couldn't create Bills to Pay", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<BillsToPay> call, Throwable t) {

            }
        });

    }

}
