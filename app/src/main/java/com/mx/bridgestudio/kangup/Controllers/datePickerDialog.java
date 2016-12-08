package com.mx.bridgestudio.kangup.Controllers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CatalogCar;

import java.util.ArrayList;
import java.util.Calendar;

import static com.mx.bridgestudio.kangup.R.id.year;

/**
 * Created by USUARIO on 07/12/2016.
 */

public class datePickerDialog {
    private Context mContext = null;
    private DatePicker dpResult;
    final Calendar c = Calendar.getInstance();
    private int year =  c.get(Calendar.YEAR);
    private int month = c.get(Calendar.MONTH);
    private int day = c.get(Calendar.DAY_OF_MONTH);
    private ArrayList<Integer> arrayDate;

    boolean isOkayClicked = true;


    public datePickerDialog(){}


    public datePickerDialog(Context mContext){
        this.mContext = mContext;
    }

    public ArrayList<Integer> showDatePicker() {
        arrayDate =new ArrayList<Integer>(3);
        // TODO Auto-generated method stub


        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                if (isOkayClicked) {
                    year =  selectedYear;
                    month = selectedMonth;
                    day = selectedDay;

                }


                Toast.makeText(mContext,""+year+month+day,Toast.LENGTH_SHORT).show();
                isOkayClicked = false;
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                mContext, datePickerListener, year,
                month, day);


        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.cancel();
                            isOkayClicked = false;

                        }
                    }
                });
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            arrayDate.add(day);
                            isOkayClicked = true;

                        }
                    }
                });


        datePickerDialog.setCancelable(false);
        datePickerDialog.show();


        return (arrayDate);
    }
}
