package com.example.project_ver1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Tworzenie lokalnej bazy danych SQLite do listy zakupow
public class ShopListDB extends SQLiteOpenHelper {

    //Prywatne zmienne potrzebne do stworzenia bazy danych: nazwa bazy, nazwa tabeli, wersja bazy
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shoppinglistdb";
    private static final String DATABASE_TABLE = "listtable";

    //Nazwy kolumn w tabeli
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    ShopListDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tworzenie tabeli w bazie danych
        String query = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID + " INT PRIMARY KEY, " +
                KEY_TITLE + " TEXT, " +
                KEY_CONTENT + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_TIME + " TEXT" + ")";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion){
            return;
        }
        else{
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

    }

    // Dodawanie nowej listy do bazy danych ze wszystkimi potrzebnymi elementami
    public long addList(ShoppingList shoppingList){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(KEY_TITLE, shoppingList.getTitle());
        content.put(KEY_CONTENT, shoppingList.getContent());
        content.put(KEY_DATE, shoppingList.getDate());
        content.put(KEY_TIME, shoppingList.getTime());

        long ID = db.insert(DATABASE_TABLE, null, content);
        return ID;

    }
}
