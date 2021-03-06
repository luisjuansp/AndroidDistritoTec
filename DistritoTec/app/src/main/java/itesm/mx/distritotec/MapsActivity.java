package itesm.mx.distritotec;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import java.lang.Object;
import java.util.ArrayList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

/*This file is part of DistritoTec.

        DistritoTec is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        DistritoTec is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with DistritoTec.  If not, see <http://www.gnu.org/licenses/>.*/
public class MapsActivity extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    double newLat;
    double newLon;


    LatLng tec = new LatLng(25.649713, -100.290032);

    protected static final String TAG = "basic-location-sample";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            newLat = extras.getDouble("newLat");
            newLon = extras.getDouble("newLon");
        }

        Log.i("This is what i want", String.valueOf(newLat));



        setUpMapIfNeeded();
        buildGoogleApiClient();
    }



    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    protected void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {


        setLocOnMap(newLat, newLon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLat, newLon),17));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLat,newLon),17));
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    public void setLocOnMap(double lat, double lon){
        mMap.animateCamera(CameraUpdateFactory.zoomTo(21), 5000, null);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lon)));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(lat, lon))
                .title("Current Pos")));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(25.653170, -100.389912))
                .title("Dummy")));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(25.655767, -100.385106))
                .title("Dummy2")));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(25.667414, -100.379827))
                .title("Dummy3")));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(25.664958, -100.374935))
                .title("Dummy4")));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(25.663952, -100.358112))
                .title("Dummy5")));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(25.652501, -100.358198))
                .title("Dummy6")));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(25.659968, -100.349379))
                .title("Dummy7")));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(25.644222, -100.323861))
                .title("Dummy8")));
        mMap.addMarker((new MarkerOptions()
                .position(new LatLng(25.614661, -100.271144))
                .title("Dummy9")));

        Polygon polygon = mMap.addPolygon(new PolygonOptions().add(new LatLng(25.6535527,-100.2898306),
                new LatLng(25.6506706,-100.2864832),
                new LatLng(25.6480302,-100.2900237),
                new LatLng(25.6516377,-100.2922016)).strokeColor(Color.RED));



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE)) {
            Intent backIntent = new Intent();
            setResult(Activity.RESULT_OK, backIntent);
            Log.i("PRESSING BACK","IN MAPS ACTIVITY");
            finish();
            return true;

        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent();
        setResult(Activity.RESULT_OK, backIntent);
        Log.i("PRESSING BACK","IN MAPS ACTIVITY");
        finish();

    }


}

