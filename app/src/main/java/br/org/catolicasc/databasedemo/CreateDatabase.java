package br.org.catolicasc.databasedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "library.db";
    public static final String ID = "_id";
    public static final String TABLE = "book";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String PUBLISHER = "publisher";

    private static final int VERSION = 1;

    public CreateDatabase(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " ( "
                + "_id integer primary key autoincrement, " +
                "title text, " +
                "author text, " +
                "publisher text" +
        ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
