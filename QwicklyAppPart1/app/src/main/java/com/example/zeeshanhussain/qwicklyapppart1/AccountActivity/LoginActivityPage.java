package com.example.zeeshanhussain.qwicklyapppart1.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeeshanhussain.qwicklyapppart1.MainActivity;
import com.example.zeeshanhussain.qwicklyapppart1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class LoginActivityPage extends AppCompatActivity implements View.OnClickListener {

    private EditText emaillogin,passwordlogin;
    private Button LoginBtn,VIsitBtn,SignUpButton,ForgotButton;
    private TextView WelcomeText,InfoText;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String Product_UI;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

    emaillogin=(EditText)findViewById(R.id.EmailLogin);
    passwordlogin=(EditText)findViewById(R.id.PasswordLogin);
    LoginBtn=(Button)findViewById(R.id.LoginBtn);
    VIsitBtn=(Button)findViewById(R.id.BtnVist);

    ForgotButton=(Button)findViewById(R.id.btn_reset_password);

    SignUpButton=(Button)findViewById(R.id.SignUpBtn);
    WelcomeText=(TextView)findViewById(R.id.WelcomeTitle);
    InfoText=(TextView)findViewById(R.id.InfoText);
    progressDialog= new ProgressDialog(this);

    firebaseAuth= FirebaseAuth.getInstance();




    LoginBtn.setOnClickListener(this);
    SignUpButton.setOnClickListener(this);
    ForgotButton.setOnClickListener(this);
    VIsitBtn.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
/*
        FirebaseUser currentUser =firebaseAuth.getCurrentUser();
        if(currentUser !=null) {
            startActivity(new Intent(this, MainActivity.class));

        }
       // updateUI(currentUser);
*/
    }

    private void userLogin() {
        String emailTxt=emaillogin.getText().toString().trim();
        String passwordTxt =passwordlogin.getText().toString().trim();
        // checking if email and password are empty

        if(TextUtils.isEmpty(emailTxt)) {
            makeText(this,"Please enter email", LENGTH_LONG).show();
            return;

        }
        if(TextUtils.isEmpty(passwordTxt)){
            makeText(this,"Please enter your password", LENGTH_LONG).show();
            return;
        }
        // if both are not empty

        // display progress dialog

        progressDialog.show();
        progressDialog.setTitle("Logging in");


        // Now Firebase


        firebaseAuth.signInWithEmailAndPassword(emailTxt,passwordTxt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                     if(task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivityPage.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                     }
                     else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                     }
                    }
                });




    }
    @Override
    public void onClick(View view) {
        if(view == LoginBtn) {
            userLogin();
        }
        if(view == VIsitBtn) {

            Product_UI="http://3dinnovations.com.pk/";
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(Product_UI));
            startActivity(intent);
        }

        if(view == SignUpButton) {
       // finish();
        startActivity(new Intent(this, SignUpActivity.class));
        }
        if(view== ForgotButton) {
         //   finish();
            startActivity(new Intent(this,PasswordReset.class));

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUser currentUser =firebaseAuth.getCurrentUser();
        if(currentUser !=null) {
            startActivity(new Intent(this, MainActivity.class));

        }
    }
}
