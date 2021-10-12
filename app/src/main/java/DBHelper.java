import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    //생성자 database 파일을 생성한다.
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "nz.db";

    public DBHelper(@Nullable Context contex)
    {
        super(contex, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL,content TEXT NOT NULL,writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }

    //INSERT문
    public void InsertTodo(String _title, String _content, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TodoList (title, content,WriteDate) VALUES('"+ _title +"','"')");
    }
}