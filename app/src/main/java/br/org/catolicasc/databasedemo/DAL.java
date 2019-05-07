package br.org.catolicasc.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.zip.CRC32;

public class DAL {
    private static final String TAG = "DAL";

    private SQLiteDatabase db;
    private CreateDatabase database;

    public DAL(Context context) {
        database = new CreateDatabase(context);
    }

    public boolean insert(String title, String author, String publisher) {
        ContentValues values;
        long result;

        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put(CreateDatabase.TITLE, title);
        values.put(CreateDatabase.AUTHOR, author);
        values.put(CreateDatabase.PUBLISHER, publisher);

        result = db.insert(CreateDatabase.TABLE, null, values);
        db.close();


        if (result == -1) {
            Log.e(TAG, "insert: Erro inserindo registro");
            return false;
        }

        return true;
    }

    public Cursor loadAll() {
        Cursor cursor;
        String [] fields = {CreateDatabase.ID, CreateDatabase.TITLE};
        db = database.getReadableDatabase();
        cursor = db.query(CreateDatabase.TABLE, fields, null, null, null, null,
                null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }
}
