package itesm.mx.distritotec;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class WaitingLocation extends ActionBarActivity {

    Firebase myFirebaseRef;
    int cont;
    String route;
    String idStudent;
    double newLat;
    double newLon;
    String newLatLon;
    boolean DeleteStudent;
    ValueEventListener mapListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_location);

        Bundle ex = getIntent().getExtras();
        cont=ex.getInt("counter");
        route = ex.getString("route");
        idStudent = ex.getString("idStudent");
        DeleteStudent = false;

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://blistering-inferno-2546.firebaseio.com/");

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(!DeleteStudent) {
                            Toast.makeText(getApplicationContext(), "No se pudo conectar con la base de datos",Toast.LENGTH_SHORT).show();
                            Log.i("tag", "This'll run 300 milliseconds later");
                        }
                    }
                },
                10000);

        myFirebaseRef.child(route).child(idStudent).setValue("1");


        mapListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if ((!dataSnapshot.getValue().toString().equals("1"))){
                        newLatLon = dataSnapshot.getValue().toString();
                        String[] separate = newLatLon.split(",");
                        newLat = Double.parseDouble(separate[0]);
                        newLon = Double.parseDouble(separate[1]);
                        Intent intentToMaps = new Intent(getApplicationContext(), PathGoogleMapActivity.class);
                        intentToMaps.putExtra("newLat", newLat);
                        intentToMaps.putExtra("newLon", newLon);
                        intentToMaps.putExtra("counter", cont++);
                        intentToMaps.putExtra("route", route);
                        intentToMaps.putExtra("idStudent", idStudent);
                        DeleteStudent = true;
                        startActivityForResult(intentToMaps,1);
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        myFirebaseRef.child(route).child(idStudent).addValueEventListener(mapListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(DeleteStudent){
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
            if(resultCode == RESULT_OK){
                Log.i("ENTERING METHOD", "WAITINGLOCATION ON ACTIVITY RESULT");
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                Log.i("GOING BACK","IN WAITINGLOCATION ACTIVITY");
                finish();
            }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause(){
        super.onPause();
        myFirebaseRef.child(route).child(idStudent).removeEventListener(mapListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_waiting_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
