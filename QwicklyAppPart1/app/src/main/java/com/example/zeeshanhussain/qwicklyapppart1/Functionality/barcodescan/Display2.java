package com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeeshanhussain.qwicklyapppart1.MainActivity;
import com.example.zeeshanhussain.qwicklyapppart1.R;

public class Display2 extends AppCompatActivity {
Bundle bundle;
String barcode;
ImageView product_image;
ImageButton FavtBtn;
 TextView price
        ,product_name,material,category,size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);

        FavtBtn=(ImageButton)findViewById(R.id.display_addToFavts);
        product_image=(ImageView)findViewById(R.id.product_preview);
        price=(TextView) findViewById(R.id.display_price);

        product_name=(TextView)findViewById(R.id.display_product_name);
        material=(TextView)findViewById(R.id.display_material);
        category=(TextView)findViewById(R.id.display_category);
        size=(TextView)findViewById(R.id.display_size);
        FavtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavtBtn.setImageResource(R.drawable.star);
                Toast.makeText(getApplicationContext(),"The item is already in favorites. Please see Favoruties",Toast.LENGTH_LONG).show();
            }
        });
        //Toast.makeText(this,"Price is "+price.getText().toString(),Toast.LENGTH_LONG).show();

        bundle=getIntent().getExtras();

        barcode=bundle.getString("barcode");

        if(barcode.equals("5045235225328"))
        {
            product_image.setImageResource(R.drawable.product_1);
            price.setText("Price is PKR.1,190");
            product_name.setText("Product name : POLO SHIRT");
            material.setText("Material : COTTON");
            category.setText("Category : Men's Summer");
            size.setText("Size avaliable: S| M | L | ");

        }
        else if(barcode.equals("9780241972939")){
            product_image.setImageResource(R.drawable.product_2);
            price.setText("Price is PKR.1,190");
            product_name.setText("Product name : T-Shirt");
            material.setText("Material : COTTON");
            category.setText("Category : Men's Summer");
            size.setText("Size avaliable: S| M | L ");
        }
        else if(barcode.equals("9780671791544")){
            product_image.setImageResource(R.drawable.product_2);
            price.setText("Price is PKR.1,190");
            product_name.setText("Product name : T-Shirt");
            material.setText("Material : COTTON");
            category.setText("Category : Men's Summer");
            size.setText("Size avaliable: S| M | L ");
        }

        else if(barcode.equals("028828520531")){
            product_image.setImageResource(R.drawable.product_3);
            price.setText("Price is 800 Rs");
            product_name.setText("Product name : Men's Jeans Special");
            material.setText("Material : Leather");
            category.setText("Mens Casual");
            size.setText("Size avaliable : S | M");


        }

        else if(barcode.equals("4059601830796")){
            product_image.setImageResource(R.drawable.product_4);
            price.setText("Price is 3000 Rs");
            product_name.setText("Product name : Men's Special Wear");
            material.setText("Material : Cotton");
            category.setText("Mens Special Wear");
            size.setText("Size avaliable : S | M | XL");
        }




    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Display2.this, MainActivity.class);
        startActivity(intent);
    }
}
