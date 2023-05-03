package com.example.zeeshanhussain.qwicklyapppart1.ARreco;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zeeshanhussain.qwicklyapppart1.R;


public class ShoeOverlayView extends RelativeLayout {

    Context context;

    public ShoeOverlayView(Context context){
        //super(context,null);
        this(context,null);
    }

    public ShoeOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShoeOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
      //  inflateLayout(context);
    }
/*

    private void inflateLayout(Context context) {

    }
*/
// Set barcodeID
public void SetShoeBarcodeId(String barcodeID){
   TextView barcode=(TextView)findViewById(R.id.Barcode_text_AR);
   barcode.setText(barcodeID);
}
//Setmaterial
public void SetShoeMaterial (String material) {
    TextView tv = (TextView)findViewById(R.id.Material_text_AR);
   tv.setText(material);
}
// Set Category
public void SetShoeCategory(String category) {
    TextView tvs =(TextView) findViewById(R.id.Category_text_AR);
    tvs.setText(category);
}

}
