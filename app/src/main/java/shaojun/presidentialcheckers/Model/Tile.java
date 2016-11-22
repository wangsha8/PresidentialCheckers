package shaojun.presidentialcheckers.Model;

/**
 * Created by shaojun on 11/4/16.
 * https://gist.github.com/Oshuma/3352280
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public final class Tile {
    private static final String TAG = Tile.class.getSimpleName();

    private final int col;
    private final int row;

    private final Paint squareColor;
    private Rect tileRect;

    public Piece piece;
    public boolean legal=false;
    public boolean isNotBlocked;

    public Tile(final int row, final int col) {
        this.col = col;
        this.row = row;

        this.squareColor = new Paint();
        squareColor.setColor(isDark() ? Color.BLACK : Color.WHITE);
        isNotBlocked = isDark() ? false : true;
    }

    public void draw(final Canvas canvas) {
        if (this.legal)
        {this.squareColor.setColor(Color.rgb(180,255,220));}
        else
        {squareColor.setColor(isDark() ? Color.BLACK : Color.WHITE);}
        canvas.drawRect(tileRect, squareColor);
    }


    public String getRowString() {
        // To get the actual row, add 1 since 'row' is 0 indexed.
        return String.valueOf(row + 1);
    }

    public void handleTouch() {
        Log.d(TAG, "handleTouch(): col: " + col +" row: " + row);
    }

    public boolean isDark() {
        return (col + row) % 2 == 0;
    }

    public boolean isTouched(final int x, final int y) {
        return tileRect.contains(x, y);
    }

    public void setTileRect(final Rect tileRect) {
        this.tileRect = tileRect;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
            return row;
    }
}