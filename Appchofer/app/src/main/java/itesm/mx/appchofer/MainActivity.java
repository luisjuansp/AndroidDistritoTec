package itesm.mx.appchofer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class MainActivity extends ActionBarActivity {
    Firebase myFirebaseRef;

    Button location;
    TextView maps;
    String reqString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://blistering-inferno-2546.firebaseio.com/");

        myFirebaseRef.child("request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                reqString=snapshot.getValue().toString();


                    Intent intentExp = new Intent(MainActivity.this, MapsActivity.class);
                    startActivityForResult(intentExp,1);

            }
            @Override public void onCancelled(FirebaseError error) { }
        });

        location = (Button)findViewById(R.id.button);
        maps = (TextView) findViewById(R.id.mapTV);
        View.OnClickListener registro = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentExp = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intentExp);
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
                String pos = data.getStringExtra("pos");
                myFirebaseRef.child("response").setValue(pos);
                Toast.makeText(getApplicationContext(), "sending new location", Toast.LENGTH_SHORT).show();
            }
        }
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
