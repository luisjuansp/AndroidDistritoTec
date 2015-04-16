package itesm.mx.distritotec;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

public class Helper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    //nombre de base de datos: distritoDB.db
    //tabla de rutas: tableRutas
    //rutas columnas: _id, tipo, nombre, inicio, horario

    public Helper(Context context) {

        super(context, "distritoDB.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_RUTA = "CREATE TABLE tableRutas (_id INTEGER PRIMARY KEY, tipo TEXT, nombre TEXT, inicio TEXT, horario TEXT)";

        db.execSQL(CREATE_TABLE_RUTA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS tableRutas");

        onCreate(db);
    }
}
