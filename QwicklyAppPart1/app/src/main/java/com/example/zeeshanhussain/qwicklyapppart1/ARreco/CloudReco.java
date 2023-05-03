/*===============================================================================
Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of QUALCOMM Incorporated, registered in the United States 
and other countries. Trademarks of QUALCOMM Incorporated are used with permission.
===============================================================================*/

package com.example.zeeshanhussain.qwicklyapppart1.ARreco;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan.BarcodeCaptureActivity;
import com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan.Display2;
import com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan.DisplayDetailedView;
import com.example.zeeshanhussain.qwicklyapppart1.R;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.qualcomm.vuforia.CameraDevice;
import com.qualcomm.vuforia.ObjectTracker;
import com.qualcomm.vuforia.State;
import com.qualcomm.vuforia.TargetFinder;
import com.qualcomm.vuforia.TargetSearchResult;
import com.qualcomm.vuforia.Trackable;
import com.qualcomm.vuforia.Tracker;
import com.qualcomm.vuforia.TrackerManager;
import com.qualcomm.vuforia.Vuforia;




import com.example.zeeshanhussain.qwicklyapppart1.ARreco.SampleApplicationControl;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.SampleApplicationException;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.SampleApplicationSession;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.LoadingDialogHandler;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.SampleApplicationGLView;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.Texture;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.SampleAppMenu;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.SampleAppMenuGroup;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.SampleAppMenuInterface;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.Shoes;
import com.example.zeeshanhussain.qwicklyapppart1.ARreco.ShoeOverlayView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.khronos.opengles.GL10;


// The main activity for the CloudReco sample. 
public class CloudReco extends Activity implements SampleApplicationControl,
    SampleAppMenuInterface
{
    private static final String LOGTAG = "CloudReco";
    
   SampleApplicationSession vuforiaAppSession;

    private Shoes shoes;

    private ShoeOverlayView shoeOverlayView;

    private Button display_btn;

    String barcode,material,category;
    // These codes match the ones defined in TargetFinder in Vuforia.jar
    static final int INIT_SUCCESS = 2;
    static final int INIT_ERROR_NO_NETWORK_CONNECTION = -1;
    static final int INIT_ERROR_SERVICE_NOT_AVAILABLE = -2;
    static final int UPDATE_ERROR_AUTHORIZATION_FAILED = -1;
    static final int UPDATE_ERROR_PROJECT_SUSPENDED = -2;
    static final int UPDATE_ERROR_NO_NETWORK_CONNECTION = -3;
    static final int UPDATE_ERROR_SERVICE_NOT_AVAILABLE = -4;
    static final int UPDATE_ERROR_BAD_FRAME_QUALITY = -5;
    static final int UPDATE_ERROR_UPDATE_SDK = -6;
    static final int UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE = -7;
    static final int UPDATE_ERROR_REQUEST_TIMEOUT = -8;
    
    static final int HIDE_LOADING_DIALOG = 0;
    static final int SHOW_LOADING_DIALOG = 1;
    
    // Our OpenGL view:
    private SampleApplicationGLView mGlView;
    
    // Our renderer:
    private CloudRecoRenderer mRenderer;
    
    private SampleAppMenu mSampleAppMenu;
    DatabaseReference firebaseDatabase;
    private boolean mExtendedTracking = false;
    boolean mFinderStarted = false;
    boolean mStopFinderIfStarted = false;
  // private TextView textView;
    // The textures we will use for rendering:
    private Vector<Texture> mTextures;
    
    private static final String kAccessKey = "39d6b97187b42b53b962665bb811e0c24c3d498f";
    private static final String kSecretKey = "5b6978c6ca9aec60295441572721613f1681c54f";
    
    // View overlays to be displayed in the Augmented View
    private RelativeLayout mUILayout;
    
    // Error message handling:
    private int mlastErrorCode = 0;
    private int mInitErrorCode = 0;
    private boolean mFinishActivityOnError;
    
    // Alert Dialog used to display SDK errors
    private AlertDialog mErrorDialog;
    private LoadingDialogHandler loadingDialogHandler = new LoadingDialogHandler(
            this);
    private GestureDetector mGestureDetector;
    TextView Baroode,Material,Category;

    
    private double mLastErrorTime;


    String barcodeRead;

    
    // Called when the activity first starts or needs to be recreated after
    // resuming the application or a configuration change.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        barcodeRead=getIntent().getStringExtra("Barcode");
        //Toast.makeText(this,"Barcode is "+barcodeRead,Toast.LENGTH_LONG).show();

//      Baroode.setText(barcodeRead.displayValue);


     //   Toast.makeText(this,"in On Create ",Toast.LENGTH_LONG).show();
        shoes= new Shoes();
       // Toast.makeText(this,"Barcode is "+ barcodeRead,Toast.LENGTH_LONG).show();
//        textView.setText("OnCreate Started");
        Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);
        
        vuforiaAppSession = new SampleApplicationSession(CloudReco.this
        );
        firebaseDatabase= FirebaseDatabase.getInstance().getReference("Product");
        //Toast.makeText(this,"App Session Created ",Toast.LENGTH_LONG).show();
        startLoadingAnimation();
        
        vuforiaAppSession
            .initAR(this, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        // Creates the GestureDetector listener for processing double tap
        mGestureDetector = new GestureDetector(this, new GestureListener());
        
        mTextures = new Vector<Texture>();
       loadTextures();
        

        
    }
    
    // Process Single Tap event to trigger autofocus
    private class GestureListener extends
        GestureDetector.SimpleOnGestureListener
    {
        // Used to set autofocus one second after a manual focus is triggered
        private final Handler autofocusHandler = new Handler();
        
        
        @Override
        public boolean onDown(MotionEvent e)
        {
            return true;
        }
        
        
        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
//textView.setText("in Gesture Detector Class on SingleTapUp");
            // Generates a Handler to trigger autofocus
            // after 1 second
            autofocusHandler.postDelayed(new Runnable()
            {
                public void run()
                {
                    boolean result = CameraDevice.getInstance().setFocusMode(
                        CameraDevice.FOCUS_MODE.FOCUS_MODE_TRIGGERAUTO);
                    
                    if (!result)
                        Log.e("SingleTapUp", "Unable to trigger focus");
                }
            }, 1000L);
            
            return true;
        }
    }
    
    
    // We want to load specific textures from the APK, which we will later use
    // for rendering.
    private void loadTextures()
    {
      //  textView.setText("Cup Load Thi wayo");
       // mTextures.add(Texture.loadTextureFromApk("TextureTeapotRed.png",
         //   getAssets()));
    }
    
    
    // Called when the activity will start interacting with the user.
    @Override
    protected void onResume()
    {
        Log.d(LOGTAG, "onResume");
        super.onResume();
        
        // This is needed for some Droid devices to force portrait


        
        try
        {
            vuforiaAppSession.resumeAR();
        } catch (SampleApplicationException e)
        {

            Log.e(LOGTAG, e.getString());
        }
        
        // Resume the GL view:
        if (mGlView != null)
        {
            mGlView.setVisibility(View.VISIBLE);
            mGlView.onResume();
        }
        
    }
    
    
    // Callback for configuration changes the activity handles itself
    @Override
    public void onConfigurationChanged(Configuration config)
    {
        Log.d(LOGTAG, "onConfigurationChanged");
        super.onConfigurationChanged(config);
        
        vuforiaAppSession.onConfigurationChanged();
    }
    
    
    // Called when the system is about to start resuming a previous activity.
    @Override
    protected void onPause()
    {
        Log.d(LOGTAG, "onPause");
        super.onPause();
        
        try
        {
            vuforiaAppSession.pauseAR();
        } catch (SampleApplicationException e)
        {
            Log.e(LOGTAG, e.getString());
        }
        
        // Pauses the OpenGLView
        if (mGlView != null)
        {
            mGlView.setVisibility(View.INVISIBLE);
            mGlView.onPause();
        }
    }
    
    
    // The final call you receive before your activity is destroyed.
    @Override
    protected void onDestroy()
    {
        Log.d(LOGTAG, "onDestroy");
        super.onDestroy();
        
        try
        {
            vuforiaAppSession.stopAR();
        } catch (SampleApplicationException e)
        {
            Log.e(LOGTAG, e.getString());
        }
        
        System.gc();
    }
    
    
    public void deinitCloudReco()
    {
        // Get the object tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager
            .getTracker(ObjectTracker.getClassType());
        if (objectTracker == null)
        {
            Log.e(LOGTAG,
                "Failed to destroy the tracking data set because the ObjectTracker has not"
                    + " been initialized.");
            return;
        }
        
        // Deinitialize target finder:
        TargetFinder finder = objectTracker.getTargetFinder();
        finder.deinit();
    }
    
    

    private void startLoadingAnimation()
    {
        // Inflates the Overlay Layout to be displayed above the Camera View
        LayoutInflater inflater = LayoutInflater.from(this);
        mUILayout = (RelativeLayout) inflater.inflate(R.layout.camera_overlay,
            null, false);
        
        mUILayout.setVisibility(View.VISIBLE);
        mUILayout.setBackgroundColor(Color.BLACK);
        //Toast.makeText(CloudReco.this,"Inflated",Toast.LENGTH_LONG).show();
        //textView=(TextView)mUILayout.findViewById(R.id.test_text);
        Baroode=(TextView)mUILayout.findViewById(R.id.Barcode_text_AR);
        Material=(TextView)mUILayout.findViewById(R.id.Material_text_AR);
        Category=(TextView)mUILayout.findViewById(R.id.Category_text_AR);
        display_btn=(Button)mUILayout.findViewById(R.id.DetailsButton);
        display_btn.setVisibility(View.INVISIBLE);
        firebaseDatabase=FirebaseDatabase.getInstance().getReference("Product");
        display_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseDatabase.orderByChild("BarcodeId").equalTo(barcodeRead).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //String barcodeId=dataSnapshot.child("BarcodeId").getValue().toString();

                            Intent intent = new Intent(CloudReco.this, DisplayDetailedView.class);
                            intent.putExtra("barcode", "" + barcodeRead);
                            String calling = "Main";
                            intent.putExtra("activity_name", "" + calling);
                            startActivity(intent);

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


               /* if(barcodeRead.equals("10171872")) {
                  Intent intent = new Intent(CloudReco.this, DisplayDetailedView.class);
                  intent.putExtra("barcode", "" + barcodeRead);
                  String calling="Main";
                  intent.putExtra("activity_name",""+calling);
                  startActivity(intent);
              }
              else if(barcodeRead.equals("18FMD235-032-BLK")){
                  Intent intent = new Intent(CloudReco.this, DisplayDetailedView.class);
                  intent.putExtra("barcode", "" + barcodeRead);
                  String calling="Main";
                  intent.putExtra("activity_name",""+calling);
                  startActivity(intent);

              }

              else if(barcodeRead.equals("8050038000000")){

                  Intent intent = new Intent(CloudReco.this, DisplayDetailedView.class);
                  intent.putExtra("barcode", "" + barcodeRead);
                  String calling="Main";
                  intent.putExtra("activity_name",""+calling);
                  startActivity(intent);


              }

              else if (barcodeRead.equals("879")){
                  Intent intent = new Intent(CloudReco.this, DisplayDetailedView.class);
                  intent.putExtra("barcode", "" + barcodeRead);
                  String calling="Main";
                  intent.putExtra("activity_name",""+calling);
                  startActivity(intent);



              }
              else {
                  Toast.makeText(CloudReco.this,"Sorry but "+barcodeRead+" doesnot match our database",Toast.LENGTH_LONG).show();
              }

                */

            }
        });


        loadingDialogHandler.mLoadingDialogContainer = mUILayout
                .findViewById(R.id.loading_indicator);
        loadingDialogHandler.mLoadingDialogContainer
                .setVisibility(View.VISIBLE);

//        textView.setText("Loading Animation");

        addContentView(mUILayout, new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));

      //  Toast.makeText(CloudReco.this,"PARAMS SET",Toast.LENGTH_LONG).show();
    }


    private void showToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    // Initializes AR application components.
    private void initApplicationAR()
    {

    //    textView.setText("in method initApplicationAR ");
        // Create OpenGL ES view:
        int depthSize = 16;
        int stencilSize = 0;
        boolean translucent = Vuforia.requiresAlpha();
        
        // Initialize the GLView with proper flags
        mGlView = new SampleApplicationGLView(this);
        mGlView.init(translucent, depthSize, stencilSize);
        
        // Setups the Renderer of the GLView
        mRenderer = new CloudRecoRenderer(vuforiaAppSession,this);
        mRenderer.setTextures(mTextures);
        mGlView.setRenderer(mRenderer);
        
    }
    
    
    // Returns the error message for each error code
    private String getStatusDescString(int code)
    {
        if (code == UPDATE_ERROR_AUTHORIZATION_FAILED)
            return getString(R.string.UPDATE_ERROR_AUTHORIZATION_FAILED_DESC);
        if (code == UPDATE_ERROR_PROJECT_SUSPENDED)
            return getString(R.string.UPDATE_ERROR_PROJECT_SUSPENDED_DESC);
        if (code == UPDATE_ERROR_NO_NETWORK_CONNECTION)
            return getString(R.string.UPDATE_ERROR_NO_NETWORK_CONNECTION_DESC);
        if (code == UPDATE_ERROR_SERVICE_NOT_AVAILABLE)
            return getString(R.string.UPDATE_ERROR_SERVICE_NOT_AVAILABLE_DESC);
        if (code == UPDATE_ERROR_UPDATE_SDK)
            return getString(R.string.UPDATE_ERROR_UPDATE_SDK_DESC);
        if (code == UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE)
            return getString(R.string.UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE_DESC);
        if (code == UPDATE_ERROR_REQUEST_TIMEOUT)
            return getString(R.string.UPDATE_ERROR_REQUEST_TIMEOUT_DESC);
        if (code == UPDATE_ERROR_BAD_FRAME_QUALITY)
            return getString(R.string.UPDATE_ERROR_BAD_FRAME_QUALITY_DESC);
        else
        {
            return getString(R.string.UPDATE_ERROR_UNKNOWN_DESC);
        }
    }
    
    
    // Returns the error message for each error code
    private String getStatusTitleString(int code)
    {
        if (code == UPDATE_ERROR_AUTHORIZATION_FAILED)
            return getString(R.string.UPDATE_ERROR_AUTHORIZATION_FAILED_TITLE);
        if (code == UPDATE_ERROR_PROJECT_SUSPENDED)
            return getString(R.string.UPDATE_ERROR_PROJECT_SUSPENDED_TITLE);
        if (code == UPDATE_ERROR_NO_NETWORK_CONNECTION)
            return getString(R.string.UPDATE_ERROR_NO_NETWORK_CONNECTION_TITLE);
        if (code == UPDATE_ERROR_SERVICE_NOT_AVAILABLE)
            return getString(R.string.UPDATE_ERROR_SERVICE_NOT_AVAILABLE_TITLE);
        if (code == UPDATE_ERROR_UPDATE_SDK)
            return getString(R.string.UPDATE_ERROR_UPDATE_SDK_TITLE);
        if (code == UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE)
            return getString(R.string.UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE_TITLE);
        if (code == UPDATE_ERROR_REQUEST_TIMEOUT)
            return getString(R.string.UPDATE_ERROR_REQUEST_TIMEOUT_TITLE);
        if (code == UPDATE_ERROR_BAD_FRAME_QUALITY)
            return getString(R.string.UPDATE_ERROR_BAD_FRAME_QUALITY_TITLE);
        else
        {
            return getString(R.string.UPDATE_ERROR_UNKNOWN_TITLE);
        }
    }
    
    
    // Shows error messages as System dialogs
    public void showErrorMessage(int errorCode, double errorTime, boolean finishActivityOnError)
    {
        if (errorTime < (mLastErrorTime + 5.0) || errorCode == mlastErrorCode)
            return;
        
        mlastErrorCode = errorCode;
        mFinishActivityOnError = finishActivityOnError;
        
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                if (mErrorDialog != null)
                {
                    mErrorDialog.dismiss();
                }
                
                // Generates an Alert Dialog to show the error message
                AlertDialog.Builder builder = new AlertDialog.Builder(
                    CloudReco.this);
                builder
                    .setMessage(
                        getStatusDescString(CloudReco.this.mlastErrorCode))
                    .setTitle(
                        getStatusTitleString(CloudReco.this.mlastErrorCode))
                    .setCancelable(false)
                    .setIcon(0)
                    .setPositiveButton(getString(R.string.button_OK),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                if(mFinishActivityOnError)
                                {
                                    finish();
                                }
                                else
                                {
                                    dialog.dismiss();
                                }
                            }
                        });
                
                mErrorDialog = builder.create();
                mErrorDialog.show();
            }
        });
    }
    
    
    // Shows initialization error messages as System dialogs
    public void showInitializationErrorMessage(String message)
    {
        final String errorMessage = message;
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                if (mErrorDialog != null)
                {
                    mErrorDialog.dismiss();
                }
                
                // Generates an Alert Dialog to show the error message
                AlertDialog.Builder builder = new AlertDialog.Builder(
                    CloudReco.this);
                builder
                    .setMessage(errorMessage)
                    .setTitle(getString(R.string.INIT_ERROR))
                    .setCancelable(false)
                    .setIcon(0)
                    .setPositiveButton(getString(R.string.button_OK),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                finish();
                            }
                        });
                
                mErrorDialog = builder.create();
                mErrorDialog.show();
            }
        });
    }
    
    
    public void startFinderIfStopped()
    {
//        textView.setText("in Method startFinderIfStopped");
        if(!mFinderStarted)
        {
            mFinderStarted = true;
            
            // Get the object tracker:
            TrackerManager trackerManager = TrackerManager.getInstance();
            ObjectTracker objectTracker = (ObjectTracker) trackerManager
                .getTracker(ObjectTracker.getClassType());
            
            // Initialize target finder:
            TargetFinder targetFinder = objectTracker.getTargetFinder();
            
            targetFinder.clearTrackables();
            targetFinder.startRecognition();
        }
    }
    
    
    public void stopFinderIfStarted()
    {
        if(mFinderStarted)
        {
            mFinderStarted = false;
            
            // Get the object tracker:
            TrackerManager trackerManager = TrackerManager.getInstance();
            ObjectTracker objectTracker = (ObjectTracker) trackerManager
                .getTracker(ObjectTracker.getClassType());
            
            // Initialize target finder:
            TargetFinder targetFinder = objectTracker.getTargetFinder();
            
            targetFinder.stop();
        }
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event)

    {
    //    textView.setText("on Touch Event");
        // Process the Gestures
        if (mSampleAppMenu != null && mSampleAppMenu.processEvent(event))
            return true;
        
        return mGestureDetector.onTouchEvent(event);
    }
    
    
    @Override
    public boolean doLoadTrackersData()
    {
           Log.d(LOGTAG, "Tracckers Data initCloudReco");
        
        // Get the object tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager
            .getTracker(ObjectTracker.getClassType());
        
        // Initialize target finder:
        TargetFinder targetFinder = objectTracker.getTargetFinder();
        
        // Start initialization:
        if (targetFinder.startInit(kAccessKey, kSecretKey))
        {
            targetFinder.waitUntilInitFinished();
        }
        
        int resultCode = targetFinder.getInitState();
        if (resultCode != TargetFinder.INIT_SUCCESS)
        {
            if(resultCode == TargetFinder.INIT_ERROR_NO_NETWORK_CONNECTION)
            {
                mInitErrorCode = UPDATE_ERROR_NO_NETWORK_CONNECTION;
            }
            else
            {
                mInitErrorCode = UPDATE_ERROR_SERVICE_NOT_AVAILABLE;
            }
                
            Log.e(LOGTAG, "Failed to initialize target finder.");
            return false;
        }
        
        // Use the following calls if you would like to customize the color of
        // the UI
        // targetFinder->setUIScanlineColor(1.0, 0.0, 0.0);
        // targetFinder->setUIPointColor(0.0, 0.0, 1.0);
        
        return true;
    }
    

    @Override
    public boolean doUnloadTrackersData()
    {
        return true;
    }
    
    
    @Override
    public void onInitARDone(SampleApplicationException exception)
    {
    //    textView.setText("AR done");
        
        if (exception == null)
        {
            initApplicationAR();
            
            // Now add the GL surface view. It is important
            // that the OpenGL ES surface view gets added
            // BEFORE the camera is started and video
            // background is configured.
            addContentView(mGlView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
            
            // Start the camera:
            try
            {
                vuforiaAppSession.startAR(CameraDevice.CAMERA.CAMERA_DEFAULT);
            } catch (SampleApplicationException e)
            {
                Toast.makeText(this,"Unable to load camera",Toast.LENGTH_LONG).show();
                Log.e(LOGTAG, e.getString());
            }
            
            boolean result = CameraDevice.getInstance().setFocusMode(
                CameraDevice.FOCUS_MODE.FOCUS_MODE_CONTINUOUSAUTO);
            
            if (!result)
                Log.e(LOGTAG, "Unable to enable continuous autofocus");
            
            mUILayout.bringToFront();
            
            // Hides the Loading Dialog

            loadingDialogHandler.sendEmptyMessage(HIDE_LOADING_DIALOG);
            mUILayout.setBackgroundColor(Color.TRANSPARENT);
            
            mSampleAppMenu = new SampleAppMenu(this, this, "Cloud Reco",
                mGlView, mUILayout, null);
            setSampleAppMenuSettings();
            
        } else
        {
            Log.e(LOGTAG, exception.getString());
            if(mInitErrorCode != 0)
            {
                showErrorMessage(mInitErrorCode,10, true);
            }
            else
            {
                showInitializationErrorMessage(exception.getString());
            }
        }
    }
    
    
    @Override
    public void onQCARUpdate(State state) throws JSONException {
     //   textView.setText("QCAR update mein aa gaye");
        // Get the tracker manager:
        TrackerManager trackerManager = TrackerManager.getInstance();
        
        // Get the object tracker:
        ObjectTracker objectTracker = (ObjectTracker) trackerManager
            .getTracker(ObjectTracker.getClassType());
        
        // Get the target finder:
        TargetFinder finder = objectTracker.getTargetFinder();
        
        // Check if there are new results available:
        final int statusCode = finder.updateSearchResults();
        
        // Show a message if we encountered an error:
        if (statusCode < 0)
        {
            
            boolean closeAppAfterError = (
                statusCode == UPDATE_ERROR_NO_NETWORK_CONNECTION ||
                statusCode == UPDATE_ERROR_SERVICE_NOT_AVAILABLE);
            
            showErrorMessage(statusCode, state.getFrame().getTimeStamp(), closeAppAfterError);
            
        } else if (statusCode == TargetFinder.UPDATE_RESULTS_AVAILABLE)
        {
            // Process new search results
            if (finder.getResultCount() > 0)
            {

                TargetSearchResult result = finder.getResult(0);
                String metadata = result.getMetaData();
              // woeking code
                //JSONArray jsonArray = new JSONArray(metadata);
                //JSONObject jsonObject = jsonArray.getJSONObject(0);
          //  JSONObject jsonObject = new JSONObject(metadata);

                //display_btn.setVisibility(View.VISIBLE);
               // mUILayout.setVisibility(View.VISIBLE);
                JSONObject reader = new JSONObject(metadata);
                JSONObject jsonObject = reader.getJSONObject("Shoe");


                barcode = jsonObject.getString("Price");
                category = jsonObject.getString("Category");
                material=jsonObject.getString("Material");
                //JSONObject jsonObject = new JSONObject(metadata);
           // JSONArray jsonArray = jsonObject.getJSONArray("Shoe2");

            //Baroode.setText(jsonArray.length());


  //              JSONArray jsonArray = new JSONArray(metadata);
    //            JSONObject jsonObject = jsonArray.getJSONObject(0);
               // String uniqueID= result.getUniqueTargetId();
                //Baroode.setText(uniqueID);
                //JSONArray jsonArray = jsonObject.getJSONArray(jsonObject.keys().toArray()[0].toString());

                //JSONArray jsonArray = jsonObject.getJSONArray();

//              JSONArray jsonArray= new JSONArray(metadata);


                   // JSONObject rec = jsonArray.getJSONObject(1);
               //     barcode = jsonArray.getJSONObject(0).getString("BarcodeId");
                 //   material=jsonArray.getJSONObject(0).getString("Material");
                   // category=jsonArray.getJSONObject(0).getString("Category");





               //barcode = jsonObject.getJSONArray("Shoe2").
            //   material= jsonObject.getJSONObject("Shoe2").getString("Material");
               // material=jsonObject.getJSONObject("Shoe2").getString("Material");
                //category=jsonObject.getJSONObject("Shoe2").getString("Category");
                ////Material.setText(metadata);

                Baroode.setText("Price: " +barcode);
                Material.setText("Material: " +material);
                Category.setText("Category: " +category);

                Baroode.setVisibility(View.VISIBLE);
                Material.setVisibility(View.VISIBLE);
                Category.setVisibility(View.VISIBLE);

               display_btn.setVisibility(View.VISIBLE);
                //shoes.setBarcodeID(jsonObject.getString("BarcodeId"));
                //shoes.setCategory(jsonObject.getString("Category"));
                //shoes.setMaterial(jsonObject.getString("Material"));
              mUILayout.bringToFront();
              mUILayout.setVisibility(View.VISIBLE);

              mGlView.clearAnimation();
              //doStopTrackers();// Aditional code


                // Check if this target is suitable for tracking:
                if (result.getTrackingRating() > 0)
                {
                    Trackable trackable = finder.enableTracking(result);
                    
                    if (mExtendedTracking)
                        trackable.startExtendedTracking();
                }
            }
        } 
    }
    
    
    @Override
    public boolean doInitTrackers()
    {
        TrackerManager tManager = TrackerManager.getInstance();
        Tracker tracker;
        
        // Indicate if the trackers were initialized correctly
        boolean result = true;
        
        tracker = tManager.initTracker(ObjectTracker.getClassType());
        if (tracker == null)
        {
            Log.e(
                LOGTAG,
                "Tracker not initialized. Tracker already initialized or the camera is already started");
            result = false;
        } else
        {
            Log.i(LOGTAG, "Tracker successfully initialized");
        }
        
        return result;
    }
    
    
    @Override
    public boolean doStartTrackers()
    {
       // textView.setText(" in method doStartTrackers");
        // Indicate if the trackers were started correctly
        boolean result = true;
        
        // Start the tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager
            .getTracker(ObjectTracker.getClassType());
        objectTracker.start();
        
        // Start cloud based recognition if we are in scanning mode:
        TargetFinder targetFinder = objectTracker.getTargetFinder();
        targetFinder.startRecognition();
        mFinderStarted = true;
        
        return result;
    }
    
    
    @Override
    public boolean doStopTrackers()
    {
        // Indicate if the trackers were stopped correctly
        boolean result = true;
        
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager
            .getTracker(ObjectTracker.getClassType());
        
        if(objectTracker != null)
        {
            objectTracker.stop();
            
            // Stop cloud based recognition:
            TargetFinder targetFinder = objectTracker.getTargetFinder();
            targetFinder.stop();
            mFinderStarted = false;
            
            // Clears the trackables
            targetFinder.clearTrackables();
        }
        else
        {
            result = false;
        }
        
        return result;
    }
    
    
    @Override
    public boolean doDeinitTrackers()
    {
        // Indicate if the trackers were deinitialized correctly
        boolean result = true;
        
        TrackerManager tManager = TrackerManager.getInstance();
        tManager.deinitTracker(ObjectTracker.getClassType());
        
        return result;
    }
    
    final public static int CMD_BACK = -1;
    final public static int CMD_EXTENDED_TRACKING = 1;
    
    // This method sets the menu's settings
    private void setSampleAppMenuSettings()
    {
        SampleAppMenuGroup group;
        
        group = mSampleAppMenu.addGroup("", false);
        group.addTextItem(getString(R.string.menu_back), -1);
        
        group = mSampleAppMenu.addGroup("", true);
        group.addSelectionItem(getString(R.string.menu_extended_tracking),
            CMD_EXTENDED_TRACKING, false);
        
        mSampleAppMenu.attachMenu();
    }
    
    
    @Override
    public boolean menuProcess(int command)
    {
        boolean result = true;
        
        switch (command)
        {
            case CMD_BACK:
                finish();
                break;
            
            case CMD_EXTENDED_TRACKING:
                TrackerManager trackerManager = TrackerManager.getInstance();
                ObjectTracker objectTracker = (ObjectTracker) trackerManager
                    .getTracker(ObjectTracker.getClassType());
                
                TargetFinder targetFinder = objectTracker.getTargetFinder();
                
                if (targetFinder.getNumImageTargets() == 0)
                {
                    result = true;
                }
                
                for (int tIdx = 0; tIdx < targetFinder.getNumImageTargets(); tIdx++)
                {
                    Trackable trackable = targetFinder.getImageTarget(tIdx);
                    
                    if (!mExtendedTracking)
                    {
                        if (!trackable.startExtendedTracking())
                        {
                            Log.e(LOGTAG,
                                "Failed to start extended tracking target");
                            result = false;
                        } else
                        {
                            Log.d(LOGTAG,
                                "Successfully started extended tracking target");
                        }
                    } else
                    {
                        if (!trackable.stopExtendedTracking())
                        {
                            Log.e(LOGTAG,
                                "Failed to stop extended tracking target");
                            result = false;
                        } else
                        {
                            Log.d(LOGTAG,
                                "Successfully started extended tracking target");
                        }
                    }
                }
                
                if (result)
                    mExtendedTracking = !mExtendedTracking;
                
                break;
            
        }
        
        return result;
    }
    
}
