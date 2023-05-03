package com.example.zeeshanhussain.qwicklyapppart1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.CloudReco;

import com.example.zeeshanhussain.qwicklyapppart1.AccountActivity.LoginActivityPage;
import com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan.ProductDisplay;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button scan, favourite, offers;
        ImageView img;

        Toolbar toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        scan = (Button) findViewById(R.id.scanBtn);
        favourite = (Button) findViewById(R.id.favtBtn);
        offers = (Button) findViewById(R.id.OffersBtn);

        img = (ImageView) findViewById(R.id.logoImg);




        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //          startActivity(new Intent(this, BarcodeCaptureActivity.class));

                Intent i = new Intent(MainActivity.this,ProductDisplay.class);

                startActivity(i);

            }
        });
        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Offers.class));
            }
        });
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FavouritesView.class));
            }
        });



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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.signout,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivityPage.class));
                break;


        }

        return true;
    }
}