package br.org.catolicasc.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

class DAL {
    private static final String TAG = "DAL";

    private SQLiteDatabase db;
    private CreateDatabase database;

    DAL(Context context) {
        database = new CreateDatabase(context);
    }

    /**
     * Insere um registro no banco
     * @param title título do livro
     * @param author autor do livro
     * @param publisher editora do livro
     * @return true se foi possível inserir com sucesso, false caso contrário
     */
    boolean insert(String title, String author, String publisher) {
        ContentValues values;
        long result;

        // Obtemos um acesso ao banco com permissão de escrita
        db = database.getWritableDatabase();

        // Par de nomes de colunas + valores, para atualização no banco
        values = new ContentValues();
        values.put(CreateDatabase.TITLE, title);
        values.put(CreateDatabase.AUTHOR, author);
        values.put(CreateDatabase.PUBLISHER, publisher);

        // efetivamente insere o registro no banco, fechando o acesso em seguida
        result = db.insert(CreateDatabase.TABLE, null, values);
        db.close();

        // Reporta um erro caso tenha acontecido
        if (result == -1) {
            Log.e(TAG, "insert: Erro inserindo registro");
            return false;
        }

        return true;
    }

    /**
     * Remove um registro do banco.
     * @param id O id do registro a ser removido
     * @return true em caso de sucesso, false em caso contrário
     */
    boolean delete(int id) {
        long result;

        // A cláusula where para o update. Note a interrogação. É um "wildcard".
        // Seu valor será inserido pelo contido na variável args
        String where = "_id = ?";
        String[] args = { String.valueOf(id) };

        // Obtemos um acesso ao banco com permissão de escrita
        db = database.getWritableDatabase();

        // efetivamente faz o delete no banco, fechando o acesso em seguida
        result = db.delete(CreateDatabase.TABLE, where, args);
        db.close();

        // Reporta um erro caso tenha acontecido
        if (result == -1) {
            Log.e(TAG, "insert: Erro removendo registro");
            return false;
        }

        return true;
    }

    /**
     * Atualiza um registro na base de dados
     * @param id o id do livro
     * @param title título do livro
     * @param author autor do livro
     * @param publisher editora do livro
     * @return true se foi possível atualizar o registro, false caso contrário.
     */
    boolean update(int id, String title, String author, String publisher) {
        ContentValues values;
        long result;

        // A cláusula where para o update. Note a interrogação. É um "wildcard".
        // Seu valor será inserido pelo contido na variável args
        String where = "_id = ?";
        String[] args = { String.valueOf(id) };

        // Obtemos um acesso ao banco com permissão de escrita
        db = database.getWritableDatabase();

        // Par de nomes de colunas + valores, para atualização no banco
        values = new ContentValues();
        values.put(CreateDatabase.TITLE, title);
        values.put(CreateDatabase.AUTHOR, author);
        values.put(CreateDatabase.PUBLISHER, publisher);

        // efetivamente faz o update no banco, fechando o acesso em seguida
        result = db.update(CreateDatabase.TABLE, values, where, args);
        db.close();

        // Reporta um erro caso tenha acontecido
        if (result == -1) {
            Log.e(TAG, "insert: Erro atualizando registro");
            return false;
        }

        return true;
    }

    /**
     * Busca no banco um registro pelo id.
     * @param id id do livro.
     * @return Cursor apontando para o registro do banco que contém o id indicado.
     */
    Cursor findById(int id) {
        Cursor cursor;

        // A cláusula where para o update. Note a interrogação. É um "wildcard".
        // Seu valor será inserido pelo contido na variável args
        String where = "_id = ?";
        String[] args = { String.valueOf(id) };

        // Obtém um acesso ao banco somente leitura
        db = database.getReadableDatabase();

        // Cria uma consulta ao banco. Observe o formato. Ao não passar colunas,
        // temos o equivalente a SELECT *. Esta consulta, em SQL corrente, seria:
        // SELECT * from book WHERE _id = :id (:id é o id passado por parâmetro)
        cursor = db.query(CreateDatabase.TABLE, null,
                where, args, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    Cursor loadAll() {
        Cursor cursor;
        String[] fields = {CreateDatabase.ID, CreateDatabase.TITLE};
        db = database.getReadableDatabase();

        // SELECT _id, title FROM book
        // Consulta equivalente:
        // String sql = "SELECT _id, title FROM book";
        // cursor = db.rawQuery(sql, null);
        cursor = db.query(CreateDatabase.TABLE, fields, null,
                null, null, null,
                null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }
}
