package itesm.mx.distritotec;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
public class MainActivity extends ActionBarActivity {

    TextView titleTV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button expresoBT = (Button) findViewById(R.id.expresoBT);
        Button circuitoBT = (Button) findViewById(R.id.circuitoBT);
        Button favBT = (Button) findViewById(R.id.favBT);


        View.OnClickListener registro = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.expresoBT:
                        Intent intentExp = new Intent(MainActivity.this, Rutas_act.class);
                        intentExp.putExtra("tipo","expreso");

                        startActivity(intentExp);
                        break;
                    case R.id.circuitoBT:
                        Intent intentCir = new Intent(MainActivity.this, Rutas_act.class);
                        intentCir.putExtra("tipo","circuito");
                        startActivity(intentCir);
                        break;
                    case R.id.favBT:
                        Intent intentFav = new Intent(MainActivity.this, Fav_Act.class);
                        startActivity(intentFav);
                }
            }
        };

        expresoBT.setOnClickListener(registro);
        circuitoBT.setOnClickListener(registro);
        favBT.setOnClickListener(registro);

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
