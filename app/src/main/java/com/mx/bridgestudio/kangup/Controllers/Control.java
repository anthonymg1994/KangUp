package com.mx.bridgestudio.kangup.Controllers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Models.Package;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CarsXtype;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.RegisterActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;


/**
 * Created by USUARIO on 20/12/2016.
 */

public class Control  {
    private int mYear, mMonth, mDay,mHour, mMinute,pm;
    public static final String UPLOAD_URL = "http://simplifiedcoding.16mb.com/PhotoUpload/upload.php";
    public static final String UPLOAD_KEY = "image";
    private static int PICK_IMAGE_REQUEST = 1;
    private static Bitmap bitmap;
    private static Uri filePath;
    private Calendar c;
    private SessionManager session;

    private void showFileChooser(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    public static Bitmap onActivityResult(int requestCode, int resultCode, Intent data, Activity activity) {
        Control.onActivityResult(requestCode, resultCode, data, activity);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == activity.RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), filePath);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(final Context mContext) {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(mContext, "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                String result = rh.sendPostRequest(UPLOAD_URL, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    public int LogicaReservaciones(Date fechaInicio, String horaInicio, String horaTermino) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date horaInicioReal = null;
        Date horaInicioMargen = null;
        Date horaFinalReal = null;
        Date horaFinalMargen = null;

        try {
            /*Obtengo tiempo de inicio de la reservacion y le reduzco dos hora = hora de margen para poder
            estar disponible para otra reservacion
            */
            horaInicioReal = simpleDateFormat.parse(horaInicio);
            String date1 = String.valueOf(horaInicioReal.getHours() - 2);
            horaInicioMargen = simpleDateFormat.parse(date1);


            /*Obtengo tiempo de inicio de la reservacion y le aumento dos hora = hora de margen para poder
            estar disponible para otra reservacion
            */
            horaFinalReal = simpleDateFormat.parse(horaTermino);
            String date2 = String.valueOf(horaFinalReal.getHours() + 2);
            horaFinalMargen = simpleDateFormat.parse(date2);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Obtengo diferencia de tiempos
        long difference = horaInicioMargen.getTime() - horaFinalMargen.getTime();


        //Obtengo dias, horas y minutos de la reservacion
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        Log.i("======= Hours", " :: " + hours);


        //Regreso minutos para poder calcular total a pagar
        return min;
    }

    public double calculatePrice(int minutes, Vehicle vehicle, Package[] paquetes) {


        //calculo precio por minuto


        //Multiplicacion de precio x minuto por minutos de rservacion
        double totalXtiempo = getTotalPrecio(minutes, vehicle);
        double gastosExtras = 0.0;

        //Obtener todos los precio de los paquetes agregados si es el caso
        if (paquetes.length > 0) {
            for (int j = 0; j < paquetes.length; j++) {
                gastosExtras += Double.valueOf(paquetes[j].getPrecio());
            }
        }

        //Se obtiene la suma de tiempo con los gastos por paquetes agregados
        double TotalPagar = totalXtiempo + gastosExtras;

        //Redondea >.5 aumenta, <.5 reduce
        double roundTotal = Math.round(TotalPagar);


        //Regresa total a pagar redondeado
        return roundTotal;
    }


    public double getPricesXAuto(Vehicle vehicle) {
        // agregar consulta de regresar precio de hora por automovil
        return 1.0;
    }


    public void agregarTiempoReservacionCurso(int minutos, Vehicle vehicle) {

        // chwecar como se vovlera a cargara el aumento de tiempo y como hacer el cobro de nuevo para la misma transaccion
        double precioTiempo = getTotalPrecio(minutos, vehicle);

    }

    public double getTotalPrecio(int minutos, Vehicle vehicle) {
        double precioHoraAuto = getPricesXAuto(vehicle);
        double precioXminuto = (precioHoraAuto / 60);
        double price = precioXminuto * minutos;
        return price;
    }

    public double cancelarReservacion(int minutos, int id_reservacion) {
        double cargosCancelacion = 0.0;
        /*agregar metodo y obtener total a pagar de reservacion
            Agregar campo de hora de reservacion
         Reservacion reservacion = webs.ObtenerTotalReservacion();
         String horaDeMovimiento = reservacion.getTimeAction;
          horaMovimiento = simpleDateFormat.parse(horaDeMovimiento);
          Date horaActual =getCurrentHour;
          long diferencia = horaMovimiento - horaActual;
        */
        long diferencia = 0;
        int days = (int) (diferencia / (1000 * 60 * 60 * 24));
        int hours = (int) ((diferencia - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (diferencia - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        double precioTotalReservacion = 100;
        if (min > 15) {
            //obtener de sw
            cargosCancelacion = 100 * .15;
            //Revisar si los cargos por cancelacion seran actualizados desde la bd o panel ponerlo en sw
        } else {
            cargosCancelacion = 100 * 0.05;
        }

        return cargosCancelacion;
    }

    public void notificarSocio(Vehicle vehicle) {
        /*
            int idsocio= webs.getSocioXvehiculo(vehicle);
            int idvehiculo = getvehicle;

            control.sendNotificaction(idsocio);
         */
    }

    public void CheckReservationStatus(int id_reservacion) {
        /*
        String status = webs.checkStatus(id_reservacion);

         */
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeColorStatusBar(Activity activity1) {
        Activity activity = activity1;
        Window window = activity.getWindow();
        activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.status));
    }

    public int showDialogTime(Context mContext){

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        final Calendar lastCalendar = Calendar.getInstance();
                        lastCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        lastCalendar.set(Calendar.MINUTE, minute);
                       // sql = new SqliteController(getApplicationContext(),"kangup",null,1);
                        //)  sql.updateReservacionHora(hourOfDay + ":" + minute);
                        mHour = hourOfDay;
                    }
                }, c.get((Calendar.HOUR_OF_DAY)), c.get(Calendar.MINUTE), false);
        timePickerDialog.show();
        return mHour;
    }

    public void showDialogCalendar(Context mContext ,final TextView fecha){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Date parseDate = null;

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        try {
                            parseDate = dateFormat.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEE, d MMM yyyy");
                        String finalString = dateFormat1.format(parseDate);
                        fecha.setText(""+finalString);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }


    public Calendar InitDateView(final Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.date_dialog, null);
        dialogBuilder.setView(dialogView);



        final EditText fecha = (EditText) dialogView.findViewById(R.id.fecha);


        final EditText hora = (EditText) dialogView.findViewById(R.id.hora);
        final ImageView bHora = (ImageView) dialogView.findViewById(R.id.IBhora);
        bHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = showDialogTime(context);
                hora.setText(""+c);
            }
        });
        final ImageView bFecha = (ImageView) dialogView.findViewById(R.id.IBfecha);
        bFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialogBuilder.setTitle("Disponbildad");
        dialogBuilder.setMessage("Ingrese hora y fecha para buscar vehiculos:");
        dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                //com.mx.bridgestudio.kangup.Models.Reservacion res = new com.mx.bridgestudio.kangup.Models.Reservacion();
                Intent setIntent = new Intent(context,CarsXtype.class);
                //CatalogCar.id_Marca = list.get
                context.startActivity(setIntent);
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();






        return c;
    }
    public boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isKeyboardShown(View rootView) {
    /* 128dp = 32dp * 4, minimum button height 32dp and generic 4 rows soft keyboard */
        final int SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128;

        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
    /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
        int heightDiff = rootView.getBottom() - r.bottom;
    /* Threshold size: dp to pixels, multiply with display density */
        boolean isKeyboardShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density;

        Log.d(TAG, "isKeyboardShown ? " + isKeyboardShown + ", heightDiff:" + heightDiff + ", density:" + dm.density
                + "root view height:" + rootView.getHeight() + ", rect:" + r);

        return isKeyboardShown;
    }
    public static void hideKeyboard(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow( context.getCurrentFocus().getWindowToken(), 0);
    }

    public void toolBarDown(final Activity activity, final View v){
            ImageButton catalogo, favoritos, historial,noticias;
            catalogo = (ImageButton)v.findViewById(R.id.catalogoToolbar);
            catalogo.setColorFilter(ContextCompat.getColor(activity.getApplicationContext(),R.color.colorAccent));
            catalogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  if (control.isOnline()) {
                     activity.finish(); // close this activity and return to preview activity (if there is any)
                     activity.startActivity(new Intent(activity.getApplicationContext(), CategoryActivity.class));
                     activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            noticias = (ImageButton)v.findViewById(R.id.noticiasToolbar);
            noticias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   if (control.isOnline()) {
                    activity.finish(); // close this activity and return to preview activity (if there is any)
                    activity.startActivity(new Intent(activity.getApplicationContext(), NewsActivity.class));
                    activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            favoritos  = (ImageButton)v.findViewById(R.id.favoritosToolbar);
            favoritos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  if (control.isOnline()) {
                    if(LoginActivity.guestFlag == 1){
                        alertGuest(activity);
                    }
                    else
                    {
                        activity.finish(); // close this activity and return to preview activity (if there is any)
                        activity.startActivity(new Intent(activity.getApplicationContext(), FavoriteActivity.class));
                        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                }
            });
            historial = (ImageButton)v.findViewById(R.id.historialToolbar);
            historial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   if (control.isOnline()) {
                    if(LoginActivity.guestFlag == 1){
                        alertGuest(activity);
                    }
                    else{
                        activity.finish(); // close this activity and return to preview activity (if there is any)
                        activity.startActivity(new Intent(activity.getApplicationContext(), HistoryActivity.class));
                        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            });
    }
    public void alertGuest(final Activity activity) {
        new AlertDialog.Builder(activity.getApplicationContext())
                .setTitle("Invitado")
                .setMessage("Gracias por visitar la aplicación de KangUp!! Para realizar la siguiente acción necesita estar registrado.")
                .setIcon(R.drawable.perfil_icon)
                .setPositiveButton("Registrar",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent setIntent = new Intent(activity, RegisterActivity.class);
                                activity.startActivity(setIntent);
                                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                activity. finish();
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void alertLogOut(final Activity activity) {
        session = new SessionManager(activity.getApplicationContext());
        new AlertDialog.Builder(activity.getApplicationContext())
                .setTitle("Cerrar sesion")
                .setMessage("¿Desea cerrar sesión?")
                .setIcon(R.drawable.ic_close_light)
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                session.logoutUser();
                                Intent setIntent = new Intent(activity.getApplicationContext(),LoginActivity.class);
                                activity.startActivity(setIntent);
                                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                activity.finish();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}

