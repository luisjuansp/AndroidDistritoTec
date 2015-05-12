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
import com.google.android.gms.location.LocationRequest;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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
public class PathGoogleMapActivity extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    private static final LatLng ITESM = new LatLng(25.652540, -100.291099);
    private static final LatLng waypoint2 = new LatLng(25.652630,-100.390000);
    private static final LatLng waypoint4 = new LatLng(25.665073, -100.374904);
    private static final LatLng waypoint5 = new LatLng(25.663995, -100.358060);
    private static final LatLng waypoint6 = new LatLng(25.661362, -100.359020);
    private static final LatLng waypoint7 = new LatLng(25.660986, -100.352261);
    private static final LatLng waypoint8 = new LatLng(25.658578, -100.345427);
    private static final LatLng waypoint9 = new LatLng(25.643855, -100.326458);
    private static final LatLng waypoint10 = new LatLng(25.613854, -100.273715);
    private static final LatLng waypoint11 = new LatLng(25.654238, -100.357560);

    //coordenadas San Nicolas
    private static final LatLng waypointSN = new LatLng(25.769070, -100.272682);
    private static final LatLng waypointSN2 = new LatLng(25.750626, -100.269391);
    private static final LatLng waypointSN4 = new LatLng(25.744673, -100.290677);
    private static final LatLng waypointSN3 = new LatLng(25.742225, -100.274716);
    private static final LatLng waypointSN5 = new LatLng(25.740172, -100.302408);
    private static final LatLng waypointSN6 = new LatLng(25.749301, -100.299494);
    private static final LatLng waypointSN7 = new LatLng(25.742387, -100.304157);
    private static final LatLng waypointSN8 = new LatLng(25.723599, -100.317097);
    private static final LatLng waypointSN9 = new LatLng(25.665815, -100.299899);

    //coordenadas san jemo
    private static final LatLng waypointSJ = new LatLng(25.695419, -100.372241);
    private static final LatLng waypointSJ2 = new LatLng(25.686937, -100.369802);
    private static final LatLng waypointSJ3 = new LatLng(25.681281, -100.370944);
    private static final LatLng waypointSJ4 = new LatLng(25.680800, -100.366253);
    private static final LatLng waypointSJ5 = new LatLng(25.680810, -100.361586);




    double newLat;
    double newLon;
    Integer cont = 1;
    Marker chofer;
    Firebase myFirebaseRef;
    String route;
    String idStudent;
    String waypoints;
    String origin;
    Location tequito = new Location("tequito");

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
            cont= extras.getInt("counter");
            route = extras.getString("route");
            idStudent = extras.getString("idStudent");
        }
        tequito.setLatitude(25.652540);
        tequito.setLongitude(-100.291099);

        Log.i("This is what i want", String.valueOf(newLat));



        setUpMapIfNeeded();
        buildGoogleApiClient();
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMap = fm.getMap();

        if(route.equals("Valle1")){
            waypoints = "waypoints=optimize:true|"
                    + waypoint4.latitude + "," + waypoint4.longitude
                    + "|" + waypoint5.latitude + "," + waypoint5.longitude
                    + "|" + waypoint6.latitude + "," + waypoint6.longitude
                    + "|" + waypoint7.latitude + "," + waypoint7.longitude
                    + "|" + waypoint8.latitude + "," + waypoint8.longitude
                    + "|" + waypoint9.latitude + "," + waypoint9.longitude
                    + "|" + waypoint10.latitude + "," + waypoint10.longitude
                    + "|" + waypoint11.latitude + "," + waypoint11.longitude;
            origin = "origin=" + waypoint2.latitude + ","+ waypoint2.longitude +"&";
            String url = getMapsApiDirectionsUrl();
            ReadTask downloadTask = new ReadTask();
            downloadTask.execute(url);
            addMarkersValle();
        }
        if(route.equals("Cumbres")){
            waypoints = "waypoints=optimize:true|"
                    + waypointSJ2.latitude + "," + waypointSJ2.longitude
                    + "|" + waypointSJ3.latitude + "," + waypointSJ3.longitude
                    + "|" + waypointSJ4.latitude + "," + waypointSJ4.longitude
                    + "|" + waypointSJ5.latitude + "," + waypointSJ5.longitude;
            origin = "origin=" + waypointSJ.latitude + ","+ waypointSJ.longitude +"&";
            String url = getMapsApiDirectionsUrl();
            ReadTask downloadTask = new ReadTask();
            downloadTask.execute(url);
            addMarkersSJ();
        }
        if(route.equals("San Nicolas")){
            waypoints = "waypoints=optimize:true|"
                    + waypointSN2.latitude + "," + waypointSN2.longitude
                    + "|" + waypointSN3.latitude + "," + waypointSN3.longitude
                    + "|" + waypointSN4.latitude + "," + waypointSN4.longitude
                    + "|" + waypointSN5.latitude + "," + waypointSN5.longitude
                    + "|" + waypointSN6.latitude + "," + waypointSN6.longitude
                    + "|" + waypointSN7.latitude + "," + waypointSN7.longitude
                    + "|" + waypointSN8.latitude + "," + waypointSN8.longitude
                    + "|" + waypointSN9.latitude + "," + waypointSN9.longitude;
            origin = "origin=" + waypointSN.latitude + ","+ waypointSN.longitude +"&";
            String url = getMapsApiDirectionsUrl();
            ReadTask downloadTask = new ReadTask();
            downloadTask.execute(url);
            addMarkersSN();
        }
        if(route.equals("Circuito1")){
            Polygon polygon = mMap.addPolygon(new PolygonOptions().add(new LatLng(25.647992, -100.290054),
                    new LatLng(25.658749, -100.296910),
                    new LatLng(25.662669, -100.297189),
                    new LatLng(25.660204, -100.282449),
                    new LatLng(25.650757, -100.275678),
                    new LatLng(25.652061, -100.280232),
                    new LatLng(25.653013, -100.284140)).strokeColor(Color.BLUE));
            origin = "origin=" + waypointSN.latitude + ","+ waypointSN.longitude +"&";
            String url = getMapsApiDirectionsUrl();
            ReadTask downloadTask = new ReadTask();
            downloadTask.execute(url);
        }
        if(route.equals("Circuito2")){
            Polygon polygon = mMap.addPolygon(new PolygonOptions().add(new LatLng(25.647992, -100.290054),
                    new LatLng(25.659586, -100.297650),
                    new LatLng(25.659301, -100.298438),
                    new LatLng(25.656679, -100.299164),
                    new LatLng(25.657196, -100.301956),
                    new LatLng(25.651319, -100.303181),
                    new LatLng(25.643021, -100.301745)).strokeColor(Color.GREEN));
            origin = "origin=" + waypointSN.latitude + ","+ waypointSN.longitude +"&";
            String url = getMapsApiDirectionsUrl();
            ReadTask downloadTask = new ReadTask();
            downloadTask.execute(url);
        }
        if(route.equals("Circuito3")){
            Polygon polygon = mMap.addPolygon(new PolygonOptions().add(new LatLng(25.644269, -100.297250),
                    new LatLng(25.653008, -100.284150),
                    new LatLng(25.652260, -100.280403),
                    new LatLng(25.648701, -100.279752),
                    new LatLng(25.650681, -100.275514),
                    new LatLng(25.643054, -100.274043),
                    new LatLng(25.635106, -100.291086)).strokeColor(Color.RED));
            origin = "origin=" + waypointSN.latitude + ","+ waypointSN.longitude +"&";
            String url = getMapsApiDirectionsUrl();
            ReadTask downloadTask = new ReadTask();
            downloadTask.execute(url);
        }


        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://blistering-inferno-2546.firebaseio.com/");

        


    }
    private String getMapsApiDirectionsUrl() {
        String sensor = "&sensor=false";

        String url = "https://maps.googleapis.com/maps/api/directions/json?";

        String destination = "destination="+ITESM.latitude + "," + ITESM.longitude + "&";
        url = url + origin + destination +waypoints+ sensor;
        return url;
    }

    private void addMarkersValle() {
        if (mMap != null) {
            mMap.addMarker(new MarkerOptions().position(ITESM)
                    .title("Fin"));
            mMap.addMarker(new MarkerOptions().position(waypoint2)
                    .title("Inicio"));
            mMap.setMyLocationEnabled(true);

        }
    }
    private void addMarkersSJ() {
        if (mMap != null) {
            mMap.addMarker(new MarkerOptions().position(ITESM)
                    .title("Fin"));
            mMap.addMarker(new MarkerOptions().position(waypointSJ)
                    .title("Inicio"));
            mMap.setMyLocationEnabled(true);

        }
    }
    private void addMarkersSN() {
        if (mMap != null) {
            mMap.addMarker(new MarkerOptions().position(ITESM)
                    .title("Fin"));
            mMap.addMarker(new MarkerOptions().position(waypointSN)
                    .title("Inicio"));
            mMap.setMyLocationEnabled(true);

        }
    }


    private class ReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                HttpConnection http = new HttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }
    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(4);
                polyLineOptions.color(Color.BLUE);
            }

            if(route.equals("Valle1")|| route.equals("Cumbres") || route.equals("San Nicolas")){
                mMap.addPolyline(polyLineOptions);
            }


            new Task().execute(0);
        }
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
        mMap.animateCamera(CameraUpdateFactory.zoomTo(21), 10000, null);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),17));
        chofer = mMap.addMarker((new MarkerOptions()
                .position(new LatLng(lat, lon))
                .title("Camion")));
        myFirebaseRef.child(route).child(idStudent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (!(snapshot.getValue().toString().equals("1")) && !(snapshot.getValue().toString().equals("2"))){
                        Toast.makeText(getApplicationContext(), "Recieving new coordinates", Toast.LENGTH_SHORT).show();
                        String newLatLon = snapshot.getValue().toString();
                        String[] separate = newLatLon.split(",");
                        newLat = Double.parseDouble(separate[0]);
                        newLon = Double.parseDouble(separate[1]);
                        chofer.setPosition(new LatLng(newLat, newLon));



                    }
                }

            }
            @Override public void onCancelled(FirebaseError error) { }
        });
    }

    class Task extends  AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {
            while (mGoogleApiClient.isConnected()) {
                publishProgress(0);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mGoogleApiClient.isConnected()) {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                myFirebaseRef.child(route).child(idStudent).setValue("2");
                Log.i("Sent Request Value ", cont.toString() + ": " + newLat + ", " + newLon);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent();
        setResult(Activity.RESULT_OK, backIntent);
        Log.i("PRESSING BACK","IN MAPS ACTIVITY");
        Toast.makeText(getApplicationContext(), "PRESSING BACK", Toast.LENGTH_SHORT).show();
        finish();
    }
}

