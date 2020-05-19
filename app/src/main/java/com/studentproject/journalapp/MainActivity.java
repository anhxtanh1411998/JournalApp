package com.studentproject.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btLogin;
    private Button btRegister;
    private Button btGoogleLogin;
    private static final String TAG = "EmailPasswordAuth";
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getReferenceUsersLogin();
        setBtLogin();

    }

    public void setBtLogin() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegistrationLogin();
            }
        });

    }

    private void handleRegistrationLogin() {
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();

        if (!validateEmailPass(email, password)) {
            return;
        }

        //show progress dialog
        showProgressDialog();

        //perform login and account creation depending on existence of email in firebase
        performLoginOrAccountCreation(email, password);
    }

    private boolean validateEmailPass(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Required.");
            valid = false;
        } else if (!email.contains("@")) {
            edtEmail.setError("Not an email id.");
            valid = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Required.");
            valid = false;
        } else if (password.length() < 6) {
            edtPassword.setError("Min 6 chars.");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        return valid;
    }

    private void getReferenceUsersLogin() {
        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btLogin = findViewById(R.id.bt_login);
        btRegister = findViewById(R.id.bt_register);
        btGoogleLogin = findViewById(R.id.bt_google);
    }

    private void performLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "login success");
                            startActivity(new Intent(MainActivity.this,JournalActivity.class));
                            finish();
                        } else {
                            Log.e(TAG, "Login fail", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //hide progress dialog
                        hideProgressDialog();
                        //enable and disable login, logout buttons depending on signin status
                        showAppropriateOptions();
                    }
                });
    }

    private void performLoginOrAccountCreation(final String email, final String password) {
        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(
                this, new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "checking to see if user exists in firebase or not");
                            ProviderQueryResult result = task.getResult();

                            if (result != null && result.getProviders() != null
                                    && result.getProviders().size() > 0) {
                                Log.d(TAG, "User exists, trying to login using entered credentials");
                                performLogin(email, password);
                            } else {
                                Log.d(TAG, "User doesn't exist, creating account");
                                // registerAccount(email, password);
                            }
                        } else {
                            Log.w(TAG, "User check failed", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "There is a problem, please try again later.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        //hide progress dialog
                        hideProgressDialog();
                        //enable and disable login, logout buttons depending on signin status
                      //  showAppropriateOptions();
                    }
                });
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait!");
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showAppropriateOptions() {
        hideProgressDialog();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            btLogin.setVisibility(View.GONE);
            //   findViewById(R.id.logout_items).setVisibility(View.VISIBLE);

            //  findViewById(R.id.verify_b).setEnabled(!user.isEmailVerified());
        } else {
            btLogin.setVisibility(View.VISIBLE);
            // findViewById(R.id.logout_items).setVisibility(View.GONE);
        }
    }
}
