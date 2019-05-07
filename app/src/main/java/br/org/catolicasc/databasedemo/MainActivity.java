package br.org.catolicasc.databasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvBooks = findViewById(R.id.lvBook);

        DAL dal = new DAL(this);
        Cursor cursor = dal.loadAll();

        String[] fields = new String[] {CreateDatabase.ID, CreateDatabase.TITLE};
        int[] ids = {R.id.tvId, R.id.tvTitle};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,
                R.layout.book_layout, cursor, fields, ids, 0);

        lvBooks.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });
    }
}
