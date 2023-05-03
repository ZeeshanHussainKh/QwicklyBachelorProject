package com.example.zeeshanhussain.qwicklyapppart1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Offers extends AppCompatActivity {

    ImageView offers1,offer2,offer3;
    String Product_UI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
    offers1=(ImageView)findViewById(R.id.offer1);
    offer2=(ImageView)findViewById(R.id.offer2);
    offer3=(ImageView)findViewById(R.id.offer3);

    Glide.with(getApplicationContext()).load("https://scontent-fco1-1.xx.fbcdn.net/v/t1.0-9/s960x960/101235444_10158108873126013_7026478423656628224_o.jpg?_nc_cat=111&_nc_sid=dd9801&_nc_ohc=JsEdMq_Vzw0AX-IuYrJ&_nc_ht=scontent-fco1-1.xx&_nc_tp=7&oh=0facb6f20f62f1d831cc905ffb031449&oe=5F02F840").into(offers1);
    Glide.with(getApplicationContext()).load("https://media.gucci.com/content/DiaryArticleDouble_Standard_1400x894/1588067107/DiaryArticleDouble_S03-PREFALL-ADV-01_001_Default.jpg").into(offer2);
    Glide.with(getApplicationContext()).load("https://i.ytimg.com/vi/YG8kLlRwhPM/maxresdefault.jpg").into(offer3);


    offers1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Product_UI="https://www.gucci.com/us/en/";
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(Product_UI));
            startActivity(intent);
        //    Toast.makeText(Offers.this,"Please visit your nearest Outfitters outlet to avail offer",Toast.LENGTH_LONG).show();
        }
    });

    offer2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Product_UI="https://www.gucci.com/us/en/";
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(Product_UI));
            startActivity(intent);

            //Toast.makeText(Offers.this,"Sorry the sale has expired",Toast.LENGTH_LONG).show();

        }
    });

    offer3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Product_UI="https://www.gucci.com/us/en/";
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(Product_UI));
            startActivity(intent);
            //Toast.makeText(Offers.this,"Promo is J.123",Toast.LENGTH_LONG).show();
        }
    });

    }
}
