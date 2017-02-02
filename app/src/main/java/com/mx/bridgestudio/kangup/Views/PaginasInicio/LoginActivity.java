package com.mx.bridgestudio.kangup.Views.PaginasInicio;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;
import com.mx.bridgestudio.kangup.AsyncTask.Usuario.AsynkTaskUser;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SessionManager;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private AsynkTaskUser mAuthTask = null;
    webServices web = new webServices();
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ImageView logo;
    private TextInputLayout txt;
    private Button guest, register, forgot, signin;
    CoordinatorLayout coordinatorLayout;
    private User user = new User();
    private SqliteController sql = new SqliteController(this, "kangup", null, 1);
    private Control control = new Control();
    SessionManager session;
    public static int guestFlag=0;

    /**
     * The {@link Tracker} used to record screen views.
     */
    private Tracker mTracker;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]*/

        // Set up the login form.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.snackbarCoordinatorLayout);
        session = new SessionManager(this);

        control.changeColorStatusBar(LoginActivity.this);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.user);
        mEmailView.setHintTextColor(getResources().getColor(R.color.textoHint));
        logo = (ImageView)findViewById(R.id.logo);
        guest = (Button)findViewById(R.id.guest);

        guest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                finish();
                startActivity(new Intent(LoginActivity.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                guestFlag = 1;
            }
        });
        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        forgot = (Button)findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                finish();
             //   startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        mPasswordView = (EditText) findViewById(R.id.passwordeye);
        mPasswordView.setHintTextColor(getResources().getColor(R.color.textoHint));
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.loginform_layout || id == EditorInfo.IME_NULL) {
                    attemptLogin(textView);
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.signin);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                guestFlag=0;
                attemptLogin(view);
            }
        });


        mEmailView.setText("isaac.figalf@gmail.com");
        mPasswordView.setText("12345678");
        mLoginFormView = findViewById(R.id.loginform_layout);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(View view) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            if(control.isNetworkAvailable(this)) {
                user.setEmail(mEmailView.getText().toString());
                user.setPassword(mPasswordView.getText().toString());
                web.Login(this, user);

            }else{
                Snackbar snackbar = Snackbar.make(view, "Usuario incorrectos", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Send a screen view when the Activity is displayed to the user.
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 8;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

