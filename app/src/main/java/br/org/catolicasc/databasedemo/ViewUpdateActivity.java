package br.org.catolicasc.databasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewUpdateActivity extends AppCompatActivity {

    private DAL dal;
    private EditText etTitle;
    private EditText etAuthor;
    private EditText etPublisher;
    private TextView tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_update);

        Intent intent = getIntent();

        int id = intent.getIntExtra(CreateDatabase.ID, 0);

        dal = new DAL(this);
        tvId = findViewById(R.id.tvId);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etPublisher = findViewById(R.id.etPublisher);

        Cursor cursor = dal.findById(id);

        tvId.setText(String.valueOf(id));
        etTitle.setText(cursor.getString(cursor.getColumnIndex(CreateDatabase.TITLE)));
        etAuthor.setText(cursor.getString(cursor.getColumnIndex(CreateDatabase.AUTHOR)));
        etPublisher.setText(cursor.getString(cursor.getColumnIndex(CreateDatabase.PUBLISHER)));


        Button btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAL dal = new DAL(ViewUpdateActivity.this);

                int id = Integer.valueOf(tvId.getText().toString());
                String title = etTitle.getText().toString();
                String author = etAuthor.getText().toString();
                String publisher = etPublisher.getText().toString();

                if (dal.update(id, title, author, publisher)) {
                    Toast.makeText(ViewUpdateActivity.this,
                            "Registro Inserido com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ViewUpdateActivity.this,
                            "Erro ao inserir registro!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
