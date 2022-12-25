package com.example.project_ver1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public ShopListDB(Context context){
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

    public ShoppingList getList(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_DATE, KEY_TIME}, KEY_ID + "=?",
        new String[]{String.valueOf(id)}, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        ShoppingList shoppingList = new ShoppingList(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return  shoppingList;
    }

    public List<ShoppingList> getLists(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<ShoppingList> allLists = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
//                ShoppingList shoppingList = new ShoppingList();
//                shoppingList.setID(cursor.getLong(0));
//                shoppingList.setTitle(cursor.getString(1));
//                shoppingList.setContent(cursor.getString(2));
//                shoppingList.setDate(cursor.getString(3));
//                shoppingList.setTime(cursor.getString(4));
//                allLists.add(shoppingList);

                long id = cursor.getLong(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                String date = cursor.getString(3);
                String time = cursor.getString(4);
                ShoppingList shoppingList = new ShoppingList(id, title, content, date, time);
                allLists.add(shoppingList);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allLists;
    }

}
