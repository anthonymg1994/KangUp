package com.mx.bridgestudio.kangup.Controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by USUARIO on 09/12/2016.
 */

public class Tools {
    public void hideKeyBoard(View view,Activity ac){
        InputMethodManager inputMethodManager = (InputMethodManager) ac.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


}
