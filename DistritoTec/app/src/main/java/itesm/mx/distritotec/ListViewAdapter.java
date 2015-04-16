package itesm.mx.distritotec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ricardo on 28/03/15.
 */
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
public class ListViewAdapter extends ArrayAdapter <Ruta> {
    private Context context;
    int layoutResourceId;
    List<Ruta> listaRutas;

    public ListViewAdapter(Context context, int idResource, List<Ruta> rutas){
        super(context, idResource, rutas);
        this.context = context;
        this.layoutResourceId = idResource;
        this.listaRutas = rutas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if(row == null){
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
        }
        TextView rowNombreTV = (TextView) row.findViewById(R.id.rowNombreTV);

        Ruta ruta = listaRutas.get(position);
        rowNombreTV.setText(ruta.getNombre());


        return row;

    }

}
