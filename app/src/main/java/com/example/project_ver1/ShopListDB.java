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
        String query = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME};
        Cursor cursor=  db.query(DATABASE_TABLE,query,KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new ShoppingList(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }

    public List<ShoppingList> getLists(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<ShoppingList> allLists = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
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

    public int editNote(ShoppingList list){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ list.getTitle() + "\n ID -> "+list.getID());
        c.put(KEY_TITLE,list.getTitle());
        c.put(KEY_CONTENT,list.getContent());
        c.put(KEY_DATE,list.getDate());
        c.put(KEY_TIME,list.getTime());
        return db.update(DATABASE_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(list.getID())});
    }

    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

}
