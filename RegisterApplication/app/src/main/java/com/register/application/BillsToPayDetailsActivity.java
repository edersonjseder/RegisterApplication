package com.register.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.register.application.events.DateTimeCallback;
import com.register.application.model.BillsToPay;
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

public class BillsToPayDetailsActivity extends AppCompatActivity {

    public static final String POSITION = "position";
    public static final String BILLSTOPAY = "billsToPay";

    private EditText txCreditorBillsToPay;
    private EditText txNumberParcelsBillsToPay;
    private EditText txNumberPaidParcelsBillsToPay;
    private EditText txNumberParcelsToPayBillsToPay;
    private EditText txStatusBillsToPay;
    private EditText txDocumentBillsToPay;
    private EditText txAmountToPayBillsToPay;
    private EditText txParcelAmountBillsToPay;
    private EditText txAmountPaidBillsToPay;
    private EditText txTotalToPayBillsToPay;
    private EditText txDueDateBillsToPay;
    private EditText txPaymentDateBillsToPay;

    private Button btEdit;
    private Button btUpdate;
    private Button btDelete;

    private ImageButton ibCalendarDueDate;
    private ImageButton ibCalendarPaymentDate;

    private DateTime fieldDueDate;
    private DateTime fieldPaymentDate;

    private String dueDate;
    private String paymentDate;

    private int idBillsToPay;

    private BillsToPay billsToPay;
    private int position;

    DatePickerFragment mDatePickerFragment;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bills_to_pay_details);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt(POSITION);
        billsToPay = (BillsToPay) bundle.getSerializable(BILLSTOPAY);

        fieldDueDate = DateTime.now();
        fieldPaymentDate = DateTime.now();

        billsToPay = new BillsToPay();

        txCreditorBillsToPay = (EditText) findViewById(R.id.txCreditorBillsToPay);
        txNumberParcelsBillsToPay = (EditText) findViewById(R.id.txNumberParcelsBillsToPay);
        txNumberPaidParcelsBillsToPay = (EditText) findViewById(R.id.txNumberPaidParcelsBillsToPay);
        txNumberParcelsToPayBillsToPay = (EditText) findViewById(R.id.txNumberParcelsToPayBillsToPay);
        txStatusBillsToPay = (EditText) findViewById(R.id.txStatusBillsToPay);
        txDocumentBillsToPay = (EditText) findViewById(R.id.txDocumentBillsToPay);
        txAmountToPayBillsToPay = (EditText) findViewById(R.id.txAmountToPayBillsToPay);
        txParcelAmountBillsToPay = (EditText) findViewById(R.id.txParcelAmountBillsToPay);
        txAmountPaidBillsToPay = (EditText) findViewById(R.id.txAmountPaidBillsToPay);
        txTotalToPayBillsToPay = (EditText) findViewById(R.id.txTotalToPayBillsToPay);
        txDueDateBillsToPay = (EditText) findViewById(R.id.txDueDateBillsToPay);
        txPaymentDateBillsToPay = (EditText) findViewById(R.id.txPaymentDateBillsToPay);

        btEdit = (Button) findViewById(R.id.btEdit);
        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btDelete);

        ibCalendarDueDate = (ImageButton) findViewById(R.id.ibCalendarDueDate);
        ibCalendarPaymentDate = (ImageButton) findViewById(R.id.ibCalendarPaymentDate);

        btEdit.setOnClickListener(onClickEditListener);
        btUpdate.setOnClickListener(onClickUpdateListener);
        btDelete.setOnClickListener(onClickDeleteListener);

        ibCalendarDueDate.setOnClickListener(onClickSelectDueDateListener);
        ibCalendarPaymentDate.setOnClickListener(onClickSelectPaymentDateListener);

        changeBillsToPay(billsToPay);

    }

    private View.OnClickListener onClickSelectDueDateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            mDatePickerFragment = new DatePickerFragment();
            mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
                @Override
                public void onDate(long timeMillis) {

                    fieldDueDate = new DateTime().withMillis(timeMillis);
                    dueDate = fieldDueDate.toString("dd/MM/yyyy");
                    txDueDateBillsToPay.setText(dueDate);
                }
            });

            mDatePickerFragment.show(fragment.getFragmentManager(), "DatePicker");

        }
    };

    private View.OnClickListener onClickSelectPaymentDateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            mDatePickerFragment = new DatePickerFragment();
            mDatePickerFragment.setDateTimeCallback(new DateTimeCallback() {
                @Override
                public void onDate(long timeMillis) {

                    fieldPaymentDate = new DateTime().withMillis(timeMillis);
                    paymentDate = fieldPaymentDate.toString("dd/MM/yyyy");
                    txPaymentDateBillsToPay.setText(paymentDate);
                }
            });

            mDatePickerFragment.show(fragment.getFragmentManager(), "DatePicker");

        }
    };

    private View.OnClickListener onClickEditListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            txCreditorBillsToPay.setEnabled(true);
            txNumberParcelsBillsToPay.setEnabled(true);
            txNumberPaidParcelsBillsToPay.setEnabled(true);
            txNumberParcelsToPayBillsToPay.setEnabled(true);
            txStatusBillsToPay.setEnabled(true);
            txDocumentBillsToPay.setEnabled(true);
            txAmountToPayBillsToPay.setEnabled(true);
            txParcelAmountBillsToPay.setEnabled(true);
            txAmountPaidBillsToPay.setEnabled(true);
            txTotalToPayBillsToPay.setEnabled(true);

            btDelete.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.INVISIBLE);
            btUpdate.setVisibility(View.VISIBLE);

            ibCalendarDueDate.setVisibility(View.VISIBLE);
            ibCalendarPaymentDate.setVisibility(View.VISIBLE);

        }
    };

    private View.OnClickListener onClickUpdateListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
            fieldDueDate = formatter.parseDateTime(txDueDateBillsToPay.getText().toString());
            fieldPaymentDate = formatter.parseDateTime(txPaymentDateBillsToPay.getText().toString());

            LocalDate dueDate = fieldDueDate.toLocalDate();
            LocalDate paymentDate = fieldPaymentDate.toLocalDate();

            billsToPay.setId(idBillsToPay);
            billsToPay.setCreditor(txCreditorBillsToPay.getText().toString());
            billsToPay.setNumberParcels(Integer.parseInt(txNumberParcelsBillsToPay.getText().toString()));
            billsToPay.setNumberPaidParcels(Integer.parseInt(txNumberPaidParcelsBillsToPay.getText().toString()));
            billsToPay.setNumberParcelsToPay(Integer.parseInt(txNumberParcelsToPayBillsToPay.getText().toString()));
            billsToPay.setBillsToPayStatus(txStatusBillsToPay.getText().toString());
            billsToPay.setDocument(txDocumentBillsToPay.getText().toString());
            billsToPay.setAmountToPay(Double.parseDouble(txAmountToPayBillsToPay.getText().toString()));
            billsToPay.setAmountPaid(Double.parseDouble(txAmountPaidBillsToPay.getText().toString()));
            billsToPay.setParcelAmount(Double.parseDouble(txParcelAmountBillsToPay.getText().toString()));
            billsToPay.setTotalToPay(Double.parseDouble(txTotalToPayBillsToPay.getText().toString()));
            billsToPay.setDueDate(dueDate);
            billsToPay.setPaymentDate(paymentDate);

            Call<BillsToPay> call = AppInitializer.get(getApplicationContext()).getBillsToPayService().updateInfo(billsToPay.getId(), billsToPay);
            call.enqueue(new Callback<BillsToPay>() {
                @Override
                public void onResponse(Call<BillsToPay> call, Response<BillsToPay> response) {
                    if (response.code() == 200) {
                        billsToPay = response.body();

                        if (billsToPay != null) {
                            Toast.makeText(getApplicationContext(), "Bills to Pay Updated", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.EMPLOYEES, billsToPay);

                            setResult(1, intent);

                            txCreditorBillsToPay.setEnabled(true);
                            txNumberParcelsBillsToPay.setEnabled(true);
                            txNumberPaidParcelsBillsToPay.setEnabled(true);
                            txNumberParcelsToPayBillsToPay.setEnabled(true);
                            txStatusBillsToPay.setEnabled(true);
                            txDocumentBillsToPay.setEnabled(true);
                            txAmountToPayBillsToPay.setEnabled(true);
                            txParcelAmountBillsToPay.setEnabled(true);
                            txAmountPaidBillsToPay.setEnabled(true);
                            txTotalToPayBillsToPay.setEnabled(true);

                            btDelete.setVisibility(View.INVISIBLE);
                            btEdit.setVisibility(View.VISIBLE);
                            btUpdate.setVisibility(View.INVISIBLE);


                            ibCalendarDueDate.setVisibility(View.INVISIBLE);
                            ibCalendarPaymentDate.setVisibility(View.INVISIBLE);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't update Bills to Pay", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BillsToPay> call, Throwable t) {

                }
            });

        }
    };

    private View.OnClickListener onClickDeleteListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Call<ResponseBody> call = AppInitializer.get(getApplicationContext()).getBillsToPayService().deleteInfo(idBillsToPay);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        if (billsToPay != null) {
                            Toast.makeText(getApplicationContext(), "Bills to Pay deleted", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.putExtra(Utils.BILLSTOPAY, billsToPay);

                            setResult(2, intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't delete Bills to Pay", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    };

    public void changeBillsToPay(BillsToPay billsToPay) {

        this.billsToPay = billsToPay;

        idBillsToPay = billsToPay.getId();

        txCreditorBillsToPay.setText(billsToPay.getCreditor());
        txNumberParcelsBillsToPay.setText(billsToPay.getNumberParcels());
        txNumberPaidParcelsBillsToPay.setText(billsToPay.getNumberPaidParcels());
        txNumberParcelsToPayBillsToPay.setText(billsToPay.getNumberParcelsToPay());
        txStatusBillsToPay.setText(billsToPay.getBillsToPayStatus());
        txDocumentBillsToPay.setText(billsToPay.getDocument());
        txAmountToPayBillsToPay.setText(String.valueOf(billsToPay.getAmountToPay()));
        txDueDateBillsToPay.setText(billsToPay.getDueDate().toString("dd/MM/yyyy"));
        txPaymentDateBillsToPay.setText(billsToPay.getPaymentDate().toString("dd/MM/yyyy"));
        txParcelAmountBillsToPay.setText(String.valueOf(billsToPay.getParcelAmount()));
        txAmountPaidBillsToPay.setText(String.valueOf(billsToPay.getAmountPaid()));
        txTotalToPayBillsToPay.setText(String.valueOf(billsToPay.getTotalToPay()));

    }

}
