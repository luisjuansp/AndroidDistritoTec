package itesm.mx.appchofer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Button location;
    TextView maps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location = (Button)findViewById(R.id.button);
        maps = (TextView) findViewById(R.id.mapTV);
        View.OnClickListener registro = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Manda a pedir la pocision
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * v
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * v
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * v
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * Manda a pedir la pocision
                * v
                * Manda a pedir la pocision
                * */
                Intent intentExp = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intentExp);
            }
        };
        location.setOnClickListener(registro);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        * OBTIENE POSICION
        * OBTIENE POSICION
        *
        * OBTIENE POSICION
        *
        * OBTIENE POSICION
        * OBTIENE POSICION
        * OBTIENE POSICION
        * OBTIENE POSICION
        *
        * OBTIENE POSICION
        * OBTIENE POSICION
        * OBTIENE POSICION
        *
        * OBTIENE POSICION
        * OBTIENE POSICION
        * OBTIENE POSICION
        * */
        Intent intent = getIntent();
        String pos = intent.getStringExtra("pos");
        maps.setText(pos);
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
