package com.mx.bridgestudio.kangup.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Lists.ListBrand;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CarsXtype;
import com.squareup.picasso.Picasso;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.RegisterActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Anthony on 09/11/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private int mYear, mMonth, mDay, mHour, mMinute, pm;

    private Context mContext;
    private List<ListBrand> listMarcas;
    private View v;
    public ClipData.Item currentItem;
    public CardView cardView;
    public static int id_marca = 0;
    public static String marca = "";
    public static String hour = "";
    public static String hour_final = "";
    public static Date datee = null;
    Control c = new Control();
    EditText hora, horaTermino;
    EditText fecha;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public ListBrand list;


        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            //count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
            cardView = (CardView) view.findViewById(R.id.card_view_catalogo);


        }
    }

    public CardAdapter(Context mContext, List<ListBrand> listMarcas) {
        this.mContext = mContext;
        this.listMarcas = listMarcas;
    }

    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_places_cars, parent, false);
        return new CardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardAdapter.MyViewHolder holder, final int position) {
        final ListBrand list = listMarcas.get(position);
        holder.title.setText(list.getName());
     //   Glide.with(mContext).load(R.drawable.marca).into(holder.thumbnail);

        Picasso.with(mContext).load(list.getImage()).into(holder.thumbnail);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Position" + list.getName(), Toast.LENGTH_SHORT).show();
                id_marca = list.getId();
                marca = list.getName();
                if(LoginActivity.guestFlag==1)
                {
                    Intent setIntent = new Intent(mContext, CarsXtype.class);
                    mContext.startActivity(setIntent);
                }
                else{
                    InitDateView();
                }

            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Position" + list.getName(), Toast.LENGTH_SHORT).show();
                id_marca = list.getId();
                marca = list.getName();
                if(LoginActivity.guestFlag==1)
                {
                    Intent setIntent = new Intent(mContext, CarsXtype.class);
                    mContext.startActivity(setIntent);
                }
                else{
                    InitDateView();
                }
            }
        });


    }

    private void showDialogTime(final int x) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        final TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if (x == 1) {
                            hora.setText(hourOfDay + ":" + minute + " ");
                            hour = hora.getText().toString();
                        } else if (x == 2) {
                            horaTermino.setText(hourOfDay + ":" + minute + " ");
                            hour_final = horaTermino.getText().toString();
                        }
                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    public void InitDateView() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext,R.style.MyDialogTheme);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.date_dialog, null);
        dialogBuilder.setView(dialogView);
        fecha = (EditText) dialogView.findViewById(R.id.fecha);
        hora = (EditText) dialogView.findViewById(R.id.hora);
        horaTermino = (EditText) dialogView.findViewById(R.id.horatermino);
        final ImageView bHora_termino = (ImageView) dialogView.findViewById(R.id.IBhoraTermino);


        fecha.setHintTextColor(mContext.getResources().getColor(R.color.textoHint));
        hora.setHintTextColor(mContext.getResources().getColor(R.color.textoHint));
        horaTermino.setHintTextColor(mContext.getResources().getColor(R.color.textoHint));



        final ImageView bHora = (ImageView) dialogView.findViewById(R.id.IBhora);


        bHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTime(1);
            }
        });
        bHora_termino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTime(2);
            }
        });

        final ImageView bFecha = (ImageView) dialogView.findViewById(R.id.IBfecha);
        bFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCalendar();
            }
        });

        dialogBuilder.setTitle("Disponibilidad");
        dialogBuilder.setMessage("Completa los siguientes datos:");
        dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                //com.mx.bridgestudio.kangup.Models.Reservacion res = new com.mx.bridgestudio.kangup.Models.Reservacion();
                if (fecha.getText().toString().equals("") || hora.getText().toString().equals("") || horaTermino.getText().toString().equals("")) {



                }else{
                    Intent setIntent = new Intent(mContext, CarsXtype.class);
                    //CatalogCar.id_Marca = list.get
                    mContext.startActivity(setIntent);
                    ((Activity) mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

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
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_cars, popup.getMenu());
        popup.setOnMenuItemClickListener(new CardAdapter.MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Agregar a favorito", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    //Revisar que poner en este campo
                    Toast.makeText(mContext, "Recomendar", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

        @Override
        public int getItemCount() {
            return listMarcas.size();
        }




        private void showDialogCalendar() {
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
                            Date parseDate2 = null;

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
                            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            try {
                                parseDate = dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEE, d MMM yyyy");
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

                            String finalString = dateFormat1.format(parseDate);
                            String finalString2 = dateFormat2.format(parseDate);

                            try {
                                Date last_date_date = new SimpleDateFormat("yyyy-MM-dd").parse(finalString2);
                                fecha.setText("" + finalString);
                                datee = last_date_date;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

    public void alertGuest() {
        new AlertDialog.Builder(mContext)
                .setTitle("Invitado")
                .setMessage("Gracias por visitar la aplicación de KangUp!! Para realizar la siguiente acción necesita estar registrado.")
                .setIcon(R.drawable.perfil_icon)
                .setPositiveButton("Registrar",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent setIntent = new Intent(mContext, RegisterActivity.class);
                                mContext.startActivity(setIntent);
                                ((Activity) mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    }



