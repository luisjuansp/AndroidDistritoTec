package itesm.mx.distritotec;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
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


public class Fav_Act extends ActionBarActivity {

    TextView textview4;
    ListView favLV;
    FavoriteOperations dao;
    List<Ruta> aux;
    ListViewAdapter miAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        favLV = (ListView) findViewById(R.id.favLV);
        dao = new FavoriteOperations(this);

        aux = new ArrayList<Ruta>();
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showRutas();

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ruta rutaLista = (Ruta) miAdaptador.getItem(position);


                Intent intent = new Intent(Fav_Act.this, InfoRuta.class);
                intent.putExtra("nombre",rutaLista.getNombre());
                intent.putExtra("horario",rutaLista.getHorario());
                intent.putExtra("inicio",rutaLista.getInicio());
                intent.putExtra("tipo",rutaLista.getTipo());
                startActivity(intent);

            }
        };

        AdapterView.OnItemLongClickListener itemLongListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> argad0, View argad1, int argad2, long argad3) {



                final int pos = argad2;
                final Ruta rutaLista = (Ruta) miAdaptador.getItem(pos);


                AlertDialog.Builder alert = new AlertDialog.Builder(Fav_Act.this);
                alert.setTitle("Eliminar");
                alert.setMessage("Confirmar borrado?");

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(), "Borrado ", Toast.LENGTH_SHORT).show();
                        removeRuta(rutaLista.getNombre());
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                return true;
            }
        };

        favLV.setOnItemClickListener(itemListener);
        favLV.setOnItemLongClickListener(itemLongListener);

    }

    public void showRutas() {
        aux = dao.getAllRutas();
        miAdaptador = new ListViewAdapter(this,R.layout.row,aux);
        favLV.setAdapter(miAdaptador);
    }

    public void removeRuta(String name){

        boolean result = dao.deleteRuta(name);

        if (result) {

        } else {
            Toast.makeText(getApplicationContext(), "No Match Found", Toast.LENGTH_SHORT).show();

        }

        showRutas();
    }



    @Override
    protected void onResume() {
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showRutas();
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
        getMenuInflater().inflate(R.menu.menu_fav, menu);
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
