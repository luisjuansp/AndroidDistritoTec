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
