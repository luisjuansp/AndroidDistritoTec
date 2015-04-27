package itesm.mx.distritotec;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


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
public class Rutas_act extends ActionBarActivity {

    TextView rutaTitleTV;
    ListView rutasLV;
    String[] nombres;
    String[] inicios;
    String[] horarios;
    String tipo;
    ListViewAdapter miAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas_act);

        rutaTitleTV = (TextView) findViewById(R.id.rutaTitleTV);
        rutasLV = (ListView) findViewById(R.id.rutasLV);

        Bundle extras = getIntent().getExtras();

        if (extras.getString("tipo").equals("expreso")){
            tipo = "Expreso";
            nombres= new String[]{"Valle1", "Cumbres", "San Nicolas"};
            inicios = new String[]{"Vasconcelos", "A. de Rodas", "Rep. Mexicana"};
            horarios = new String[]{"6:00-7:15","10:15-11:00","7:00-8:15"};

            rutaTitleTV.setText("Rutas Expreso");



        }
        else{
            tipo = "Circuito";
            nombres= new String[]{"Circuito1", "Circuito2", "Circuito3"};
            inicios = new String[]{"Lago los patos", "Estacionamiento", "Rectoría"};
            horarios = new String[]{"6:00-7:15","9:15-10:15","7:00-8:15"};

            rutaTitleTV.setText("Rutas Circuito");




        }




        miAdaptador = new ListViewAdapter(getApplicationContext(),R.layout.row,getDataForListView());
        rutasLV.setAdapter(miAdaptador);




        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ruta rutaLista = (Ruta) miAdaptador.getItem(position);


                Intent intent = new Intent(Rutas_act.this, InfoRuta.class);
                intent.putExtra("nombre",rutaLista.getNombre());
                intent.putExtra("horario",rutaLista.getHorario());
                intent.putExtra("inicio",rutaLista.getInicio());
                intent.putExtra("tipo",rutaLista.getTipo());
                startActivity(intent);

            }
        };

        rutasLV.setOnItemClickListener(itemListener);
    }

    public List<Ruta> getDataForListView(){
        Ruta ruta;

        List<Ruta> listRutas = new ArrayList<Ruta>();
        for(int i = 0; i < 3; i++) {
            ruta = new Ruta(tipo, nombres[i], inicios[i], horarios[i]);
            listRutas.add(ruta);
        }
        return listRutas;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rutas, menu);
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
