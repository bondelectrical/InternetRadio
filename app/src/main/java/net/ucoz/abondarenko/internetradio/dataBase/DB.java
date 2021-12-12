package net.ucoz.abondarenko.internetradio.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB {

    private Context context;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DB(Context context) {
        this.context = context;
    }

    public void openDB() {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void closeDB() {
        if(dbHelper != null) {
            dbHelper.close();
        }
    }

    public Cursor getAllDataDB() {
        return sqLiteDatabase.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getItemDataDB(long id) {
        String where = "_id = " + Long.toString(id);
        return sqLiteDatabase.query(DBHelper.TABLE_NAME, null, where, null, null, null, null);
    }

    public void addRecDB(String nameStation, String linkStation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_NAME, nameStation);
        contentValues.put(DBHelper.COLUMN_RADIO_LINK, linkStation);
        sqLiteDatabase.insert(DBHelper.TABLE_NAME, null, contentValues);
    }

    public  void delRecDB(long id){
        sqLiteDatabase.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID + " = " + id, null);
    }
}
