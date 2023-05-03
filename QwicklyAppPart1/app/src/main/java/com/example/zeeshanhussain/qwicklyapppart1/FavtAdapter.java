package com.example.zeeshanhussain.qwicklyapppart1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavtAdapter extends RecyclerView.Adapter<FavtAdapter.MyViewHolder> {
private Context mContext;
private List<ShoeData> shoeDataList;

int positions;
public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView price,name,category;
    public ImageView imagedisplay,overflow;

    public MyViewHolder(View view) {
        super(view);
        price=(TextView)view.findViewById(R.id.products_price_favt);
        name=(TextView)view.findViewById(R.id.product_name_favt);
        category=(TextView)view.findViewById(R.id.product_category);
        imagedisplay=(ImageView)view.findViewById(R.id.image_display);
        overflow=(ImageView)view.findViewById(R.id.overflow_favt);

    }


}


public FavtAdapter(Context mContext,List<ShoeData> shoeDataList) {
    this.mContext=mContext;
    this.shoeDataList=shoeDataList;

}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_display,parent,false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ShoeData shoeData = shoeDataList.get(position);
        holder.price.setText(shoeData.getPrice());
        holder.name.setText(shoeData.getProductName());
        holder.category.setText(shoeData.getCategory());
        Glide.with(mContext).load(shoeData.getThumbnail()).into(holder.imagedisplay);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positions=holder.getPosition();
                showPopUpMenu(holder.overflow);
            }
        });

        //Toast.makeText(mContext,"The adapter Position is "+positions,Toast.LENGTH_LONG).show();
    }
    private void showPopUpMenu(View view) {
        PopupMenu popup = new PopupMenu(mContext,view);
        MenuInflater menuInflater= popup.getMenuInflater();
        menuInflater.inflate(R.menu.menu_favts,popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

        public MyMenuItemClickListener() {

        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.remove_favt:
                    removeAt(positions);
                   // Toast.makeText(mContext,"Item ID is " +menuItem.getItemId(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext,"Remove From Favts",Toast.LENGTH_LONG).show();
                    return true;
                    default:
            }
            return false;
                    }


}

    public void removeAt(int item) {
        shoeDataList.remove(item);
        notifyItemRemoved(item);
        notifyItemRangeChanged(item,getItemCount());


    }


    @Override
    public int getItemCount() {
        return shoeDataList.size();
    }
}
