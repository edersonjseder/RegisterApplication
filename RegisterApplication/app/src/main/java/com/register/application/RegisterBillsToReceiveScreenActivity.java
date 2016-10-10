package com.register.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.register.application.events.DateTimeCallback;
import com.register.application.model.BillsToPay;
import com.register.application.model.BillsToReceive;
import com.register.application.utils.Utils;
import com.register.application.view.DatePickerFragment;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterBillsToReceiveScreenActivity extends AppCompatActivity {

    private EditText txCreditorBillsToReceiveRegister;
    private EditText txNumberParcelsBillsToReceiveRegister;
    private EditText txNumberReceivedParcelsBillsToReceiveRegister;
    private EditText txNumberParcelsToReceiveBillsToReceiveRegister;
    private EditText txStatusBillsToReceiveRegister;
    private EditText txDocumentBillsToReceiveRegister;
    private EditText txAmountToReceiveBillsToReceiveRegister;
    private EditText txParcelAmountBillsToReceiveRegister;
    private EditText txAmountReceivedBillsToReceiveRegister;
    private EditText txTotalToReceiveBillsToReceiveRegister;
    private EditText txDueDateBillsToReceiveRegister;
    private EditText txReceiptDateBillsToReceiveRegister;
    private Button btRegisterBillsToReceive;

    private BillsToReceive billsToReceive;

    private DateTime fieldDueDate;
    private DateTime fieldReceiptDate;

    private String dueDate;
    private String receiptDate;

    DatePickerFragment mDatePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bills_to_receive_screen);

        fieldDueDate = DateTime.now();
        fieldReceiptDate = DateTime.now();

        billsToReceive = new BillsToReceive();

        txCreditorBillsToReceiveRegister = (EditText) findViewById(R.id.txCreditorBillsToReceiveRegister);
        txNumberParcelsBillsToReceiveRegister = (EditText) findViewById(R.id.txNumberParcelsBillsToReceiveRegister);
        txNumberReceivedParcelsBillsToReceiveRegister = (EditText) findViewById(R.id.txNumberReceivedParcelsBillsToReceiveRegister);
        txNumberParcelsToReceiveBillsToReceiveRegister = (EditText) findViewById(R.id.txNumberParcelsToReceiveBillsToReceiveRegister);
        txStatusBillsToReceiveRegister = (EditText) findViewById(R.id.txStatusBillsToReceiveRegister);
        txDocumentBillsToReceiveRegister = (EditText) findViewById(R.id.txDocumentBillsToReceiveRegister);
        txAmountToReceiveBillsToReceiveRegister = (EditText) findViewById(R.id.txAmountToReceiveBillsToReceiveRegister);
        txParcelAmountBillsToReceiveRegister = (EditText) findViewById(R.id.txParcelAmountBillsToReceiveRegister);
        txAmountReceivedBillsToReceiveRegister = (EditText) findViewById(R.id.txAmountReceivedBillsToReceiveRegister);
        txTotalToReceiveBillsToReceiveRegister = (EditText) findViewById(R.id.txTotalToReceiveBillsToReceiveRegister);
        txDueDateBillsToReceiveRegister = (EditText) findViewById(R.id.txDueDateBillsToReceiveRegister);
        txReceiptDateBillsToReceiveRegister = (EditText) findViewById(R.id.txReceiptDateBillsToReceiveRegister);

        btRegisterBillsToReceive = (Button) findViewById(R.id.btRegisterBillsToReceive);

    }

    public void selectDueDate(View view){

        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
            @Override
            public void onDate(long timeMillis) {

                fieldDueDate = new DateTime().withMillis(timeMillis);
                dueDate = fieldDueDate.toString("dd/MM/yyyy");
                txDueDateBillsToReceiveRegister.setText(dueDate);
            }
        });

        mDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");

    }

    public void selectReceiptDate(View view){

        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
            @Override
            public void onDate(long timeMillis) {

                fieldReceiptDate = new DateTime().withMillis(timeMillis);
                receiptDate = fieldReceiptDate.toString("dd/MM/yyyy");
                txReceiptDateBillsToReceiveRegister.setText(receiptDate);
            }
        });

        mDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");

    }

    public void registerBillsToReceive(View view){

        LocalDate dueDate = fieldDueDate.toLocalDate();
        LocalDate receiptDate = fieldReceiptDate.toLocalDate();

        billsToReceive.setCreditor(txCreditorBillsToReceiveRegister.getText().toString());
        billsToReceive.setNumberParcels(Integer.parseInt(txNumberParcelsBillsToReceiveRegister.getText().toString()));
        billsToReceive.setNumberReceivedParcels(Integer.parseInt(txNumberReceivedParcelsBillsToReceiveRegister.getText().toString()));
        billsToReceive.setNumberParcelsToReceive(Integer.parseInt(txNumberParcelsToReceiveBillsToReceiveRegister.getText().toString()));
        billsToReceive.setDocument(txDocumentBillsToReceiveRegister.getText().toString());
        billsToReceive.setTotalToReceive(Double.parseDouble(txTotalToReceiveBillsToReceiveRegister.getText().toString()));
        billsToReceive.setAmountReceived(Double.parseDouble(txAmountReceivedBillsToReceiveRegister.getText().toString()));
        billsToReceive.setParcelAmount(Double.parseDouble(txParcelAmountBillsToReceiveRegister.getText().toString()));
        billsToReceive.setBillsToReceiveStatus(txStatusBillsToReceiveRegister.getText().toString());
        billsToReceive.setAmountToReceive(Double.parseDouble(txAmountToReceiveBillsToReceiveRegister.getText().toString()));
        billsToReceive.setDueDate(dueDate);
        billsToReceive.setReceiptDate(receiptDate);

        Call<BillsToReceive> call = AppInitializer.get(getApplication()).getBillsToReceiveService().createInfo(billsToReceive);
        call.enqueue(new Callback<BillsToReceive>() {
            @Override
            public void onResponse(Call<BillsToReceive> call, Response<BillsToReceive> response) {

                Intent intent = new Intent();

                if (response.code() == 201) {

                    billsToReceive = response.body();

                    if (billsToReceive != null) {

                        intent.putExtra(Utils.BILLSTOPAY, billsToReceive);

                        setResult(1, intent);

                        RegisterBillsToReceiveScreenActivity.this.finish();

                        Toast.makeText(getApplicationContext(), "Bills to Receive Created", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Couldn't create Bills to Receive", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<BillsToReceive> call, Throwable t) {

            }
        });

    }

}
