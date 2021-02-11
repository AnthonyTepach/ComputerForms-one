package com.anthonytepach.computerforms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.sql.SQLOutput;

public class Login extends AppCompatActivity {
    private EditText mEmailField,mPasswordField;

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            getDashboard();
        }

    }
    private void setup() {
        mEmailField = (EditText)findViewById(R.id.email);
        mPasswordField = (EditText)findViewById(R.id.pass);
    }

    /**
     * Llamar al Activity Main
     */
    public void getMainView(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void signIn(String correo,String contrasenia){
        if (!validateForm()) {
            return;
        }


        mAuth.signInWithEmailAndPassword(correo, contrasenia)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("OK","signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            getDashboard();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Error", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            System.out.println("Error");
                        }

                        // [END_EXCLUDE]
                    }
                });
    }
    @Override
    public void onBackPressed() {

    }
    private void getDashboard() {
        Intent a=new Intent(this,dashboard.class);
        startActivity(a);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Debe ingresar el correo electronico");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Debe ingresar su contraseña.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }




    public void onClick(View v) {
        int i = v.getId();
          if (i == R.id.login) {
              signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

          }
    }



}
