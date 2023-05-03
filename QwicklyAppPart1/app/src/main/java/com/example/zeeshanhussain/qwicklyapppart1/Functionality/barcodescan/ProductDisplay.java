
package com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeeshanhussain.qwicklyapppart1.ARreco.AboutScreen;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.CloudReco;
import com.example.zeeshanhussain.qwicklyapppart1.R;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;


public class ProductDisplay extends Activity implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView barcodeValue;

    String barcodeTxt;

    Bundle extras;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        extras=getIntent().getExtras();
        statusMessage = (TextView)findViewById(R.id.status_message);
        barcodeValue = (TextView)findViewById(R.id.barcode_value);
//activities= getIntent().getStringExtra("ACTIVITY_TO_LAUNCH");
        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);


        findViewById(R.id.read_barcode).setOnClickListener(this);
//     findViewById(R.id.button_start).setOnClickListener(this);
 //       findViewById(R.id.view_details_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan.BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
/*
        if(v.getId() == R.id.view_details_btn) {

            barcodeTxt= barcodeValue.getText().toString();
            //barcodeTxt= "9781402222634";
         if(barcodeTxt.isEmpty()) {
             Toast.makeText(this,"Please scan barcode first",Toast.LENGTH_LONG).show();
         }
         else {

             if(barcodeTxt.equals("5045235225328")) {
                 Intent intent = new Intent(ProductDisplay.this,Display2.class);
                 intent.putExtra("barcode",barcodeTxt);
                 startActivity(intent);

             }
             else if(barcodeTxt.equals("9780241972939")){
                 Intent intent = new Intent(ProductDisplay.this,Display2.class);
                 intent.putExtra("barcode",barcodeTxt);
                 startActivity(intent);




             }
             else if(barcodeTxt.equals("5045235225328")){
                 Intent intent = new Intent(ProductDisplay.this,Display2.class);
                 intent.putExtra("barcode",barcodeTxt);
                 startActivity(intent);




             }
             else if(barcodeTxt.equals("028828520531")){
                 Intent intent = new Intent(ProductDisplay.this,Display2.class);
                 intent.putExtra("barcode",barcodeTxt);
                 startActivity(intent);




             }

             else if(barcodeTxt.equals("9780582094840")){
                 Intent intent = new Intent(ProductDisplay.this,Display2.class);
                 intent.putExtra("barcode",barcodeTxt);
                 startActivity(intent);




             }

             else {
                 Toast.makeText(getApplicationContext(),"Sorry the "+barcodeTxt+"doesnot match our detabase",Toast.LENGTH_LONG).show();

             }

             // Display details activity
             
         }
        }
        */
/*
        if(v.getId()==R.id.button_start) {



        startARActivity();
        }
*/
    }

    private void startARActivity() {

        Intent intent = new Intent(ProductDisplay.this,CloudReco.class);
        startActivity(intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);
                    autoFocus.setVisibility(View.INVISIBLE);
                    useFlash.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
