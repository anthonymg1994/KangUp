package com.mx.bridgestudio.kangup.Views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;

import com.mx.bridgestudio.kangup.Controllers.BaseActivity;
import com.mx.bridgestudio.kangup.R;

/**
 * Created by Anthony on 07/11/2016.
 */

public class CatalogCar extends BaseActivity {
    //UI References
    private DatePicker dpResult;
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 999;
    //probar otro date picker
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_types);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
        toolbar.setTitle("Catalogo");
        setSupportActionBar(toolbar);
        showDialog(DATE_DIALOG_ID);

    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
          //  tvDisplayDate.setText(new StringBuilder().append(month + 1)
           //         .append("-").append(day).append("-").append(year)
            //        .append(" "));

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };
}
