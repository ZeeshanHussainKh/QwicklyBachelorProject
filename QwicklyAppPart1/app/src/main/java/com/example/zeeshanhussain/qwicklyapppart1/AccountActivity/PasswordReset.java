package com.example.zeeshanhussain.qwicklyapppart1.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeeshanhussain.qwicklyapppart1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    private ImageView logo;
    private TextView forgotLabel,forgotDescr;
    private EditText emailText;
    private Button ResetBtn,BackBtn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

    logo=(ImageView)findViewById(R.id.forgot_logo);
    forgotLabel=(TextView)findViewById(R.id.forgot_password_label);
    forgotLabel=(TextView)findViewById(R.id.DescForgot);
    ResetBtn=(Button)findViewById(R.id.btn_reset);
    emailText=(EditText)findViewById(R.id.emailReset);
    BackBtn=(Button)findViewById(R.id.btn_back);
    progressBar=new ProgressBar(this);
    mAuth=FirebaseAuth.getInstance();

    ResetBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String emailRes=emailText.getText().toString().trim();

            if(TextUtils.isEmpty(emailRes)) {
               // Toast.makeText(PasswordReset.this,"Please enter your email",Toast.LENGTH_LONG).show();
                emailText.setError("Please enter your email");
                emailText.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(emailRes).matches()) {
                emailText.setError("Invalid email");
                emailText.requestFocus();

                // Toast.makeText(PasswordReset.this,emailRes+" is not a valid Email Address",Toast.LENGTH_LONG).show();
                return;
            }
            mAuth.sendPasswordResetEmail(emailRes)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordReset.this,"Check email for reset password",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(PasswordReset.this,"Failed to reset Password..",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        }
    });
BackBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(PasswordReset.this,LoginActivityPage.class);
        startActivity(intent);
    }
});
    }
}
