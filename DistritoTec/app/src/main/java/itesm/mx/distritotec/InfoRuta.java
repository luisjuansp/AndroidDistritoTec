package itesm.mx.distritotec;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class InfoRuta extends ActionBarActivity {

    TextView infTipoTV;
    TextView textView;
    TextView infNombreTV;
    TextView textView2;
    TextView infHorarioTV;
    TextView textView3;
    TextView infInicioTV;
    Button mapButtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ruta);

        Bundle extras = getIntent().getExtras();
        infTipoTV = (TextView) findViewById(R.id.infTipoTV);
        infNombreTV = (TextView) findViewById(R.id.infNombreTV);
        infHorarioTV = (TextView) findViewById(R.id.infHorarioTV);
        infInicioTV = (TextView) findViewById(R.id.infInicioTV);

        mapButtn = (Button)findViewById(R.id.mapBT);

        infTipoTV.setText(extras.getString("tipo"));
        infNombreTV.setText(extras.getString("nombre"));
        infHorarioTV.setText(extras.getString("horario"));
        infInicioTV.setText(extras.getString("inicio"));

        mapButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoRuta.this, MapsActivity.class);
                startActivity(intent);
            }
        });
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
