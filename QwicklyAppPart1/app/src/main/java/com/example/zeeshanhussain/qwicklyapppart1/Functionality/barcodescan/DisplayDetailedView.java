package com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zeeshanhussain.qwicklyapppart1.FavouritesView;
import com.example.zeeshanhussain.qwicklyapppart1.MainActivity;
import com.example.zeeshanhussain.qwicklyapppart1.R;

import com.example.zeeshanhussain.qwicklyapppart1.ShoeData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class DisplayDetailedView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static final String TAG = "AddFriendsActivity";
    DatabaseReference firebaseDatabase;
   // DatabaseReference databaseReference;
   // final static String database_path = "Product";
   // Query query,deletequery;
    ProgressDialog progressDialog;
    ImageView DisplayImage;
    ImageButton AddToFavts;
    TextView Colour_Avaliable,Price,ProductName,Category,Material,SizeChart;
   // Dialog my_dialog;
Bundle bundle;

    //private RecyclerView.LayoutManager layoutManager;
    String barcodeId;

//    FirebaseRecyclerAdapter<ShoeData, ShoeDataViewHolder> firebaseRecyclerAdapter
//FirebaseRecyclerAdapter<ShoeData,ShoeDataViewHolder> firebaseRecyclerAdapter;
  //  private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detailed_view);
        DisplayImage =(ImageView)findViewById(R.id.image_display_detail);
        Colour_Avaliable=(TextView)findViewById(R.id.product_barcode_display);
        Price=(TextView)findViewById(R.id.product_Price_display);
        SizeChart=(TextView)findViewById(R.id.product_size_chart);
        ProductName=(TextView)findViewById(R.id.product_name_display);
        Category=(TextView)findViewById(R.id.product_category_display);
        Material=(TextView)findViewById(R.id.product_material_display);
        bundle=getIntent().getExtras();
        barcodeId=bundle.getString("barcode");
        firebaseDatabase=FirebaseDatabase.getInstance().getReference("Product");


        AddToFavts=(ImageButton)findViewById(R.id.overflow_display);
        AddToFavts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseDatabase.orderByChild("BarcodeId").equalTo(barcodeId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String value = dataSnapshot.child("isFavt").getValue().toString();
                        if(value.equals("true")) {
                            Toast.makeText(DisplayDetailedView.this,"Item is already in Favorites",Toast.LENGTH_LONG).show();
                        }

                        else  {
                            String TAG = "true";
                            dataSnapshot.getRef().child("isFavt").setValue(TAG);
                            AddToFavts.setImageResource(R.drawable.star);
                            Toast.makeText(DisplayDetailedView.this,"Added to Favorites",Toast.LENGTH_LONG).show();

                        }
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

                //Toast.makeText(DisplayDetailedView.this,"Value is "+value,Toast.LENGTH_LONG).show();
            }
        });
        progressDialog = new ProgressDialog(this);

        progressDialog.show();

        firebaseDatabase.orderByChild("BarcodeId").equalTo(barcodeId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressDialog.dismiss();
                String barcode=dataSnapshot.child("Colour").getValue().toString();
                Colour_Avaliable.setText("Colours : "+barcode);

                String price = dataSnapshot.child("Price").getValue().toString();
                Price.setText("PKR : "+price);

                String productname=dataSnapshot.child("ProductName").getValue().toString();
                ProductName.setText("Product Name : "+productname);

                String category=dataSnapshot.child("Category").getValue().toString();
                Category.setText("Category  : "+category);

                String material=dataSnapshot.child("Material").getValue().toString();
                Material.setText("Material : "+material);

                String sizeChart = dataSnapshot.child("SizeChart").getValue().toString();
                SizeChart.setText("Size : " + sizeChart);
                String imageUrl =dataSnapshot.child("image_url").getValue().toString() ;

                Glide.with(getApplicationContext()).load(imageUrl).into(DisplayImage);





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





       // recyclerView =(RecyclerView)findViewById(R.id.show_details_recyclerview);
      //recyclerView.setHasFixedSize(true);
        //layoutManager=new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //bundle=getIntent().getExtras();

        //barcodeId=bundle.getString("barcode");
       // Toast.makeText(DisplayDetailedView.this,""+barcodeId,Toast.LENGTH_LONG).show();
      //  firebaseDatabase = FirebaseDatabase.getInstance();
        //databaseReference=firebaseDatabase.getReference(database_path).child("0");
        //databaseReference.keepSynced(true);
        //databaseReference.addValueEventListener(new ValueEventListener() {



        }

    @Override
    public void onBackPressed() {

       bundle=getIntent().getExtras();
       String calling_activity=bundle.getString("activity_name");

       if(calling_activity.equals("Favorites")) {
           Intent intent = new Intent(DisplayDetailedView.this, FavouritesView.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);

       }
       else if(calling_activity.equals("Main")) {
           Intent intent = new Intent(DisplayDetailedView.this, MainActivity.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);


       }

       // Intent intent = new Intent(DisplayDetailedView.this, MainActivity.class);

       // startActivity(intent);



    }
}


