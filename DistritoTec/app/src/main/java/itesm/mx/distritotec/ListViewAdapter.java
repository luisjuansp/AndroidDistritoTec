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
