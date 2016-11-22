package shaojun.presidentialcheckers.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shaojun on 11/15/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String TABLE_GAME_STAT = "Game_Stat";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CANDIDATE_NAME = "candidate_name";
    public static final String COLUMN_NUMBER_OF_WINNING = "number_of_winning";

    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_GAME_STAT + "( "
            + COLUMN_ID + " integer primary key, "
            + COLUMN_CANDIDATE_NAME + " text not null, "
            + COLUMN_NUMBER_OF_WINNING + " integer not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL("insert into " + TABLE_GAME_STAT + " ("+COLUMN_ID+","+COLUMN_CANDIDATE_NAME+","+COLUMN_NUMBER_OF_WINNING+")"+
                " values (1,'trump',0);");
        database.execSQL("insert into " + TABLE_GAME_STAT + " ("+COLUMN_ID+","+COLUMN_CANDIDATE_NAME+","+COLUMN_NUMBER_OF_WINNING+")"+
                " values (2,'hillary',0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME_STAT);
        onCreate(db);
    }
}

