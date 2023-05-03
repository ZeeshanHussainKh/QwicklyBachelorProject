package com.example.zeeshanhussain.qwicklyapppart1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan.DetailsFavt;
import com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan.DisplayDetailedView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FavouritesView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference firebaseDatabase;
    //private DatabaseReference databaseReference;
    Query favtquery;
    TextView DisplayText;
    ProgressDialog progressDialog;
    String testData;

    String removeItem;

    int positiontest;
    //  Query testQuery;

    ShoeDataViewHolder testholder;

    FirebaseRecyclerAdapter<Product, ShoeDataViewHolder> firebaseRecyclerAdapter;

    List<Product> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_view);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        // DisplayText = (TextView)findViewById(R.id.TestText);
        //recyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Product");

        //   databaseReference=firebaseDatabase.getReference("Product");

        // favtquery=firebaseDatabase.orderByChild("BarcodeId").equalTo("9780671791544");
        favtquery = firebaseDatabase.orderByChild("isFavt").equalTo("true");
        firebaseDatabase.keepSynced(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ShoeDataViewHolder>(Product.class, R.layout.card_item2, ShoeDataViewHolder.class, favtquery) {
            @Override
            protected void populateViewHolder(final ShoeDataViewHolder viewHolder, final Product model, final int position) {
                progressDialog.dismiss();

                // viewHolder.SetShoeRecords(model.getPrice(),model.getCategory(),model.getMaterial(),model.getCategory(),model.getProductName());
                viewHolder.ProductNameText.setText(model.getProductName());
                viewHolder.CategoryText.setText(model.getCategory());
                viewHolder.PriceText.setText(model.getPrice());
                // viewHolder.DisplayImage.setImageResource(R.drawable.bata);

                //  Toast.makeText(FavouritesView.this,""+model.getImage_url(),Toast.LENGTH_LONG).show();
                Glide.with(getApplicationContext()).load(model.getImage_url()).into(viewHolder.DisplayImage);

                final String barcode = model.getBarcodeId();

                viewHolder.DetailsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String activity_name ="Favorites" ;
                        Intent intent = new Intent(FavouritesView.this, DetailsFavt.class);
                        intent.putExtra("barcode", "" + barcode);
                        intent.putExtra("activity_name",""+activity_name);
                        startActivity(intent);
                    }
                });

                viewHolder.menuBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // positiontest=viewHolder.getAdapterPosition();
                        removeItem=model.getBarcodeId().toString();
                        PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                        MenuInflater menuInflater = popup.getMenuInflater();
                        menuInflater.inflate(R.menu.menu_favts, popup.getMenu());
                        popup.setOnMenuItemClickListener(new FavouritesView.MyMenuItemClickListener());
                        popup.show();


                    }
                });

            }
        };
        firebaseRecyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showPopMenu() {


    }

    public static class ShoeDataViewHolder extends RecyclerView.ViewHolder {

        TextView ProductNameText, CategoryText, PriceText;
        ImageView DisplayImage;
        Button DetailsBtn;
        ImageButton menuBtn;

        //View view;
//Context mContext;
        public ShoeDataViewHolder(View itemView) {

            super(itemView);
            ProductNameText = (TextView) itemView.findViewById(R.id.textViewProductName);
            CategoryText = (TextView) itemView.findViewById(R.id.textViewCategory);
            PriceText = (TextView) itemView.findViewById(R.id.textViewPrice);
            DisplayImage = (ImageView) itemView.findViewById(R.id.card_background);
            menuBtn = (ImageButton) itemView.findViewById(R.id.menu_btn);
            DetailsBtn = (Button) itemView.findViewById(R.id.button_view_details);

        }


        //Toast.makeText(DisplayDetailedView.this,"The toast is in SetShoe Records",Toast.LENGTH_SHORT).show();

//


    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.remove_favt:
                    firebaseDatabase.orderByChild("BarcodeId").equalTo(removeItem).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            dataSnapshot.getRef().child("isFavt").setValue("false");
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
//                    firebaseDatabase.notify();
                    firebaseDatabase.keepSynced(true);
                  //  firebaseRecyclerAdapter.getRef(positiontest).removeValue();


                    // Toast.makeText(mContext,"Item ID is " +menuItem.getItemId(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Remove From Favts", Toast.LENGTH_LONG).show();
                    return true;
                default:
            }
            return false;
        }
    }



}
