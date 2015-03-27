package itesm.mx.distritotec;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by John on 26/03/2015.
 */
public class Helper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_RUTA = "CREATE TABLE Ruta (RutaID INTEGER, Tipo INTEGER, Favorite INTEGER);";
        String CREATE_TABLE_PARADA = "CREATE TABLE Parada(ParaID INTEGER, Latitud REAL, Longitud REAL);";
        String CREATE_TABLE_UNIDAD = "CREATE TABLE Unidad(UnidadID INTEGER, Latitud REAL, Longitud REAL, idRuta INTEGER);";
        String CREATE_TABLE_PARADA_RUTA = "CREATE TABLE ParadaRuta(idParada INTEGER, idRuta INTEGER);";
        db.execSQL(CREATE_TABLE_RUTA);
        db.execSQL(CREATE_TABLE_PARADA);
        db.execSQL(CREATE_TABLE_UNIDAD);
        db.execSQL(CREATE_TABLE_PARADA_RUTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Ruta");
        db.execSQL("DROP TABLE IF EXISTS Parada");
        db.execSQL("DROP TABLE IF EXISTS Unidad");
        db.execSQL("DROP TABLE IF EXISTS ParadaRuta");
        onCreate(db);
    }
}
