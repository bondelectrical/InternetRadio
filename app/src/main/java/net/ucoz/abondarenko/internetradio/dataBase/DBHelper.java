package net.ucoz.abondarenko.internetradio.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "InternetRadio";
    public static final String TABLE_NAME = "ListLinkInternetRadio";
    public static final String COLUMN_NAME = "name_station";
    public static final String COLUMN_RADIO_LINK = "radio_link";
    private static final String TEXT_TYPE = " TEXT NOT NULL";
    private static final String COMMA_SEP = ", ";
    public static final String COLUMN_ID = "_id";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_RADIO_LINK + TEXT_TYPE +
                    ");";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, "Ретро фм");
        contentValues.put(COLUMN_RADIO_LINK,"http://cast.radiogroup.com.ua:8000/retro");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "Авторадио");
        contentValues.put(COLUMN_RADIO_LINK,"http://cast.radiogroup.com.ua:8000/avtoradio");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "KISS FM");
        contentValues.put(COLUMN_RADIO_LINK,"http://online.kissfm.ua/KissFM");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "Радио РОКС");
        contentValues.put(COLUMN_RADIO_LINK,"http://online.radioroks.ua/RadioROKS");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "Радио Мелодия");
        contentValues.put(COLUMN_RADIO_LINK,"http://online.melodiafm.ua/MelodiaFM");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "Радио Хит-FM");
        contentValues.put(COLUMN_RADIO_LINK,"http://online.hitfm.ua/HitFM");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "Русское Радио Украина");
        contentValues.put(COLUMN_RADIO_LINK,"http://online.rusradio.ua/RusRadio");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "Relaks");
        contentValues.put(COLUMN_RADIO_LINK,"http://online.radiorelax.ua/RadioRelax");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "Супердискотека 90-х");
        contentValues.put(COLUMN_RADIO_LINK,"https://radiorecord.hostingradio.ru/sd9096.aacp");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "Premium FM");
        contentValues.put(COLUMN_RADIO_LINK,"http://listen.rpfm.ru:9000/premium128");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        contentValues.put(COLUMN_NAME, "Makradio Детский Хит");
        contentValues.put(COLUMN_RADIO_LINK,"http://stream.makradio.ru/makdeti128.mp3/title");
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
