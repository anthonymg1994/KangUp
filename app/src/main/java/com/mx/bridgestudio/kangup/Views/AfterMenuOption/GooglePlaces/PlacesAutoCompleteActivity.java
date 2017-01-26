package com.mx.bridgestudio.kangup.Views.AfterMenuOption.GooglePlaces;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.maps.android.SphericalUtil;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Reservacion;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

/**
 * Created by USUARIO on 16/12/2016.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.mx.bridgestudio.kangup.Adapters.PlacesAutoCompleteAdapter;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Models.Constants;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;


public class PlacesAutoCompleteActivity extends DrawerActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    protected GoogleApiClient mGoogleApiClient;

    /*
    StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
    sb.append("?sensor=false&key=" + API_KEY);
    sb.append("&components=country:il");
    sb.append("&types=(cities)");
    sb.append("&input=" + URLEncoder.encode(input, "utf8"));
*/
    private SqliteController sql;
    AutocompleteFilter filter = new AutocompleteFilter.Builder().setCountry("MX").build();
    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(
            new LatLng(-0, 0), new LatLng(0, 0));

    private EditText mAutocompleteView, mAutocompleteView_destino;
    private int flag = 0;
    private int flagFill = 0,flagFillDestino = 0;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    PlacesAutoCompleteAdapter.PlaceAutocomplete item = null;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    ImageView delete,deleted;
    private static String origen;
    private static String destino;
    private Button addruta;
    int option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_search);
        mAutocompleteView = (EditText)findViewById(R.id.autocomplete_places);
        mAutocompleteView_destino = (EditText)findViewById(R.id.autocomplete_places_destino);
        addruta = (Button) findViewById(R.id.addRutaPlaces);


        addruta.setOnClickListener(this);

        /*
        addruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAutocompleteView.getText().toString().equals("") || mAutocompleteView_destino.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),mAutocompleteView.getText().toString() + mAutocompleteView_destino.getText().toString() ,Toast.LENGTH_SHORT).show();

                }else{

                }

            }
        });
        */
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
             option = bundle.getInt("option");
        }
        delete=(ImageView)findViewById(R.id.cross);
        deleted=(ImageView)findViewById(R.id.crosss);

        mAutoCompleteAdapter =  new PlacesAutoCompleteAdapter(this, R.layout.searchview_adapter,
                mGoogleApiClient, BOUNDS_INDIA, filter);

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAutoCompleteAdapter);
        delete.setOnClickListener(this);
        mAutocompleteView.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                flag = 1;
                if (!s.toString().equals("") && mGoogleApiClient.isConnected()) {
                    mAutoCompleteAdapter.getFilter().filter(s.toString());
                }else if(!mGoogleApiClient.isConnected()){
                    Toast.makeText(getApplicationContext(), Constants.API_NOT_CONNECTED,Toast.LENGTH_SHORT).show();
                    Log.e(Constants.PlacesTag,Constants.API_NOT_CONNECTED);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                mAutoCompleteAdapter.getFilter().filter("");
            }
        });
        mAutocompleteView_destino.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                flag = 2;
                if (!s.toString().equals("") && mGoogleApiClient.isConnected()) {
                    mAutoCompleteAdapter.getFilter().filter(s.toString());
                }else if(!mGoogleApiClient.isConnected()){
                    Toast.makeText(getApplicationContext(), Constants.API_NOT_CONNECTED,Toast.LENGTH_SHORT).show();
                    Log.e(Constants.PlacesTag,Constants.API_NOT_CONNECTED);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                    public void onItemClick(View view, int position) {
                        item = mAutoCompleteAdapter.getItem(position);
                        final String placeId = String.valueOf(item.placeId);

                        Log.i("TAG", "Autocomplete item selected: " + item.description);
                            Toast.makeText(getApplicationContext(), item.description,Toast.LENGTH_SHORT).show();


                            if(flag == 1){
                                origen = ""+item.description;
                                mAutocompleteView.setText(""+item.description);
                                flagFill = 1;
                                mAutoCompleteAdapter.getFilter().filter(" ");


                            }else if(flag == 2){
                                destino = ""+item.description;
                                mAutocompleteView_destino.setText(""+item.description);
                                flagFillDestino = 1;
                                mAutoCompleteAdapter.getFilter().filter(" ");

                            }
                        /*        Toast.makeText(this, Constants.API_NOT_CONNECTED,Toast.LENGTH_SHORT).show();

                             Issue a request to the Places Geo Data API to retrieve a Place object with additional details about the place.
                         */

                        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                                .getPlaceById(mGoogleApiClient, placeId);
                        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(PlaceBuffer places) {
                                if(places.getCount()==1){
                                    //Do the things here on Click.....
                                    Toast.makeText(getApplicationContext(),String.valueOf(places.get(0).getLatLng()),Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),Constants.SOMETHING_WENT_WRONG,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Log.i("TAG", "Clicked: " + item.description);
                            Reservacion ruta= new Reservacion();
                            ruta.setOrigen(item.description.toString());
                            sql = new SqliteController(PlacesAutoCompleteActivity.this, "kangup",null, 1);
                            sql.Connect();
                            //sql.in(item.description.toString(),"");
                            sql.Close();


                          //  onBackPressed();

                        Log.i("TAG", "Called getPlaceById to get Place details for " + item.placeId);
                    }


                })
        );
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("Google API Callback", "Connection Done");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("Google API Callback", "Connection Suspended");
        Log.v("Code", String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v("Google API Callback","Connection Failed");
        Log.v("Error Code", String.valueOf(connectionResult.getErrorCode()));
        Toast.makeText(this, Constants.API_NOT_CONNECTED,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v==delete){
            mAutocompleteView.setText("");
        }
        if(v==deleted){
            mAutocompleteView_destino.setText("");
        }
        if(v.getId() == R.id.addRutaPlaces){
            if(mAutocompleteView.getText().toString().equals("") || mAutocompleteView_destino.getText().toString().equals("")){

            }else{
                Toast.makeText(getApplicationContext(),mAutocompleteView.getText().toString() + mAutocompleteView_destino.getText().toString() ,Toast.LENGTH_SHORT).show();
                sql = new SqliteController(PlacesAutoCompleteActivity.this, "kangup",null, 1);
                sql.Connect();
                int id_reservacion = sql.getReservacionIdNext();
                sql.insertRutas(mAutocompleteView.getText().toString(),mAutocompleteView_destino.getText().toString(),id_reservacion);
                sql.Close();



                Intent setIntent = new Intent(PlacesAutoCompleteActivity.this, com.mx.bridgestudio.kangup.Views.AfterMenuOption.Reservacion.class);
                //setIntent.putExtra("option",1);
                //  setIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()){
            Log.v("Google API","Connecting");
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            Log.v("Google API","Dis-Connecting");
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onBackPressed()
    {
      super.onBackPressed();
    }

}