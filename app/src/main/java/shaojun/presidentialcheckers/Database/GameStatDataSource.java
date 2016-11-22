package shaojun.presidentialcheckers.Database;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by shaojun on 11/15/16.
 */

public class GameStatDataSource
{
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_CANDIDATE_NAME, DatabaseHelper.COLUMN_NUMBER_OF_WINNING};

    public GameStatDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void updateNumberOfWinning(boolean trump)
    {

        if(trump)
        {
//            database.execSQL("insert into " + DatabaseHelper.TABLE_GAME_STAT + " ("+DatabaseHelper.COLUMN_ID+","+DatabaseHelper.COLUMN_CANDIDATE_NAME+","+DatabaseHelper.COLUMN_NUMBER_OF_WINNING+")"+
//                    " values (1,'trump',0);");
//            database.execSQL("insert into " + DatabaseHelper.TABLE_GAME_STAT + " ("+DatabaseHelper.COLUMN_ID+","+DatabaseHelper.COLUMN_CANDIDATE_NAME+","+DatabaseHelper.COLUMN_NUMBER_OF_WINNING+")"+
//                    " values (2,'hillary',0);");
            Cursor cursor = database.query(DatabaseHelper.TABLE_GAME_STAT, allColumns, "id=1", null, null, null, null);
            //Cursor cursor = database.query(DatabaseHelper.TABLE_GAME_STAT, allColumns, null, null, null, null, null);
            cursor.moveToFirst();
            GameStat gs=cursorToGameStat(cursor);
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NUMBER_OF_WINNING,gs.numberOfWinning+1);
            database.update(DatabaseHelper.TABLE_GAME_STAT,values,"id=1",null);
        }
        else
        {
            Cursor cursor = database.query(DatabaseHelper.TABLE_GAME_STAT, allColumns, "id=2", null, null, null, null);
            //Cursor cursor = database.query(DatabaseHelper.TABLE_GAME_STAT, allColumns, null, null, null, null, null);
            cursor.moveToFirst();
            GameStat gs=cursorToGameStat(cursor);
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NUMBER_OF_WINNING,gs.numberOfWinning+1);
            database.update(DatabaseHelper.TABLE_GAME_STAT,values,"id=2",null);
        }
    }

    public List<GameStat> getAllGameStats() {
        List<GameStat> gss = new ArrayList<GameStat>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_GAME_STAT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GameStat gs = cursorToGameStat(cursor);
            gss.add(gs);
            cursor.moveToNext();
        }
        cursor.close();
        return gss;
    }

    private GameStat cursorToGameStat(Cursor cursor) {
        GameStat gs = new GameStat();
        gs.id=Integer.parseInt(cursor.getString(0));
        gs.candidateName=cursor.getString(1);
        gs.numberOfWinning=Integer.parseInt(cursor.getString(2));
        return gs;
    }
}
