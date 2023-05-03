package com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zeeshanhussain.qwicklyapppart1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsFavt extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference firebaseDatabase;
    ProgressDialog progressDialog;
    ImageView DisplayImage;
    Button Buy_Btn;
    TextView BarcodeId,Price,ProductName,Category,Material;
    Bundle bundle;
    String barcodeId;
    String Product_Uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_favt);
        DisplayImage =(ImageView)findViewById(R.id.image_display_detail_favt);
        BarcodeId=(TextView)findViewById(R.id.product_barcode_display_favt);
        Price=(TextView)findViewById(R.id.product_Price_display_favt);
        ProductName=(TextView)findViewById(R.id.product_name_display_favt);
        Category=(TextView)findViewById(R.id.product_category_display_favt);
        Material=(TextView)findViewById(R.id.product_material_display_favt);
        Buy_Btn=(Button)findViewById(R.id.ViewOnline);
        bundle=getIntent().getExtras();
        barcodeId=bundle.getString("barcode");
        firebaseDatabase= FirebaseDatabase.getInstance().getReference("Product");
        progressDialog = new ProgressDialog(this);

        progressDialog.show();

        firebaseDatabase.orderByChild("BarcodeId").equalTo(barcodeId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressDialog.dismiss();
                String barcode=dataSnapshot.child("BarcodeId").getValue().toString();
                BarcodeId.setText("Barcode : "+barcode);

                String price = dataSnapshot.child("Price").getValue().toString();
                Price.setText("PKR : "+price);

                String productname=dataSnapshot.child("ProductName").getValue().toString();
                ProductName.setText("Product Name : "+productname);

                String category=dataSnapshot.child("Category").getValue().toString();
                Category.setText("Category  : "+category);

                String material=dataSnapshot.child("Material").getValue().toString();
                Material.setText("Material : "+material);

                String imageUrl =dataSnapshot.child("image_url").getValue().toString() ;


                Glide.with(getApplicationContext()).load(imageUrl).into(DisplayImage);

                Product_Uri=dataSnapshot.child("product_url").getValue().toString();





            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Buy_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Product_Uri !=null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(Product_Uri));
                    startActivity(intent);

                }
                else {
                    Toast.makeText(DetailsFavt.this,"Sorry the URL is not avaliable",Toast.LENGTH_LONG).show();

                }
                            }
        });
    }

}
