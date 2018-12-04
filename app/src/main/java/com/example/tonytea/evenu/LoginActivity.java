package com.example.tonytea.evenu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthException;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               if (actionId == R.integer.login || actionId == EditorInfo.IME_NULL){
                   //attemptLogin();
                   return true;
               }
                return false;
            }
        });

        // Grab instance of Firebase.Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void signInUser(View v){
        // calls attemptLogin()
        attemptLogin();
    }

    public void registerUser(View v){
        //Opens up registration page
        Intent intent = new Intent(this, com.example.tonytea.evenu.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    private void attemptLogin() {

        String emailAddr = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (emailAddr.isEmpty()) return;
        Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(emailAddr, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("FlashChat", "SignInWithEmail() onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()){
                    Log.d("Evenu", "There was a problem signing in: " + task.getException());
                    showErrorDialog("There was a problem signing in");
                }
                else{
                     Intent intent = new Intent (LoginActivity.this, MainDisplayActivity.class);
                     finish();
                     startActivity(intent);
                }
            }
        });


    }

    private void showErrorDialog(String message){
        new AlertDialog.Builder(this).setTitle("oops").setMessage(message).setPositiveButton("ok",null).show();
    }
}
