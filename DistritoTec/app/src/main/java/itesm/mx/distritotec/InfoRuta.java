package itesm.mx.distritotec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
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

import java.sql.SQLException;
import java.util.Random;


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
public class InfoRuta extends ActionBarActivity {

    Firebase myFirebaseRef;
    int cont=1;

    TextView infTipoTV;
    TextView textView;
    TextView infNombreTV;
    TextView textView2;
    TextView infHorarioTV;
    TextView textView3;
    TextView infInicioTV;
    Button mapButtn;
    Button addFav;
    FavoriteOperations dao;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;

    String route;
    String idStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ENTERING METHOD", "INFORUTA ONCREATE");
        Random randomGenerator = new Random();
        idStudent = Integer.toString(randomGenerator.nextInt(10000));

        Random randomGenerator2 = new Random();
        cont = randomGenerator2.nextInt(100);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ruta);

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://blistering-inferno-2546.firebaseio.com/");

        /*myFirebaseRef.child("response").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {

          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {

          }
        });*/

        dao = new FavoriteOperations(this);
        Bundle extras = getIntent().getExtras();
        infTipoTV = (TextView) findViewById(R.id.infTipoTV);
        infNombreTV = (TextView) findViewById(R.id.infNombreTV);
        infHorarioTV = (TextView) findViewById(R.id.infHorarioTV);
        infInicioTV = (TextView) findViewById(R.id.infInicioTV);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
        mapButtn = (Button)findViewById(R.id.mapBT);
        addFav = (Button) findViewById(R.id.button);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


        infTipoTV.setText(extras.getString("tipo"));
        infNombreTV.setText(extras.getString("nombre"));
        infHorarioTV.setText(extras.getString("horario"));
        infInicioTV.setText(extras.getString("inicio"));

        route = extras.getString("nombre");
        Log.i("RUTA NAME" ,route);

        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();

        mapButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoRuta.this, WaitingLocation.class);
                intent.putExtra("counter", cont++);
                intent.putExtra("route", route);
                intent.putExtra("idStudent", idStudent);
                startActivityForResult(intent,1);
            }
        });

        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRuta();
            }
        });
    }

    public void newRuta(){
        Ruta ruta = new Ruta(infTipoTV.getText().toString(), infNombreTV.getText().toString(), infInicioTV.getText().toString(), infHorarioTV.getText().toString());
        Ruta rutaAux = dao.findRuta(infNombreTV.getText().toString());
        if (rutaAux==null){
            dao.addRuta(ruta);
            Toast.makeText(getApplicationContext(), "Agregando ruta", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Ruta agregada anteriormente", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        Log.i("ENTERING METHOD", "INFORUTA ONRESUME");

        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cont++;
        Random randomGenerator = new Random();
        idStudent = Integer.toString(randomGenerator.nextInt(10000));

        super.onResume();


    }



    @Override
    protected void onPause() {
        Log.i("ENTERING METHOD", "INFORUTA ONPAUSE");

        dao.close();
        super.onPause();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //if (requestCode == 1) {
        if(resultCode == RESULT_OK){
            Log.i("ENTERING METHOD", "INFORUTA ON ACTIVITY RESULT");
            Firebase del = new Firebase("https://blistering-inferno-2546.firebaseio.com/" + route + "/" + idStudent);
            del.removeValue();
            finish();
            Log.i("WAITING LOCATION", "THE BRANCH SHOULD ALREADY BE DELETED (onActivityResult)");
        }
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_ruta, menu);
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
