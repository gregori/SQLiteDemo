package br.org.catolicasc.databasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvBooks = findViewById(R.id.lvBook);

        DAL dal = new DAL(this);
        Cursor cursor = dal.loadAll();

        String[] fields = new String[] {CreateDatabase.ID, CreateDatabase.TITLE};
        int[] ids = {R.id.tvId, R.id.tvTitle};

        //Log.d(TAG, "onCreate: id " + cursor.getInt(0) + " title " + cursor.getString(1));
        Log.d(TAG, "onCreate: " + cursor.getCount());
        adapter = new SimpleCursorAdapter(MainActivity.this,
                R.layout.book_layout, cursor, fields, ids, 0);

        lvBooks.setAdapter(adapter);

        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(MainActivity.this, ViewUpdateActivity.class);
               intent.putExtra(CreateDatabase.ID,
                       Integer.valueOf(((TextView)view.findViewById(R.id.tvId)).getText().toString()));
               startActivity(intent);
            }
        });


        Button btnAdd = findViewById(R.id.btnUpdate);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, InsertActivity.class);
               startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
