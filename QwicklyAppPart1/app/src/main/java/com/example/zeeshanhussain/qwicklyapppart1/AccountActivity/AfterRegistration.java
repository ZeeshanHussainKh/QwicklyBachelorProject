package com.example.zeeshanhussain.qwicklyapppart1.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zeeshanhussain.qwicklyapppart1.R;

public class AfterRegistration extends AppCompatActivity {
Button AfterButton;
TextView AfterView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_registration);

        AfterButton=(Button)findViewById(R.id.afterButton);
        AfterView=(TextView)findViewById(R.id.textAfter);

        AfterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterRegistration.this,LoginActivityPage.class);
                startActivity(intent);

            }
        });
    }
}
