package itesm.mx.distritotec;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Random;


public class WaitingLocation extends ActionBarActivity {

    Firebase myFirebaseRef;
    int cont;
    double newLat;
    double newLon;
    String newLatLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_location);

        Bundle ex = getIntent().getExtras();
        cont=ex.getInt("counter");


        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://blistering-inferno-2546.firebaseio.com/");
        myFirebaseRef.child("request").setValue(cont);
        myFirebaseRef.child("response").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(!snapshot.getValue().toString().equals("0")){
                    newLatLon=snapshot.getValue().toString();
                    String[] separate = newLatLon.split(",");
                    newLat = Double.parseDouble(separate[0]);
                    newLon = Double.parseDouble(separate[1]);
                    Intent intentToMaps = new Intent(getApplicationContext(), MapsActivity.class);
                    intentToMaps.putExtra("newLat", newLat);
                    intentToMaps.putExtra("newLon", newLon);
                    startActivity(intentToMaps);
                }
            }
            @Override public void onCancelled(FirebaseError error) { }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_waiting_location, menu);
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
