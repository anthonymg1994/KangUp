package com.mx.bridgestudio.kangup.Views.PaginasInicio;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//import com.koushikdutta.ion.Ion;

public class RegisterActivity extends AppCompatActivity {

    private CardView card;
    private ImageView imageViewRound;
    private EditText name, lastname_pat,lastname_mat,mail,password,confirm;
    private Button next;
    private User user = new User();
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private final int PICK_IMAGE = 12345;
    private final int TAKE_PICTURE = 6352;
    private static final int REQUEST_CAMERA_ACCESS_PERMISSION =5674;
    private Bitmap bitmap;
    public String path="";
    Control control = new Control();
    public static String i;
    private AlertDialog alertTypePayment;
    private Uri uri;  //图片保存uri
    private File file;
    String imagePath;
    private static final int REQUEST_IMAGE = 100;
    CharSequence[] values = {"Cámara","Galería"};
    webServices webs = new webServices();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRegister);
        setSupportActionBar(toolbar);
        control.changeColorStatusBar(RegisterActivity.this);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().set
        }

        name = (EditText) findViewById(R.id.namedrawer);
        name.setHintTextColor(getResources().getColor(R.color.white));
        lastname_pat = (EditText) findViewById(R.id.lastnamepat);
        lastname_pat.setHintTextColor(getResources().getColor(R.color.white));
        lastname_mat = (EditText) findViewById(R.id.lastnamemat);
        lastname_mat.setHintTextColor(getResources().getColor(R.color.white));
        mail = (EditText) findViewById(R.id.editEmail);
        mail.setHintTextColor(getResources().getColor(R.color.white));
        password = (EditText) findViewById(R.id.password);
        password.setHintTextColor(getResources().getColor(R.color.white));
        confirm = (EditText) findViewById(R.id.passwordr);
        confirm.setHintTextColor(getResources().getColor(R.color.white));

        card = (CardView) findViewById(R.id.cardImage);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAlertDialogWithRadioButtonGroup();
            }
        });
        imageViewRound = (ImageView) findViewById(R.id.imageProfilee);
        imageViewRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAlertDialogWithRadioButtonGroup();
            }
        });
        name.setText("dasdasd");
        lastname_pat.setText("dasdasd");
        lastname_mat.setText("hhhh");
        mail.setText("asdas@g.com");
        password.setText("12345");
        confirm.setText("12345");
        file = new File(Environment.getExternalStorageDirectory(), name + ".jpg");

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p1, p2;
                p1 = password.getText().toString();
                p2 = confirm.getText().toString();
                String email = mail.getText().toString().trim();
                //Verifica campos vacios
                if (name.getText().toString().equals("") || lastname_pat.getText().toString().equals("") || lastname_mat.getText().toString().equals("")
                        || mail.getText().toString().equals("") || password.getText().toString().equals("") || confirm.getText().toString().equals("")) {
                    Toast msg = Toast.makeText(getBaseContext(),
                            "Faltan campos por llenar", Toast.LENGTH_SHORT);
                    msg.show();
                } else {
                    if (email.matches(emailPattern)) {
                        //Se valida que tenga formato de email
                        if (p1.equals(p2)) {
                            // se validan que ambas contraselas sean identicas email = emailPattern
                            //Y verifica que tenga mas de 8 caracteres
                            if (!isPasswordValid(p1)) {
                                Snackbar snackbar = Snackbar.make(view, "La contraseña debe tener al menos 8 caracteres", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }else {
                                // validar espacios o remplazar con string replace por _
                                user.setFirstName(name.getText().toString());
                                user.setAp_paterno(lastname_pat.getText().toString());
                                user.setAp_materno(lastname_mat.getText().toString());
                                user.setEmail(mail.getText().toString().trim());
                                user.setPassword(password.getText().toString());
                                user.setFile(file);

                                webs.insertUser(RegisterActivity.this, user);

                                Snackbar snackbar = Snackbar.make(view, "Registro con exito", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        } else {

                            Snackbar snackbar = Snackbar.make(view, "Error Las contraseñas no son identicas", Snackbar.LENGTH_SHORT);
                            snackbar.show();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        //or
                        Snackbar snackbar = Snackbar.make(view, "Correo invalido", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }

                }

            }
        });

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            //fromCamera.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }

    public void CreateAlertDialogWithRadioButtonGroup(){

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

        builder.setTitle("Selecciona alguna opcion:");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                                && ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA_ACCESS_PERMISSION);
                        }else {
                            getImageFromCamera();
                        }
                        break;
                    case 1:
                        getImageFromGallery();
                        break;
                }
                alertTypePayment.dismiss();
            }
        });
        alertTypePayment = builder.create();
        alertTypePayment.show();

    }




    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    private void getImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_IMAGE);

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, TAKE_PICTURE);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                FileInputStream in = null;
                try {
                    in = new FileInputStream(file);
                } catch (FileNotFoundException e) {


                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                imagePath = file.getAbsolutePath();
                Log.d("INFO", "PATH === " +imagePath);
                //tvPath.setText(imagePath);
                Bitmap bmp = BitmapFactory.decodeStream(in, null, options);



            } else if (requestCode == TAKE_PICTURE) {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    bitmap = (Bitmap) extras.get("data");
                    imageViewRound.setImageBitmap(bitmap);
                    imageViewRound.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getImageFromCamera();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        }


    }



    @Override
    protected void onResume() {
        super.onResume();
        //getting Root View that gets focus
        View rootView =((ViewGroup)findViewById(android.R.id.content)).
                getChildAt(0);
        rootView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    control.hideKeyboard(RegisterActivity.this);
                }
            }
        });
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 8;
    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this,LoginActivity.class);
        startActivity(setIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


}
