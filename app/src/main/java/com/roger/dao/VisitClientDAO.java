package com.roger.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roger.db.DataBaseHelper;
import com.roger.Entities.VisitClient;
import com.roger.db.VisitClientTable;

import java.util.ArrayList;
import java.util.List;

public class VisitClientDAO {
    final DataBaseHelper dbHelper;
    SQLiteDatabase db;
    public VisitClientDAO(Context context) {
        dbHelper = DataBaseHelper.getInstance(context);

        this.db= dbHelper.getWritableDatabase();
    }

    public void close() {
        this.dbHelper.close();
    }

    public long Ingresar(VisitClient visitClient) {
        ContentValues cv = new ContentValues();
        cv.put(VisitClientTable.cClient , visitClient.getcClient()  );
        cv.put(VisitClientTable.cDocument , visitClient.getcDocument()  );
        cv.put(VisitClientTable.nLatitude , visitClient.getnLatitude()  );
        cv.put(VisitClientTable.nLength , visitClient.getnLength()  );

        long idG = -1;

        try {
            idG = db.insertWithOnConflict(VisitClientTable.TableName, // tabla donde se va a insertar
                    null, cv, // valores a insertar
                    SQLiteDatabase.CONFLICT_IGNORE); // si hay un problema
        }catch(Exception ex){

        }

        return idG;
    }

    public void modificar(){
        String sql = "DELETE FROM "+VisitClientTable.TableName+" WHERE "+VisitClientTable.cDocument+" = '69558800'";
        db.execSQL(sql);
    }

    public Cursor ListarVisitas(){

        String sql = "SELECT  "+VisitClientTable.cDocument+" AS _id, (" + VisitClientTable.cClient + "||' DNI: '||"+VisitClientTable.cDocument+")  AS "+VisitClientTable.cClient+", " + VisitClientTable.cDocument +", "+VisitClientTable.nLatitude+", "+VisitClientTable.nLength+", ('Latitud: '||"+VisitClientTable.nLatitude+"||' Longitud: '||"+VisitClientTable.nLength+") AS Coordenadas, "+VisitClientTable.nLatitude+", "+VisitClientTable.nLength+"   FROM "+VisitClientTable.TableName  ;
        return db.rawQuery(sql,null);
    }
    public List<VisitClient> ListarPosiciones(){
        List<VisitClient> lst = new ArrayList<VisitClient>();

        String sql = "SELECT  "+VisitClientTable.cDocument+" AS _id, " + VisitClientTable.cClient + ", " + VisitClientTable.cDocument +", "+VisitClientTable.nLatitude+", "+VisitClientTable.nLength+" FROM "+VisitClientTable.TableName  ;
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();

        for(int i=0; i<c.getCount();i++ ){
            VisitClient visitClient = new VisitClient();
            visitClient.setcDocument(c.getString(0));
            visitClient.setcClient(c.getString(1));
            visitClient.setnLatitude(c.getDouble(3) );
            visitClient.setnLength(c.getDouble(4));
            lst.add(visitClient);
            c.moveToNext();
        }
        return lst;
    }

    public void EliminarCliente(String cDNI){
        String  cSQL = "DELETE FROM "+VisitClientTable.TableName+" WHERE "+VisitClientTable.cDocument+"='"+cDNI+"'";
        db.execSQL(cSQL);
    }

    public VisitClient BuscarCliente(String cDNI){
        String sql = "SELECT  "+VisitClientTable.cDocument+" AS _id, " + VisitClientTable.cClient + ", " + VisitClientTable.cDocument + ", "+VisitClientTable.nLatitude+", "+VisitClientTable.nLength+" FROM "+VisitClientTable.TableName + " WHERE "+VisitClientTable.cDocument+ "='" + cDNI + "'" ;
        Cursor c = db.rawQuery(sql,null);

        if (c.getCount() > 0){
            c.moveToFirst();
            VisitClient clienteVisita = new VisitClient();
            clienteVisita.setcClient(c.getString(1));
            clienteVisita.setcDocument(c.getString(0));
            clienteVisita.setnLatitude(c.getDouble(3) );
            clienteVisita.setnLength(c.getDouble(4));
            return clienteVisita;
        } else{
            return null;
        }
    }

    public Cursor getTablas(){
        String sql = "SELECT sql AS _id, sql FROM sqlite_master WHERE type='table'";
        return db.rawQuery(sql,null);
    }
}
