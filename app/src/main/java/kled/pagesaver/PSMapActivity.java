package kled.pagesaver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class PSMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String PLACE_MARKER_MODE = "marker mode";
    public static final String MAP_MODE = "map mode";
    public static final String LOCATIONS_LIST = "locations list";
    public static final String BOOKS_LIST = "books list";
    public static final String VIEW_ALL_ENTRIES = "view all entries";
    public static final String VIEW_SINGLE_ENTRY = "view single entry";
    public static final String LAT_KEY = "latitude_bundle_key";
    public static final String LNG_KEY = "longitude_bundle_key";

    MyTrackingService myTrackingService;
    private Intent mServiceIntent;
    private boolean isPlaceMarkerMode;
    Marker currMarker;
    AlertDialog alert;


    private boolean doRedraw;
    private boolean isBound;
    ArrayList<LatLng> savedLocations;
    ArrayList<String> booksAtLocation;
    private double curLat;
    private double curLong;
    ArrayList<int[]> list;


    private ServiceConnection mServiceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            MyTrackingService.TrackingServiceBinder binder = (MyTrackingService.TrackingServiceBinder) service;
            myTrackingService = binder.getService();
        }


        public void onServiceDisconnected(ComponentName name) {
            myTrackingService = null;
        }
    };

    private LocationUpdateReceiver myLocationReceiver;

    public class LocationUpdateReceiver extends BroadcastReceiver {
        //Receive broadcast that new location data has been addded
        @Override
        public void onReceive(Context ctx, Intent intent) {
            Log.d("PSMapsActivity", "OnReceive Called");
            //Get the intent from the Tracking Service and set the current Longitude/Latitude
            if (intent != null) {
                if (intent.getExtras() != null) {
                    Bundle extras = intent.getExtras();
                    curLat = (double) extras.get("lat");
                    curLong = (double) extras.get("long");
                    //Toast.makeText(getApplicationContext(), "Lat: " + String.valueOf(curLat) + " Long: " + String.valueOf(curLong),Toast.LENGTH_SHORT).show();
                }
            }
            //Redraw the UI
            if (doRedraw) {
                doRedraw = false;
                redrawUI();
            }
        }
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MAP", "oncreate called");
        setContentView(R.layout.activity_psmap);


        //Initialize arrays for storage of locations and the corresponding books
        if (savedLocations == null) {
            savedLocations = new ArrayList<LatLng>();
        }
        if (booksAtLocation == null) {
            booksAtLocation = new ArrayList<String>();
        }

        //Async setup of Map
        setUpMapIfNeeded();

        //Get the intent in order to determine what map mode we are in
        Intent mapIntent = getIntent();
        Bundle extras = mapIntent.getExtras();

        //Connect the receiver for collecting location updates
        myLocationReceiver = new LocationUpdateReceiver();

        if (extras != null) {

            //If in Place Marker Mode set the isPlaceMarkerMode boolean as true
            if (extras.get(MAP_MODE).equals(PLACE_MARKER_MODE)) {
                isPlaceMarkerMode = true;

            }
            //If in view all entries mode, get the saved locations and their corresponding books for viewing
            if (extras.get(MAP_MODE).equals(VIEW_ALL_ENTRIES)) {
                isPlaceMarkerMode = false;

                byte[] byteArray = extras.getByteArray(LOCATIONS_LIST);
                setLocationListFromByteArray(byteArray, savedLocations);
                booksAtLocation = extras.getStringArrayList(BOOKS_LIST);

            }
            //If in single entry mode, get the location list of a single book with the corresponding information about the location
            //i.e. How long you read, how many pages etc...
            if (extras.get(MAP_MODE).equals(VIEW_SINGLE_ENTRY)) {
                isPlaceMarkerMode = false;
                setLocationListFromByteArray(extras.getByteArray(LOCATIONS_LIST), savedLocations);
                booksAtLocation = extras.getStringArrayList(BOOKS_LIST);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        //If in Place Marker Mode, set up to map click listener and dialog box
        if (isPlaceMarkerMode) {
            startTrackingService();
            redrawUI();
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    savedLocations.add(latLng);
                    currMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_book)));

                    curLat = latLng.latitude;
                    curLong = latLng.longitude;
                    drawDialogBox();
                }
            });
        }
        //Else we are in one of the view entry modes therefore just draw the stored locations
        else {
            redrawUI();

        }

    }

    //Function that starts the tracking service
    private void startTrackingService() {
        isBound = true;
        mServiceIntent = new Intent(this, MyTrackingService.class);
        //mServiceIntent.putExtra(MainActivity.SAVED_ACTIVITY, activityType);
        startService(mServiceIntent);
        bindService(mServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

    }

    //Unregister the location receiver if the app is paused
    //Unbind the service if the app is paused
    @Override
    protected void onPause() {
        if (isPlaceMarkerMode) {
            unregisterReceiver(myLocationReceiver);
        }
        if (isBound) {
            //unbind service
            unbindService(mServiceConnection);
            isBound = false;
        }

        super.onPause();
    }

    //Function that stops that unbinds the tracking service
    private void stopTrackingService() {
        if (myTrackingService != null) {
            if (isBound) {
                //unbind service
                unbindService(mServiceConnection);
                isBound = false;
            }
            stopService(mServiceIntent);
        }
    }

    //Function that redraws the UI depending on the mode we are in
    public void redrawUI() {
        //If in Place Marker Mode, zoom to current location for ease of placing a marker near you
        if (isPlaceMarkerMode) {
            if (mMap != null) {
                Log.d("MapActivity", "Lat: " + String.valueOf(curLat) + " Long: " + String.valueOf(curLong));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(curLat, curLong))
                        .zoom(17)
                        .bearing(0)
                        .tilt(45)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
        //Else we are in one of the view entry modes therefore set markers for locations on the maps
        else {
            int count = 0;

            for (LatLng l : savedLocations) {
                mMap.addMarker(new MarkerOptions().position(l).title(booksAtLocation.get(count))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_book)));
                count++;
            }
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(savedLocations.get(savedLocations.size() - 1).latitude, savedLocations.get(savedLocations.size() - 1).longitude))
                    .zoom(13)
                    .bearing(0)
                    .tilt(45)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(savedLocations.get(savedLocations.size()-1)));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }
    }

    //When the application returns if we are in Place Marker Mode, register the receiver
    protected void onResume() {
        super.onResume();
        doRedraw = true;
        setUpMapIfNeeded();


        // register the receiver for receiving the location update broadcast
        if (isPlaceMarkerMode) {
            IntentFilter intentFilter = new IntentFilter(LocationUpdateReceiver.class.getName());
            registerReceiver(myLocationReceiver, intentFilter);
        }
    }

    public void drawDialogBox() {
        alert = new AlertDialog.Builder(PSMapActivity.this)
                .setTitle("Latitude: " + curLat + " Longitude: " + curLong)
                .setMessage("Would you like to save this location?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent();
                        Bundle extras = new Bundle();
                        extras.putDouble(LAT_KEY, curLat);
                        extras.putDouble(LNG_KEY, curLong);
                        data.putExtras(extras);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        currMarker.remove();
                        alert.dismiss();

                    }
                }).show();
    }


    //Stop tracking if we leave the application from a back press
    @Override
    public void onBackPressed() {
        stopTrackingService();
        super.onBackPressed();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        //Restore the image when the activity returns from the background
        super.onRestoreInstanceState(outState);

    }

    //Initialize the map if it is currently null
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }


    public void setLocationListFromByteArray(byte[] bytePointArray, ArrayList<LatLng> list) {

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytePointArray);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();

        int[] intArray = new int[bytePointArray.length / Integer.SIZE];
        intBuffer.get(intArray);

        int locationNum = intArray.length / 2;

        for (int i = 0; i < locationNum; i++) {
            LatLng latLng = new LatLng((double) intArray[i * 2] / 1E6F,
                    (double) intArray[i * 2 + 1] / 1E6F);
            list.add(latLng);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopTrackingService();
    }
}