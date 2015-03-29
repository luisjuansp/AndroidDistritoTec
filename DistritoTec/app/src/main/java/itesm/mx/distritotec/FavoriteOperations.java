package itesm.mx.distritotec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardo on 29/03/15.
 */
public class FavoriteOperations {
    private SQLiteDatabase db;
    private Helper dbHelper;

    public FavoriteOperations(Context context) {
        dbHelper = new Helper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void addRuta(Ruta ruta) {
        ContentValues values = new ContentValues();
        values.put("tipo", ruta.getTipo());
        values.put("nombre", ruta.getNombre());
        values.put("inicio", ruta.getInicio());
        values.put("horario", ruta.getHorario());

        db.insert("tableRutas", null, values);
    }

    public boolean deleteRuta(String rutaName) {
        boolean result = false;

        db.isOpen();

        String query = "SELECT * FROM tableRutas WHERE nombre = \""+ rutaName +"\"";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            db.delete("tableRutas", "_id" + " = ?",
                    new String[] {String.valueOf(id) });
            cursor.close();
            result = true;
        }
        return result;
    }

    public Ruta findRuta(String rutaName) {
        String query = "SELECT * FROM tableRutas WHERE nombre = \""+ rutaName +"\"";

        Cursor cursor = db.rawQuery(query, null);

        Ruta ruta;

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            ruta = new Ruta (cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));
            cursor.close();
        } else {
            ruta = null;
        }
        return ruta;
    }

    public List<Ruta> getAllRutas() {
        List<Ruta> listaRutas = new ArrayList<Ruta>();

        String selectQuery = "SELECT * FROM tableRutas";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Ruta ruta = new Ruta (cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                listaRutas.add(ruta);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return listaRutas;
    }
}
