package itesm.mx.distritotec;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


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
