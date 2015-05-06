package itesm.mx.appchofer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class MainActivity extends ActionBarActivity {
    LocationManager mlocManager=null;
    LocationListener mlocListener;
    String position;
    ChildEventListener childAdded;

    Firebase myFirebaseRef;

    Button location;
    TextView maps;
    String reqString;
    String selectedRoute;
    String idStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle ext = getIntent().getExtras();
        selectedRoute = ext.getString("ruta");


        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://blistering-inferno-2546.firebaseio.com/");

        /*myFirebaseRef.child("request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                reqString=snapshot.getValue().toString();


                    Intent intentExp = new Intent(MainActivity.this, MapsActivity.class);
                    startActivityForResult(intentExp,1);

            }
            @Override public void onCancelled(FirebaseError error) { }
        });*/

        childAdded = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {

                    if (!dataSnapshot.getKey().equals("-1")) {
                        idStudent = dataSnapshot.getKey();
                        Log.i("USUARIO DEBERIA DE SER", idStudent);
                        Intent intentExp = new Intent(MainActivity.this, MapsActivity.class);
                        Log.i("AQUI AUN NO TRUENA", idStudent);
                        //startActivityForResult(intentExp, 1);

                        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                        mlocListener = new MyLocationListener();
                        mlocManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
                        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1, 0, mlocListener);
                        if (mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                            if(MyLocationListener.latitude>0)
                            {
                                position = String.valueOf(MyLocationListener.latitude) + "," + String.valueOf(MyLocationListener.longitude);
                                myFirebaseRef.child(selectedRoute).child(idStudent).setValue(position);
                                Toast.makeText(getApplicationContext(), "sending new location to " + selectedRoute, Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Wait, gps is loading", Toast.LENGTH_SHORT).show();
                            }
                        }
                        myFirebaseRef.child(selectedRoute).child(idStudent).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    if (dataSnapshot.getValue().toString().equals("2")){
                                        mlocManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
                                        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1, 0, mlocListener);
                                        if (mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                                            if(MyLocationListener.latitude>0)
                                            {
                                                position = String.valueOf(MyLocationListener.latitude) + "," + String.valueOf(MyLocationListener.longitude);
                                                myFirebaseRef.child(selectedRoute).child(idStudent).setValue(position);
                                                Toast.makeText(getApplicationContext(), "sending new location 5 sec to " + selectedRoute, Toast.LENGTH_SHORT).show();
                                                Log.i("this", "sending new loc 5 sec is " + position);

                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), "Wait, gps is loading 5 sec update", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }
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
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        myFirebaseRef.child(selectedRoute).addChildEventListener(childAdded);



        location = (Button)findViewById(R.id.button);
        maps = (TextView) findViewById(R.id.mapTV);
        View.OnClickListener registro = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Mi ruta es: " + selectedRoute, Toast.LENGTH_SHORT).show();
            }
        };
        location.setOnClickListener(registro);
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                Log.i("ENTERING RESULT", "FROM CLASS MAIN");
                String pos = data.getStringExtra("pos");
                myFirebaseRef.child(selectedRoute).child(idStudent).setValue(pos);
                Toast.makeText(getApplicationContext(), "sending new location", Toast.LENGTH_SHORT).show();
                Log.i("SENDING NEW LOCATION", pos);
            }
        }
    }

   /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            myFirebaseRef.child(selectedRoute).removeEventListener(childAdded);
            Toast.makeText(getApplicationContext(), "back pressed", Toast.LENGTH_SHORT).show();
            finish();


        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed(){
        myFirebaseRef.child(selectedRoute).removeEventListener(childAdded);
        Toast.makeText(getApplicationContext(), "back pressed", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
