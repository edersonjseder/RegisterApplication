package com.register.application.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.register.application.events.DateTimeCallback;

import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * Created by ederson on 21/04/16.
 */
public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    private DateTimeCallback mDateTimeCallback;
    private int year;
    private int month;
    private int day;


    public void setDateTimeCallback(DateTimeCallback mnDateTimeCallback){
        this.mDateTimeCallback = mnDateTimeCallback;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        DateTime date = new DateTime().withDate(year,month,day);
        mDateTimeCallback.onDate(date.getMillis());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }
}
