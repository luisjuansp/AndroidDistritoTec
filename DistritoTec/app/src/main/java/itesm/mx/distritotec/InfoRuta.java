package itesm.mx.distritotec;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ruta);

        dao = new FavoriteOperations(this);
        Bundle extras = getIntent().getExtras();
        infTipoTV = (TextView) findViewById(R.id.infTipoTV);
        infNombreTV = (TextView) findViewById(R.id.infNombreTV);
        infHorarioTV = (TextView) findViewById(R.id.infHorarioTV);
        infInicioTV = (TextView) findViewById(R.id.infInicioTV);

        mapButtn = (Button)findViewById(R.id.mapBT);
        addFav = (Button) findViewById(R.id.button);

        infTipoTV.setText(extras.getString("tipo"));
        infNombreTV.setText(extras.getString("nombre"));
        infHorarioTV.setText(extras.getString("horario"));
        infInicioTV.setText(extras.getString("inicio"));

        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();

        mapButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoRuta.this, MapsActivity.class);
                startActivity(intent);
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
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
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
